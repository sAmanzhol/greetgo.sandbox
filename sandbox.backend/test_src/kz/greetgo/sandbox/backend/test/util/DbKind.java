package kz.greetgo.sandbox.backend.test.util;

public enum DbKind {
  MASTER(ConnectionKind.MASTER),
  DIFF(ConnectionKind.DIFF);

  private ConnectionKind connection;

  DbKind(ConnectionKind connection) {
    this.connection = connection;
  }

  public ConnectionKind connection() {
    return connection;
  }
}
