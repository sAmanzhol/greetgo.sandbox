package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.register.MigrationRegister;
import kz.greetgo.sandbox.register.configs.MigrationConfig;
import kz.greetgo.sandbox.register.impl.jdbc.migration.CiaMigrationCallbackImpl;
import kz.greetgo.sandbox.register.impl.jdbc.migration.FrsMigrationCallbackImpl;
import kz.greetgo.sandbox.register.util.JdbcSandbox;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


@Bean
public class MigrationRegisterImpl implements MigrationRegister {

  public BeanGetter<JdbcSandbox> jdbc;
  public BeanGetter<MigrationConfig> migrationConfig;


  @Override
  public void migrate() throws Exception {
    // Temporary files in local directory from config
    File migrationFolder = new File(migrationConfig.get().directory());

    ArrayList<File> ciaFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(migrationFolder.listFiles((file, name) -> name.toLowerCase().endsWith(".xml")))));
    ArrayList<File> frsFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(migrationFolder.listFiles((file, name) -> name.toLowerCase().endsWith(".json_row")))));

    while (ciaFiles.size() > 0 || frsFiles.size() > 0) {
      if (ciaFiles.size() > 0) {
        jdbc.get().execute(new CiaMigrationCallbackImpl(ciaFiles.get(0).getPath()));
        ciaFiles.remove(0);
      }

      if (frsFiles.size() > 0) {
        jdbc.get().execute(new FrsMigrationCallbackImpl(frsFiles.get(0).getPath()));
        frsFiles.remove(0);
      }
    }
  }
}
