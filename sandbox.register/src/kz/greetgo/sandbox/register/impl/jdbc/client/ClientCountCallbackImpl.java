package kz.greetgo.sandbox.register.impl.jdbc.client;

import kz.greetgo.sandbox.controller.model.ClientToFilter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientCountCallbackImpl extends ClientCallbackAbstract<Integer, Integer> {
  public ClientCountCallbackImpl(ClientToFilter filter) {
    super(filter);
  }

  @Override
  protected void select() {
    sql.append("select count(cl.id) as count \n");
  }

  @Override
  public Integer doInConnection(Connection con) throws Exception {
    prepareSql();

    try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
      for (int i = 1; i <= params.size(); i++) {
        ps.setObject(i, params.get(i - 1));
      }

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return fromRs(rs);
        }
      }
    }

    throw new RuntimeException("query did not return count(int)");
  }

  @Override
  protected Integer fromRs(ResultSet rs) throws SQLException {
    return rs.getInt("count");
  }
}
