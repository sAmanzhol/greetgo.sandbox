package kz.greetgo.sandbox.register.impl.jdbc.migration;

import org.apache.commons.net.ftp.FTPClient;

import java.sql.Connection;

@SuppressWarnings("WeakerAccess")
public abstract class MigrationAbstract {

  public Connection connection;
  public FTPClient ftp;
  public String filePath;


  public MigrationAbstract(Connection connection) {
    this.connection = connection;
  }

  public MigrationAbstract(Connection connection, FTPClient ftp, String filePath) {
    this.connection = connection;
    this.ftp = ftp;
    this.filePath = filePath;
  }

  public void migrate() throws Exception {

    dropTemplateTables();

    createTempTables();

    parseAndFillData();

    checkForValidness();

    validateAndMigrateData();

    dropTemplateTables();

    disableUnusedRecords();

    checkForLateUpdates();
  }

  public abstract void createTempTables() throws Exception;

  public abstract void parseAndFillData() throws Exception;

  public abstract void checkForValidness() throws Exception;

  public abstract void validateAndMigrateData() throws Exception;

  public abstract void dropTemplateTables() throws Exception;

  public abstract void disableUnusedRecords() throws Exception;

  public abstract void checkForLateUpdates() throws Exception;
}
