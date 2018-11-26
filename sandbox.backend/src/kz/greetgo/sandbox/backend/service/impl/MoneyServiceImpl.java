package kz.greetgo.sandbox.backend.service.impl;

import kz.greetgo.sandbox.backend.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

@SuppressWarnings("SqlResolve")
@Component
public class MoneyServiceImpl implements MoneyService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public void create(String id, String room, long amount) {

    jdbcTemplate.execute(

        "insert into money (id, room, amount) values (?, ?, ?)",

        (PreparedStatementCallback<Void>) ps -> {
          ps.setString(1, id);
          ps.setString(2, room);
          ps.setLong(3, amount);
          ps.executeUpdate();
          return null;
        });
  }

  @Override
  public long amountSum(String room) {
    //noinspection ConstantConditions
    return jdbcTemplate.execute(

        "select sum(amount) from money where room = ?",

        (PreparedStatementCallback<Long>) ps -> {

          ps.setString(1, room);

          try (ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) {
              throw new RuntimeException("No result");
            }

            return rs.getLong(1);
          }
        });
  }

  @Override
  public long getAmount(String id) {
    //noinspection ConstantConditions
    return jdbcTemplate.execute(

        "select amount from money where id = ? for update",

        (PreparedStatementCallback<Long>) ps -> {

          ps.setString(1, id);

          try (ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) {
              throw new RuntimeException("No money with id = " + id);
            }

            return rs.getLong(1);
          }
        });
  }

  @Override
  public void setAmount(String id, long amount) {
    jdbcTemplate.execute(

        "update money set amount = ? where id = ?",

        (PreparedStatementCallback<Void>) ps -> {
          ps.setLong(1, amount);
          ps.setString(2, id);
          int count = ps.executeUpdate();
          if (count != 1) {
            throw new RuntimeException("No money with id = " + id);
          }
          return null;
        });
  }
}
