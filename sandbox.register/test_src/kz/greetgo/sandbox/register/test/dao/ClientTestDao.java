package kz.greetgo.sandbox.register.test.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface ClientTestDao {
  @Insert("insert into Client (surname, name, patronymic, gender, birth_date, charm) " +
    "values (#{surname}, #{name}, #{patronymic}, #{gender}::gender, #{birth_date}, #{charm})")
  void insertClient(@Param("surname") String surname,
                    @Param("name") String name,
                    @Param("patronymic") String patronymic,
                    @Param("gender") String gender,
                    @Param("birth_date") Date birth_date,
                    @Param("charm") int charm);

  @Insert("insert into Client_addr (client, type, street, house, flat) " +
    "values (#{client}, #{type}::addr, #{street}, #{house}, #{flat})")
  void insertClientAddr(@Param("client") int client,
                        @Param("type") String type,
                        @Param("street") String street,
                        @Param("house") String house,
                        @Param("flat") String flat);

  @Insert("insert into Client_phone (client, type, number) " +
    "values (#{client}, #{type}::phone, #{number})")
  void insertClientPhone(@Param("client") int client,
                         @Param("type") String type,
                         @Param("number") String number);

  @Insert("insert into Client_account (client, number) " +
    "values (#{client}, #{number})")
  void insertClientAccount(@Param("client") int client,
                           @Param("number") String number);


  @Insert("insert into Transaction_type (code, name) " +
    "values (#{code}, #{name})")
  void insertTransactionType(@Param("code") String code,
                             @Param("name") String name);

  @Insert("insert into Client_account_transaction (account, money, type) " +
    "values (#{account}, #{money}, #{type})")
  void insertClientAccountTransaction(@Param("account") int account,
                                      @Param("money") float money,
                                      @Param("type") int type);
}
