package kz.greetgo.sandbox.backend.configuration.util;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DbUtil {
  public static SQLException extractSqlException(Throwable throwable) {

    Set<Throwable> checked = new HashSet<>();

    Throwable current = throwable;

    while (true) {

      if (current == null) {
        return null;
      }

      if (current instanceof SQLException) {
        return (SQLException) current;
      }

      if (checked.contains(current)) {
        return null;
      }
      checked.add(current);

      current = current.getCause();

    }

  }
}
