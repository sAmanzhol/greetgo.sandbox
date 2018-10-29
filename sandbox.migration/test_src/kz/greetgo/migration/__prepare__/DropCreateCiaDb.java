package kz.greetgo.migration.__prepare__;

import kz.greetgo.migration.__prepare__.core.DbWorker;
import kz.greetgo.migration.__prepare__.db.cia.CiaDDL;
import kz.greetgo.migration.util.ConfigFiles;

public class DropCreateCiaDb {
  public static void main(String[] args) throws Exception {
    DbWorker dbWorker = new DbWorker();

    dbWorker.prepareConfigFiles();

    dbWorker.dropCiaDb();
    dbWorker.createCiaDb();

    dbWorker.applyDDL(ConfigFiles.ciaDb(), CiaDDL.get());
  }
}
