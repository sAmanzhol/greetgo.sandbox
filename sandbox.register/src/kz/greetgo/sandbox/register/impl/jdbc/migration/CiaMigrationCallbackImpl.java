package kz.greetgo.sandbox.register.impl.jdbc.migration;

import kz.greetgo.sandbox.register.impl.jdbc.migration.model.Cia;

import java.sql.Connection;

public class CiaMigrationCallbackImpl extends MigrationCallbackAbstract<Void> {

  public CiaMigrationCallbackImpl(Connection connection) {
    super(connection);
  }

  public CiaMigrationCallbackImpl(Connection connection, Cia ciaData) {
    super(connection);
  }

  public CiaMigrationCallbackImpl(Cia ciaData) {}

  @Override
  public Void doInConnection(Connection con) {
    return null;
  }
}
