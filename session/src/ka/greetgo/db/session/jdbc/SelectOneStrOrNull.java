package ka.greetgo.db.session.jdbc;

import kz.greetgo.db.ConnectionCallback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SelectOneStrOrNull implements ConnectionCallback<String> {

  private final String sql;

  public SelectOneStrOrNull(String sql) {
    this.sql = sql;
  }

  @Override
  public String doInConnection(Connection con) throws Exception {
    try (PreparedStatement ps = con.prepareStatement(sql)) {
      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next()) return null;
        return rs.getString(1);
      }
    }
  }
}
