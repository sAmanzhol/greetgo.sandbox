package kz.greetgo.sandbox.backend.configuration.util;

import kz.greetgo.db.Jdbc;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class JdbcTemplateBridge implements Jdbc {

  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateBridge(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public <T> T execute(kz.greetgo.db.ConnectionCallback<T> connectionCallback) {
    //noinspection NullableProblems
    return jdbcTemplate.execute((ConnectionCallback<T>) connection -> {
      try {
        return connectionCallback.doInConnection(connection);
      } catch (Exception e) {
        if (e instanceof RuntimeException) {
          throw (RuntimeException) e;
        }
        if (e instanceof SQLException) {
          throw (SQLException) e;
        }
        throw new RuntimeException(e);
      }
    });
  }
}
