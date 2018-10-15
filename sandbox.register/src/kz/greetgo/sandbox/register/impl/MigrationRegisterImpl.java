package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.register.MigrationRegister;
import kz.greetgo.sandbox.register.impl.jdbc.migration.CiaMigrationCallbackImpl;
import kz.greetgo.sandbox.register.impl.jdbc.migration.FrsMigrationCallbackImpl;
import kz.greetgo.sandbox.register.util.JdbcSandbox;


@Bean
public class MigrationRegisterImpl implements MigrationRegister {

  public BeanGetter<JdbcSandbox> jdbc;

  @Override
  public void migrate(String fileType, String file) {
    if ("cia".equals(fileType)) {
      jdbc.get().execute(new CiaMigrationCallbackImpl(file));
    } else if ("frs".equals(fileType)) {
      jdbc.get().execute(new FrsMigrationCallbackImpl(file));
    }
  }
}
