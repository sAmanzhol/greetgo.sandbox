package kz.greetgo.sandbox.register.impl.jdbc.migration;

import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.register.configs.DbConfig;

import kz.greetgo.sandbox.register.configs.MigrationConfig;
import java.sql.Connection;
import java.sql.DriverManager;

@SuppressWarnings("WeakerAccess")
public abstract class MigrationCallbackAbstract<T> implements ConnectionCallback<T> {

  public Connection connection;

//  public BeanGetter<MigrationConfig> migrationConfig;

  public MigrationCallbackAbstract(Connection connection) {
    this.connection = connection;
  }

  public MigrationCallbackAbstract() {
  }

  @Override
  public T doInConnection(Connection con) throws Exception {
//    this.connection = DriverManager.getConnection(
//      migrationConfig.get().dbUrl(),
//      migrationConfig.get().dbUsername(),
//      migrationConfig.get().dbPassword()
//    );



    this.connection = con;

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

  public void createTempTables() throws Exception {
    this.connection = this.getConnection();
  }

  public void parseAndFillData() throws Exception {
    this.connection = this.getConnection();
  }

  public abstract void checkForValidness() throws Exception;

  public abstract void validateAndMigrateData() throws Exception;

  public void dropTemplateTables() throws Exception {
    this.connection = this.getConnection();
  }

  public abstract void disableUnusedRecords() throws Exception;

  public abstract void checkForLateUpdates() throws Exception;

  public Connection getConnection() throws Exception {
    return DriverManager.getConnection(
      "jdbc:postgresql://localhost/ssailaubayev_sandbox",
      "ssailaubayev_sandbox",
      "111"
    );
  }
}
