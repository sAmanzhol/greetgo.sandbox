package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.db.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ClientTestDao {


  @Select("with charm as (insert into charm (name, description, energy) " +
    "values (#{charmDb.name}, #{charmDb.description}, #{charmDb.energy}) returning id)" +
    " select * from charm")
  int insertCharm(@Param("charmDb") CharmDb charmDb);


  @Select("with client as (insert into client (surname, name, patronymic, gender, birth_date, charm) " +
    "values (#{clientDb.surname}, #{clientDb.name}, #{clientDb.patronymic}, #{clientDb.gender}, #{clientDb.birthDate}, 1) returning id) " +
    "select * from client")
  int insertClientDb(@Param("clientDb") ClientDb clientDb);


  @Select("with client_account as (insert into client_account(client, money, number, registered_at) " +
    "values(#{clientAccountDb.client}, #{clientAccountDb.money}, #{clientAccountDb.number}, #{clientAccountDb.registeredAt}) returning id) " +
    "select * from client_account")
  int insertClientAccountDb(@Param("clientAccountDb") ClientAccountDb clientAccountDb);

  @Select("with transaction_type as(insert into transaction_type(code, name) " +
    "values(#{transactionTypeDb.code}, #{transactionTypeDb.name}) returning id) " +
    "select * from transaction_type")
  int insertTransactionType(@Param("transactionTypeDb") TransactionTypeDb transactionTypeDb);

  @Select("with client_account_transaction as (insert into client_account_transaction(account, money, finished_at, type)" +
    "values(#{clientATDb.account}, #{clientATDb.money}, #{clientATDb.finishedAt}, #{clientATDb.type}) returning id)" +
    "select * from client_account_transaction")
  int insertClientAccountTransaction(@Param("clientATDb") ClientAccountTransactionDb clientATDb);

  @Select("with client as (insert into client (surname, name, patronymic, gender, birth_date, charm) " +
    "values (#{client.surname}, #{client.name}, #{client.patronymic}, 'MALE', #{client.birthDay}, 1) returning id) " +
    "select * from client")
  int insertClient(@Param("client") Client client);

  @Select("select id from client where surname = #{client.surname}, name = #{client.name}, patronymic = #{client.patronymic}," +
    "birthDate = #{client.birthDate}, gender = #{client.gender}, charm = #{client.charm}")
  long getId(@Param("client") Client client);

  @Select("select name from client where id = #{id}")
  String getName(@Param("id") int id);


  @Select("select id, surname, name, patronymic, gender, birth_date, charm from client where id=#{id}")
  ClientDb getClientDb(@Param("id") int id);

//  @Insert("insert into client values(#{id}, #{surname}, #{name}, #{patronymic}, #{gender}, #{charm})")
//  void insertClientDb(@Param("id") int id,
//                      @Param("surname") String surname,
//                      @Param("name") String name,
//                      @Param("patronymic") String patronymic,
//                      @Param("gender") String gender,
//                      @Param("charm") int charm);

  @Select("select client, type, street, house, flat from client_addr where client=#{client} and type = #{type}")
  ClientAddrDb getClientAddrDb(@Param("id") int id,
                               @Param("type") String type);
//  @Insert("insert into ")

  @Select("select client, number, type from client_phone where client=#{client} and number=#{number}")
  ClientPhoneDb getClientPhoneDb(@Param("client") int client,
                                 @Param("number") String number);


}
