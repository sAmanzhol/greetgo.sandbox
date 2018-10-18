package kz.greetgo.sandbox.register.impl.jdbc.migration;

import kz.greetgo.sandbox.register.impl.jdbc.migration.model.FrsAccount;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.FrsTransaction;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.PreparedStatement;

public class FrsMigrationCallbackImpl extends MigrationCallbackAbstract<Void> {

  private String filePath;

  public FrsMigrationCallbackImpl(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public void createTempTables() throws Exception {

    final String clientAccountTableCreate =
      "create table client_account_temp (" +
        " client varchar(100), " +
        " account_number varchar(100), " +
        " registered_at varchar(100), " +
        " status int default 1" +
        ")";

    final String clientAccountTransactionTableCreate =
      "create table client_account_transaction_temp (" +
        " transaction_type varchar(100), " +
        " account_number varchar(100), " +
        " finished_at varchar(100), " +
        " money varchar(100), " +
        " status int default 1" +
        ")";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTableCreate)) {
      ps.executeUpdate();
    }

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTableCreate)) {
      ps.executeUpdate();
    }
  }

  @Override
  public void parseAndFillData() throws Exception {

    String clientAccountTempTableInsert =
      "insert into client_account_temp (client, account_number, registered_at) " +
        " values (?, ?, ?)";

    String clientAccountTransactionTempTableInsert =
      "insert into client_account_transaction_temp (account_number, transaction_type, money, finished_at) " +
        " values (?, ?, ?, ?)";


    FileReader fileReader = new FileReader(filePath);
    BufferedReader bufferedReader = new BufferedReader(fileReader);

    String rowString;

    while ((rowString = bufferedReader.readLine()) != null) {
      JSONObject rowJson = new JSONObject(rowString);

      if (rowJson.get("type").equals("transaction")) {
        FrsTransaction frsTransaction = new FrsTransaction();
        frsTransaction.money = ((String) rowJson.get("money")).replace("_", "");
        frsTransaction.finished_at = (String) rowJson.get("finished_at");
        frsTransaction.transaction_type = (String) rowJson.get("transaction_type");
        frsTransaction.account_number = (String) rowJson.get("account_number");

        try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTempTableInsert)) {
          ps.setObject(1, frsTransaction.account_number);
          ps.setObject(2, frsTransaction.transaction_type);
          ps.setObject(3, frsTransaction.money);
          ps.setObject(4, frsTransaction.finished_at);

          ps.executeUpdate();
        }
      } else if (rowJson.get("type").equals("new_account")) {
        FrsAccount frsAccount = new FrsAccount();
        frsAccount.client_id = (String) rowJson.get("client_id");
        frsAccount.account_number = (String) rowJson.get("account_number");
        frsAccount.registered_at = (String) rowJson.get("registered_at");

        try (PreparedStatement ps = connection.prepareStatement(clientAccountTempTableInsert)) {
          ps.setObject(1, frsAccount.client_id);
          ps.setObject(2, frsAccount.account_number);
          ps.setObject(3, frsAccount.registered_at);

          ps.executeUpdate();
        }
      }
    }

    bufferedReader.close();
    fileReader.close();
  }

  /*
   Function for checking records for validness, if there some error than changes it`s status to 2
 */
  @Override
  public void checkForValidness() throws Exception {

    String clientAccountTempTableUpdateError =
      "update client_account_temp set status = 2 " +
        " where client = '' or account_number = '' or registered_at = ''";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTempTableUpdateError)) {
      ps.executeUpdate();
    }


    String clientAccountTransactionTempTableUpdateError =
      "update client_account_transaction_temp set status = 2 " +
        " where transaction_type = '' or account_number = '' or finished_at = '' or money = ''";

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

    // no (update insert) need to fix
    String clientAccountTableUpdateMigrate =
      "insert into client_account (id, client, number, registered_at) " +
        "  select nextval('id') as id, " +
        "  (select id from client where migration_id = client) as client, " +
        "  account_number as number, " +
        "  to_timestamp(registered_at, 'YYYY-MM-DD hh24:mi:ss') as registered_at " +
        " from client_account_temp " +
        " where status = 1" +
        " on conflict (number) " +
        " do nothing";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTableUpdateMigrate)) {
      ps.executeUpdate();
    }


    String clientAccountTransactionTableUpdateMigrate =
      "insert into client_account_transaction (id, account, money, finished_at, type) " +
        " select nextval('id') as id, " +
        "   (select id from client_account where number = account_number) as account, " +
        "   cast(money as double precision) as money, " +
        "   to_timestamp(finished_at, 'YYYY-MM-DD hh24:mi:ss') as finished_at, " +
        "   (select id from transaction_type where name = transaction_type) as type " +
        " from client_account_transaction_temp " +
        " where status = 1";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTableUpdateMigrate)) {
      ps.executeUpdate();
    }


    String clientAccountTableUpdateMigrateMoney =
      "update client_account " +
        "set money = (select sum(money) from client_account_transaction where account = client_account.id and type notnull) " +
        "where client notnull and actual = 1";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTableUpdateMigrateMoney)) {
      ps.executeUpdate();
    }
  }

  @Override
  public void dropTemplateTables() throws Exception {

    final String clientAccountTableDrop = "drop table client_account_temp";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTableDrop)) {
      ps.executeUpdate();
    }


    final String clientAccountTransactionTableDrop = "drop table client_account_transaction_temp";

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
        "where account not in (select distinct id from client_account where actual = 1)";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTableUpdateDisable)) {
      ps.executeUpdate();
    }
  }

  /*
   Function for checking new records that needed for disabled data, if there exists than we enable disabled records
 */
  @Override
  public void checkForLateUpdates() throws Exception {

  }
}
