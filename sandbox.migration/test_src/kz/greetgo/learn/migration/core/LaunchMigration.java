package kz.greetgo.learn.migration.core;

import kz.greetgo.learn.migration.interfaces.ConnectionConfig;
import kz.greetgo.learn.migration.util.ConfigFiles;
import kz.greetgo.learn.migration.util.ConnectionUtils;
import kz.greetgo.learn.migration.util.TimeUtils;

import java.io.File;

public class LaunchMigration {

  public static void main(String[] args) throws Exception {

    final File file = new File("build/__migration__");
    file.getParentFile().mkdirs();
    file.createNewFile();

    ConnectionConfig operCC = ConnectionUtils.fileToConnectionConfig(ConfigFiles.operDb());
    ConnectionConfig ciaCC = ConnectionUtils.fileToConnectionConfig(ConfigFiles.ciaDb());
    long startT = System.nanoTime();
    try (MigrationXML migrationXML = new MigrationXML(operCC, ciaCC)) {

      migrationXML.portionSize = 250_000;
      migrationXML.uploadMaxBatchSize = 50_000;
      migrationXML.downloadMaxBatchSize = 50_000;

      while (true)
      {
        int count = migrationXML.migrate();
        if (count == 0) break;
        if (count < 0) break;
        if (!file.exists()) break;
        System.out.println("MigratedXML " + count + " records");
        System.out.println("------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------");
      }
      System.out.println("SQL REPORT CLIENTS");
      migrationXML.print();
    }

    try (MigrationJSON migrationJSON  = new MigrationJSON(operCC, ciaCC)) {

      migrationJSON.portionSize = 500_000;
      migrationJSON.uploadMaxBatchSize = 50_000;
      migrationJSON.downloadMaxBatchSize = 50_000;

      while (true)
      {
        int count = migrationJSON.migrate();
        if (count == 0) break;
        if (count < 0) break;
        if (!file.exists()) break;
        System.out.println("MigratedJSON " + count + " records");
        System.out.println("------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------");
      }
      System.out.println("SQL REPORT ACCOUNT TRANSACTION");
      migrationJSON.print();
    }

    file.delete();
    long now = System.nanoTime();

    System.out.println("Finish migration in " + TimeUtils.showTime(now, startT));
  }

}