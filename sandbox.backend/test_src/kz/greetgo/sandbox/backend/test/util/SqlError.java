package kz.greetgo.sandbox.backend.test.util;

import java.sql.SQLException;

public class SqlError extends RuntimeException {
  public final SqlErrorType type;

  public SqlError(SQLException e) {
    super("SQL State = " + e.getSQLState() + " : " + e.getMessage(), e);
    type = defineType(e);
  }

  private SqlErrorType defineType(SQLException e) {
    if ("3D000".equals(e.getSQLState())) {
      return SqlErrorType.DB_ABSENT;
    }
    if ("42704".equals(e.getSQLState())) {
      return SqlErrorType.ROLE_ABSENT;
    }
    return SqlErrorType.UNKNOWN;
  }
}
