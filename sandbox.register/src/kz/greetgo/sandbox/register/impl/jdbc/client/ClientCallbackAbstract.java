package kz.greetgo.sandbox.register.impl.jdbc.client;


import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.sandbox.controller.model.ClientToFilter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public abstract class ClientCallbackAbstract<T, R> implements ConnectionCallback<T> {

  protected final ClientToFilter filter;
  protected StringBuilder sql = new StringBuilder();
  protected List<Object> params = new ArrayList<>();

  public ClientCallbackAbstract(ClientToFilter filter) {
    this.filter = filter;
  }

  protected void prepareSql() {
    select();
    from();
    leftJoin();
    where();
    groupBy();
    orderBy();
    limit();
  }

  protected abstract void select();

  private void from() {
    sql.append("from Client as cl \n");
  }

  protected void leftJoin() {
  }

  protected void where() {
    sql.append("where cl.actual = 1 ");

    if (filter.fio != null && !filter.fio.trim().isEmpty()) {
      sql.append("and lower(cl.surname || ' ' || cl.name || ' ' || cl.patronymic) like '%' || lower(?) || '%' ");
      params.add(filter.fio);
    }
  }

  protected void groupBy() {
  }

  protected void orderBy() {
  }

  protected void limit() {
  }

  @Override
  public abstract T doInConnection(Connection con) throws Exception;

  protected abstract R fromRs(ResultSet rs) throws SQLException;
}