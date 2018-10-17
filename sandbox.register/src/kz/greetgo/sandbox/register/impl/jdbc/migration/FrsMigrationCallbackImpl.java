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
        frsTransaction.money = (String) rowJson.get("money");
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

  @Override
  public void validateAndMigrateData() throws Exception {

    // First part getting rid of records with error.
    // Set status = 2 (Records with errors)

    this.checkForValidness();

    // Migrate valid accounts and transactions with actual 1
    // Else set actual 0, to late update

    this.validateAndMigrateAccountData();
    this.validateAndMigrateTransactionData();


  }

  private void validateAndMigrateAccountData() throws Exception {

    // no upsert errss
    String clientAccountTableUpdateMigrate =
      "insert into client_account (id, client, number, registered_at) " +
        " select nextval('id') as id, (select id from client where migration_id = client) as client, account_number as number, to_timestamp(registered_at, 'YYYY-MM-DD hh24:mi:ss') as registered_at " +
        " from client_account_temp " +
        " where status = 1" +
        " on conflict (number) " +
        " do nothing";

    String clientAccountTableUpdateMigrateCheck =
      "update client_account " +
        "set actual = 0 " +
        "where client not in (select distinct id from client)";


    try (PreparedStatement ps = connection.prepareStatement(clientAccountTableUpdateMigrate)) {
      ps.executeUpdate();
    }

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTableUpdateMigrateCheck)) {
      ps.executeUpdate();
    }
  }

  private void validateAndMigrateTransactionData() throws Exception {

    // Need to change account number to int (remove 1)
    // Need to change money to double (remove 1)
    // Need to change type to int (remove 1)
    String clientAccountTransactionTableUpdateMigrate =
      "insert into client_account_transaction (id, account, money, finished_at, type) " +
        " select nextval('id') as id, 1 as account, 1 as money, to_timestamp(finished_at, 'YYYY-MM-DD hh24:mi:ss') as finished_at, 1 as type " +
        " from client_account_transaction_temp " +
        " where status = 1";

    String clientAccountTransactionTableUpdateMigrateCheck =
      "update client_account_transaction " +
        "set actual = 0 " +
        "where account not in (select distinct number from client_account)";


    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTableUpdateMigrate)) {
      ps.executeUpdate();
    }

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTableUpdateMigrateCheck)) {
      ps.executeUpdate();
    }
  }

  /*
   Function for checking records for validness, if there some error than changes it`s status to 2
 */
  private void checkForValidness() throws Exception {
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
  public void dropTemplateTables() throws Exception {
    final String clientAccountTableDrop = "drop table client_account_temp";

    final String clientAccountTransactionTableDrop = "drop table client_account_transaction_temp";

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTableDrop)) {
      ps.executeUpdate();
    }

    try (PreparedStatement ps = connection.prepareStatement(clientAccountTransactionTableDrop)) {
      ps.executeUpdate();
    }
  }

  @Override
  public void checkForLateUpdates() throws Exception {

  }
}
