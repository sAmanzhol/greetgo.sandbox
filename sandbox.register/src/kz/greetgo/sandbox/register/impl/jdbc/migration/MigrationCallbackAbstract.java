package kz.greetgo.sandbox.register.impl.jdbc.migration;

import kz.greetgo.db.ConnectionCallback;

import java.sql.Connection;
import java.sql.DriverManager;

@SuppressWarnings("WeakerAccess")
public abstract class MigrationCallbackAbstract<T> implements ConnectionCallback<T> {

  public Connection connection;

//  public BeanGetter<MigrationConfig> migrationConfig;

  public MigrationCallbackAbstract() throws Exception {
    this.connection = DriverManager.getConnection(
      "jdbc:postgresql://localhost/ssailaubayev_sandbox",
      "ssailaubayev_sandbox",
      "111"
    );
  }

  @Override
  public T doInConnection(Connection con) throws Exception {
//    this.connection = DriverManager.getConnection(
//      migrationConfig.get().dbUrl(),
//      migrationConfig.get().dbUsername(),
//      migrationConfig.get().dbPassword()
//    );

    createTempTables();

    parseAndFillData();

    checkForValidness();

    validateAndMigrateData();

    System.out.println("check tables");

    dropTemplateTables();

    disableUnusedRecords();

    checkForLateUpdates();

    return null;
  }

  public abstract void createTempTables() throws Exception;

  public abstract void parseAndFillData() throws Exception;

  public abstract void checkForValidness() throws Exception;

  public abstract void validateAndMigrateData() throws Exception;

  public abstract void dropTemplateTables() throws Exception;

  public abstract void disableUnusedRecords() throws Exception;

  public abstract void checkForLateUpdates() throws Exception;
}
