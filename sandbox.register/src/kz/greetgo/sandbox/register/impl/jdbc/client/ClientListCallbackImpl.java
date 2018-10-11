package kz.greetgo.sandbox.register.impl.jdbc.client;

import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.ClientToFilter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientListCallbackImpl extends ClientListCallbackAbstract<List<ClientRecord>> {

  public ClientListCallbackImpl(ClientToFilter filter) {
    super(filter);
  }

  @Override
  public List<ClientRecord> doInConnection(Connection con) throws Exception {
    List<ClientRecord> ret = new ArrayList<>();
    prepareSql();

    try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
      for (int i = 1; i <= params.size(); i++) {
        ps.setObject(i, params.get(i - 1));
      }

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          ret.add(fromRs(rs));
        }
      }
    }

    return ret;
  }

  @Override
  protected void limit() {
    sql.append("offset ? limit ? ");
    params.add((filter.page - 1) * filter.itemCount);
    params.add(filter.itemCount);
  }
}
