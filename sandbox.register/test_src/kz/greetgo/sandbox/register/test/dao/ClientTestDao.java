package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.controller.model.ClientDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ClientTestDao {
  @Insert("insert into charm values (#{id}, #{name})")
  void insertCharm(@Param("id") int id,
                   @Param("name") String name);

  @Insert("insert into client (id, surname, name, gender, birth_date, charm) " +
    "values (#{id}, #{surname}, #{name}, #{gender}, #{birth_date}, #{charm})")
  void insertClient(@Param("id") int id,
                    @Param("surname") String surname,
                    @Param("name") String name,
                    @Param("gender") String gender,
                    @Param("birth_date") Object birth_date,
                    @Param("charm") int charm
  );

  @Insert("insert into client (id, surname, name, actual) " +
    "values (#{id}, #{surname}, #{name}, #{actual})")
  void insertNotFullClient(@Param("id") int id,
                    @Param("surname") String surname,
                    @Param("name") String name,
                    @Param("actual") int actual
  );

  @Insert("insert into client_account (id , client, money)" +
          "values (#{id}, #{client}, 0.0)")
  void insertClientAccount(@Param("id") int id,
                           @Param("client") int client);

  @Update("update client set actual = 0")
  void deleteAllClients();

  @Select("select * from client where id=#{id}")
  ClientDetail selectClientById(@Param("id") int id);
}
