package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.controller.model.ClientDetails;
import kz.greetgo.sandbox.controller.model.PhoneDisplay;
import kz.greetgo.sandbox.register.dao_model.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ClientTestDao {

  @Insert("insert into Client (id, surname, name, patronymic, gender, birth_date, charm) " +
    "values (#{id}, #{surname}, #{name}, #{patronymic}, #{gender}::gender, #{birth_date}, #{charm}) " +
    "on conflict (id) do update set actual = 1;")
  void insertClient(Client client);

  @Insert("insert into Client_account (id, client, money, number) " +
    "values (#{id}, #{client}, #{money}, #{number}) " +
    "on conflict (id) do update set actual = 1;")
  void insertClientAccount(ClientAccount clientAccount);

  @Insert("insert into Client_addr (client, type, street, house, flat) " +
    "values (#{client}, #{type}::addr, #{street}, #{house}, #{flat}) " +
    "on conflict (client, type) do update set actual = 1;")
  void insertClientAddr(ClientAddr clientAddr);

  @Insert("insert into Client_phone (id, client, type, number) " +
    "values (#{id}, #{client}, #{type}::phone, #{number}) " +
    "on conflict (id) do update set actual = 1;")
  void insertClientPhone(ClientPhone clientPhone);

  @Insert("insert into Transaction_type (id, code, name) " +
    "values (#{id}, #{code}, #{name}) " +
    "on conflict (id) do update set actual = 1;")
  void insertTransactionType(TransactionType transactionType);

  @Insert("insert into Client_account_transaction (id, account, money, type) " +
    "values (#{id}, #{account}, #{money}, #{type}) " +
    "on conflict (id) do update set actual = 1;")
  void insertClientAccountTransaction(ClientAccountTransaction clientAccountTransaction);

  @Select("select cl.id, cl.surname, cl.name, cl.patronymic, cl.birth_date as birthDate, cl.charm as characterId, cl.gender, " +
    "rA.street as streetRegistration, rA.house as houseRegistration, rA.flat as apartmentRegistration, " +
    "fA.street as streetResidence, fA.house as houseResidence, fA.flat as apartmentResidence " +

    "from Client as cl " +

    "left join Client_addr as rA on cl.id = rA.client and rA.type = 'REG' " +
    "left join Client_addr as fA on cl.id = fA.client and fA.type = 'FACT' " +

    "where cl.id = #{id} and cl.actual = 1")
  ClientDetails details(int id);

  @Select("Select id, type, number " +
    "from Client_phone " +
    "where client = #{id} and actual = 1")
  List<PhoneDisplay> getClientPhones(int id);

  @Select("select actual from Client " +
    "where id = #{id}")
  int getClientActual(@Param("id") int id);

  @Update("" +
    "update Client set actual=0 where actual=1;" +
    "update Client_account set actual=0 where actual=1;" +
    "update Transaction_type set actual=0 where actual=1;" +
    "update Client_account_transaction set actual=0 where actual=1;" +
    "update Client_phone set actual=0 where actual=1;" +
    "update Client_addr set actual=0 where actual=1;")
  void removeAll();
}
