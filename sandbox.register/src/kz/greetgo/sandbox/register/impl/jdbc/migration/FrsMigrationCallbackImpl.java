package kz.greetgo.sandbox.register.impl.jdbc.migration;

import kz.greetgo.sandbox.register.impl.jdbc.migration.model.Frs;

import java.sql.Connection;

public class FrsMigrationCallbackImpl extends MigrationCallbackAbstract<Void> {

  public FrsMigrationCallbackImpl(Connection connection) {
    super(connection);
  }

  public FrsMigrationCallbackImpl(Connection connection, Frs frsData) {
    super(connection);
  }

  public FrsMigrationCallbackImpl(Frs frsData) {}

  @Override
  public Void doInConnection(Connection con) {
    return null;
  }
}
