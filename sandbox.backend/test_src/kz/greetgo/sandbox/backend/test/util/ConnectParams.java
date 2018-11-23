package kz.greetgo.sandbox.backend.test.util;

public interface ConnectParams {
  String url();

  String username();

  String password();

  default String dbName() {
    return DbUrlUtils.extractDbName(url());
  }
}
