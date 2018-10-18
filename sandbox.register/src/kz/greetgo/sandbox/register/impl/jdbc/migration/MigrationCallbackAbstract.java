package kz.greetgo.sandbox.register.impl.jdbc.migration;

import kz.greetgo.db.ConnectionCallback;

import java.sql.Connection;

@SuppressWarnings("WeakerAccess")
public abstract class MigrationCallbackAbstract<T> implements ConnectionCallback<T> {

  public Connection connection;

//  public BeanGetter<DbConfig> dbConfig;

  public MigrationCallbackAbstract(Connection connection) {
    this.connection = connection;
  }

  public MigrationCallbackAbstract() {
  }

  @Override
  public T doInConnection(Connection con) throws Exception {
//    this.connection = DriverManager.getConnection(
//      dbConfig.get().url(),
//      dbConfig.get().username(),
//      dbConfig.get().password()
//    );

    this.connection = con;

    createTempTables();

    parseAndFillData();

    validateAndMigrateData();

    System.out.println("check tables");

    dropTemplateTables();

    disableUnusedRecords();

    checkForLateUpdates();

    return null;
  }

  public abstract void createTempTables() throws Exception;

  public abstract void parseAndFillData() throws Exception;

  public abstract void validateAndMigrateData() throws Exception;

  public abstract void dropTemplateTables() throws Exception;

  public abstract void disableUnusedRecords() throws Exception;

  public abstract void checkForLateUpdates() throws Exception;
}
