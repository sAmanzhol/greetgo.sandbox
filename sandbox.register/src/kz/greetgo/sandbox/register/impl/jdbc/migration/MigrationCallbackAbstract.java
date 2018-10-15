package kz.greetgo.sandbox.register.impl.jdbc.migration;

import kz.greetgo.db.ConnectionCallback;

import java.sql.Connection;

@SuppressWarnings("WeakerAccess")
public abstract class MigrationCallbackAbstract<T> implements ConnectionCallback<T> {

  public Connection connection;

  public MigrationCallbackAbstract(Connection connection) {
    this.connection = connection;
  }

  public MigrationCallbackAbstract() {}

  @Override
  public abstract T doInConnection(Connection con) throws Exception;
}
