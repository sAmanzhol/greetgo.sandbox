package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.controller.model.Client;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ClientTestDao {

  @Insert("with client as (insert into client (surname, name, patronymic, gender, birth_date, charm) " +
    "values (#{client.surname}, #{client.name}, #{client.patronymic}, #{client.gender}, #{client.birthDate}, #{client.charm}) returning id)")
  int insertClient(@Param("client") Client client
  );

  @Select("select id from client where surname = #{client.surname}, name = #{client.name}, patronymic = #{client.patronymic}," +
    "birthDate = #{client.birthDate}, gender = #{client.gender}, charm = #{client.charm}")
  long getId(@Param("client") Client client);

  @Select("select name from client where id = #{id}")
  String getName(@Param("id") Long id);


}
