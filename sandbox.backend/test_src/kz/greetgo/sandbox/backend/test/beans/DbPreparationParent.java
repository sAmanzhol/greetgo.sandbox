package kz.greetgo.sandbox.backend.test.beans;

import kz.greetgo.sandbox.backend.config.DbConfig;
import kz.greetgo.sandbox.backend.configuration.logging.LOG;
import kz.greetgo.sandbox.backend.test.util.ConnectParams;
import kz.greetgo.sandbox.backend.test.util.ConnectionKind;
import kz.greetgo.sandbox.backend.test.util.SqlError;
import kz.greetgo.sandbox.backend.test.util.SqlErrorType;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static kz.greetgo.sandbox.backend.test.util.ConnectParamsUtil.extractConnectParams;

public abstract class DbPreparationParent {
  protected abstract LOG log();

  @Autowired
  protected DbConfig dbConfig;

  protected ConnectParams getConnectParams(ConnectionKind connectionKind) {
    return extractConnectParams(dbConfig, connectionKind);
  }

  protected Connection connectTo(ConnectionKind connectionKind) throws Exception {
    Class.forName("org.postgresql.Driver");
    ConnectParams params = getConnectParams(connectionKind);
    return DriverManager.getConnection(params.url(), params.username(), params.password());
  }

  protected void exec(ConnectionKind connectionKind, String sql) {
    try {

      try (Connection con = connectTo(connectionKind)) {
        try (Statement statement = con.createStatement()) {
          log().info(() -> "Exec SQL: " + sql.replace('\n', ' '));
          statement.execute(sql);
        }
      }

    } catch (RuntimeException e) {
      throw e;
    } catch (SQLException e) {
      log().errorMessage(e.getMessage());
      throw new SqlError(e);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected void exec(ConnectionKind connectionKind, String sql, SqlErrorType... types) {
    try {
      exec(connectionKind, sql);
    } catch (SqlError e) {
      for (SqlErrorType type : types) {
        if (e.type == type) {
          return;
        }
      }
      throw e;
    }
  }
}
