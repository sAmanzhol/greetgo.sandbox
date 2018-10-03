package kz.greetgo.sandbox.register.impl.jdbc;

import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.ClientToFilter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ClientListCallbackAbstract<T> extends ClientCallbackAbstract<T, ClientRecord> {

  public ClientListCallbackAbstract(ClientToFilter filter) {
    super(filter);
  }

  @Override
  protected void select() {
    sql.append("select ");
    sql.append("cl.id, ");
    sql.append("cl.surname || ' ' || cl.name || ' ' || cl.patronymic as fio, ");
    sql.append("ch.name as character, ");
    sql.append("(extract(year from age(cl.birth_date))) as age, ");
    sql.append("sum(ac.money) as balance, ");
    sql.append("min(ac.money) as balanceMin, ");
    sql.append("max(ac.money) as balanceMax \n");
  }

  @Override
  protected void leftJoin() {
    sql.append("left join Charm as ch on cl.charm = ch.id ");
    sql.append("left join Client_account as ac on cl.id = ac.client \n");
  }

  @Override
  protected void groupBy() {
    sql.append("group by cl.id, ch.name \n");
  }

  @Override
  protected void orderBy() {
    super.orderBy();
    sql
      .append("order by ")
      .append(filter.sortColumn)
      .append(" ")
      .append(filter.sortDirection)
      .append("\n");
  }

  @Override
  public abstract T doInConnection(Connection con) throws Exception;

  @Override
  protected ClientRecord fromRs(ResultSet rs) throws SQLException {
    ClientRecord clientRecord = new ClientRecord();
    clientRecord.id = rs.getInt("id");
    clientRecord.fio = rs.getString("fio");
    clientRecord.character = rs.getString("character");
    clientRecord.age = rs.getInt("age");
    clientRecord.balance = rs.getInt("balance");
    clientRecord.balanceMax = rs.getInt("balanceMax");
    clientRecord.balanceMin = rs.getInt("balanceMin");

    return clientRecord;
  }
}
