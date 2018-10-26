package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.register.MigrationRegister;
import kz.greetgo.sandbox.register.configs.MigrationConfig;
import kz.greetgo.sandbox.register.impl.jdbc.migration.CiaMigrationCallbackImpl;
import kz.greetgo.sandbox.register.impl.jdbc.migration.FrsMigrationCallbackImpl;
import kz.greetgo.sandbox.register.util.JdbcSandbox;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
@Bean
public class MigrationRegisterImpl implements MigrationRegister {

  public BeanGetter<JdbcSandbox> jdbc;
  public BeanGetter<MigrationConfig> migrationConfig;

  FTPClient ftp;

  @Override
  public void migrate() throws Exception {
    ftp = getFtpConnection();

    ArrayList<FTPFile> ciaFiles = getCiaMigrationFiles("migration");
    ArrayList<FTPFile> frsFiles = getFrsMigrationFiles("migration");

    while (ciaFiles.size() > 0 || frsFiles.size() > 0) {
      if (ciaFiles.size() > 0) {
        String curFilePath = String.format("migration/%s", ciaFiles.get(0).getName());

        jdbc.get().execute(new CiaMigrationCallbackImpl(ftp, curFilePath));
        ftp.rename(curFilePath, curFilePath + ".txt");
        ciaFiles.remove(0);
      }

      if (frsFiles.size() > 0) {
        String curFilePath = String.format("migration/%s", frsFiles.get(0).getName());

        jdbc.get().execute(new FrsMigrationCallbackImpl(ftp, curFilePath));
        ftp.rename(curFilePath, curFilePath + ".txt");
        frsFiles.remove(0);
      }
    }

    ftp.disconnect();
  }

  public FTPClient getFtpConnection() throws Exception {
    FTPClient ftp = new FTPClient();
    ftp.connect("localhost");
    ftp.login("ssailaubayev", "111");

    return ftp;
  }

  public ArrayList<FTPFile> getCiaMigrationFiles(String path) throws Exception {
    FTPFile[] ftpFiles = ftp.listFiles(path, ftpFile -> (ftpFile.isFile() && ftpFile.getName().endsWith(".xml")));

    return new ArrayList<>(Arrays.asList(ftpFiles));
  }

  public ArrayList<FTPFile> getFrsMigrationFiles(String path) throws Exception {
    FTPFile[] ftpFiles = ftp.listFiles(path, ftpFile -> (ftpFile.isFile() && ftpFile.getName().endsWith(".json_row")));

    return new ArrayList<>(Arrays.asList(ftpFiles));
  }
}
