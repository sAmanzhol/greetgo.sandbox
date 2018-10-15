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
  public void validateAndMigrateData() {

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
}
