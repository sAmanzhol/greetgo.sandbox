package kz.greetgo.sandbox.register.impl.jdbc.migration;

import kz.greetgo.sandbox.register.impl.jdbc.migration.model.FrsAccount;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.FrsTransaction;
import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;

@SuppressWarnings("WeakerAccess, SqlResolve")
public class FrsMigrationCallbackImpl extends MigrationCallbackAbstract<Void> {

  private FTPClient ftp;
  private String filePath;

  public FrsMigrationCallbackImpl(String filePath) throws Exception {
    this.filePath = filePath;
  }

  public FrsMigrationCallbackImpl(FTPClient ftp, String filePath) throws Exception {
    this.ftp = ftp;
    this.filePath = filePath;
  }

  @Override
  public void createTempTables() throws Exception {

    final String clientAccountTableCreate =
      "create table client_account_temp (" +
        " client varchar(100), " +
        " account_number varchar(100), " +
        " registered_at varchar(100), " +
        " status int default 1, " +
        " migration_order int" +
        ")";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTableCreate)) {
      ps.executeUpdate();
    }


    final String clientAccountTransactionTableCreate =
      "create table client_account_transaction_temp (" +
        " transaction_type varchar(100), " +
        " account_number varchar(100), " +
        " finished_at varchar(100), " +
        " money varchar(100), " +
        " status int default 1" +
        ")";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTableCreate)) {
      ps.executeUpdate();
    }
  }

  @Override
  public void parseAndFillData() throws Exception {

    String clientAccountTempTableInsert =
      "insert into client_account_temp (client, account_number, registered_at, migration_order) " +
        " values (?, ?, ?, nextval('migration_order'))";

    String clientAccountTransactionTempTableInsert =
      "insert into client_account_transaction_temp (account_number, transaction_type, money, finished_at) " +
        " values (?, ?, ?, ?)";


    InputStream stream = ftp.retrieveFileStream(filePath);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

    String rowString;

    while ((rowString = bufferedReader.readLine()) != null) {
      JSONObject rowJson = new JSONObject(rowString);

      if (rowJson.get("type").equals("transaction")) {
        FrsTransaction frsTransaction = new FrsTransaction();
        frsTransaction.money = ((String) rowJson.get("money")).replace("_", "");
        frsTransaction.finishedAt = (String) rowJson.get("finished_at");
        frsTransaction.transactionType = (String) rowJson.get("transaction_type");
        frsTransaction.accountNumber = (String) rowJson.get("account_number");


        try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTempTableInsert)) {
          ps.setObject(1, frsTransaction.accountNumber);
          ps.setObject(2, frsTransaction.transactionType);
          ps.setObject(3, frsTransaction.money);
          ps.setObject(4, frsTransaction.finishedAt);

          ps.executeUpdate();
        }
      } else if (rowJson.get("type").equals("new_account")) {
        FrsAccount frsAccount = new FrsAccount();
        frsAccount.client = (String) rowJson.get("client_id");
        frsAccount.accountNumber = (String) rowJson.get("account_number");
        frsAccount.registeredAt = (String) rowJson.get("registered_at");

        try (PreparedStatement ps = connection.prepareStatement(clientAccountTempTableInsert)) {
          ps.setObject(1, frsAccount.client);
          ps.setObject(2, frsAccount.accountNumber);
          ps.setObject(3, frsAccount.registeredAt);

          ps.executeUpdate();
        }
      }
    }

    bufferedReader.close();
    stream.close();
    ftp.completePendingCommand();
  }

  /*
   Function for checking records for validness, if there some error than changes it`s status to 2
 */
  @Override
  public void checkForValidness() throws Exception {

    String clientAccountTempTableUpdateError =
      "update client_account_temp set status = 2 " +
        " where client = '' or client = 'null' " +
        "    or account_number = '' or account_number = 'null' " +
        "    or registered_at = '' or registered_at = 'null'";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTempTableUpdateError)) {
      ps.executeUpdate();
    }


    String clientAccountTransactionTempTableUpdateError =
      "update client_account_transaction_temp set status = 2 " +
        " where transaction_type = '' or transaction_type = 'null' " +
        "    or account_number = '' or account_number = 'null' " +
        "    or finished_at = '' or finished_at = 'null' " +
        "    or money = '' or money = 'null'";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTempTableUpdateError)) {
      ps.executeUpdate();
    }
  }

  @Override
  public void validateAndMigrateData() throws Exception {

    // Adding new transaction types

    String clientAccountTransactionTypeInsert =
      "insert into transaction_type (name, id, code) " +
        " select " +
        "   distinct transaction_type as name, " +
        "   nextval('id') as id, " +
        "   nextval('code') as code " +
        " from client_account_transaction_temp " +
        " where transaction_type notnull and status = 1" +
        " group by name " +
        "on conflict (name) do nothing";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTypeInsert)) {
      ps.executeUpdate();
    }

    // Migrate valid accounts and transactions with actual 1

    String clientAccountTableUpdateMigrate =
      "insert into client_account (id, client, number, registered_at, migration_client) " +
        "  select " +
        "  distinct on (account_number)" +
        "  nextval('id') as id, " +
        "  cl.id as client, " +
        "  ac_temp.account_number as number, " +
        "  to_timestamp(ac_temp.registered_at, 'YYYY-MM-DD hh24:mi:ss') as registered_at, " +
        "  ac_temp.client as migration_client" +
        " from client_account_temp ac_temp " +
        "   left join client cl " +
        "     on cl.migration_id = ac_temp.client" +
        "   where status = 1 " +
        "   order by account_number, migration_order asc" +
        " on conflict (number) " +
        " do nothing";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTableUpdateMigrate)) {
      ps.executeUpdate();
    }


    String clientAccountTransactionTableUpdateMigrate =
      "insert into client_account_transaction (id, account, money, finished_at, type, migration_account) " +
        " select nextval('id') as id, " +
        "   ac.id as account, " +
        "   cast(ac_tr_temp.money as double precision) as money, " +
        "   to_timestamp(ac_tr_temp.finished_at, 'YYYY-MM-DD hh24:mi:ss') as finished_at, " +
        "   tt.id as type, " +
        "   account_number as migration_account " +
        " from client_account_transaction_temp ac_tr_temp " +
        "   left join client_account ac " +
        "     on account_number = ac.number " +
        "   left join transaction_type tt " +
        "     on transaction_type = tt.name " +
        " where status = 1 " +
        "on conflict (migration_account, money, finished_at) do nothing";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTableUpdateMigrate)) {
      ps.executeUpdate();
    }


    String clientAccountTableUpdateMigrateMoney =
      "update client_account " +
        "set money = (select sum(money) from client_account_transaction where account = client_account.id and type notnull) " +
        "where actual = 1 and client notnull";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTableUpdateMigrateMoney)) {
      ps.executeUpdate();
    }
  }

  @Override
  public void dropTemplateTables() throws Exception {

    final String clientAccountTableDrop = "drop table if exists client_account_temp";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTableDrop)) {
      ps.executeUpdate();
    }


    final String clientAccountTransactionTableDrop = "drop table if exists client_account_transaction_temp";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTableDrop)) {
      ps.executeUpdate();
    }
  }

  /*
   Function for checking records for dependencies, if there some error than changes it`s actual to 0 until there will be valid dependency
 */
  @Override
  public void disableUnusedRecords() throws Exception {

    String clientAccountTableUpdateDisable =
      "update client_account " +
        "set actual = 0 " +
        "where client isnull and actual = 1";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTableUpdateDisable)) {
      ps.executeUpdate();
    }


    String clientAccountTransactionTableUpdateDisable =
      "update client_account_transaction " +
        "set actual = 0 " +
        "where account is null or account not in (select distinct id from client_account where actual = 1)";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTableUpdateDisable)) {
      ps.executeUpdate();
    }
  }

  /*
    Function for checking new records that needed for disabled data, if there exists than we enable disabled records
  */
  @Override
  public void checkForLateUpdates() throws Exception {
    String clientAccountTableUpdateMigrate =
      "update client_account " +
        "set actual = 1, " +
        "    client = cl.id " +
        "   from client cl " +
        "where migration_client notnull and client_account.actual = 0 and cl.migration_id = migration_client and cl.id notnull";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTableUpdateMigrate)) {
      ps.executeUpdate();
    }

    String clientAccountTransactionTableUpdateMigrate =
      "update client_account_transaction " +
        "set actual = 1, " +
        "    account = acc.id " +
        "   from client_account acc " +
        "where migration_account notnull and client_account_transaction.actual = 0 and acc.number = migration_account and acc.number notnull";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTableUpdateMigrate)) {
      ps.executeUpdate();
    }

    String clientAccountTransactionTableUpdateDisable =
      "update client_account_transaction " +
        "set actual = 0 " +
        "where account is null or account not in (select distinct id from client_account where actual = 1)";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTableUpdateDisable)) {
      ps.executeUpdate();
    }
  }
}
