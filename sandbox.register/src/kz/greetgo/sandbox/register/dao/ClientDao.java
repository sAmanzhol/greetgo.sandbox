package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.ClientDetails;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.PhoneDisplay;
import kz.greetgo.sandbox.register.dao_model.Client;
import kz.greetgo.sandbox.register.dao_model.ClientAddr;
import kz.greetgo.sandbox.register.dao_model.ClientPhone;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ClientDao {
  @Insert("insert into Client (id, surname, name, patronymic, gender, birth_date, charm) " +
    "values (nextval('id'), #{surname}, #{name}, #{patronymic}, #{gender}::gender, #{birth_date}, #{charm})")
  void insertClient(Client client);

  @Insert("insert into Client_addr (client, type, street, house, flat) " +
    "values (#{client}, #{type}::addr, #{street}, #{house}, #{flat}) " +
    "on conflict (client, type) do update set actual = 1;")
  void insertClientAddr(ClientAddr clientAddr);

  @Insert("insert into Client_phone (id, client, type, number) " +
    "values (nextval('id'), #{client}, #{type}::phone, #{number}) " +
    "on conflict (client, number) do update set actual = 1;")
  void insertClientPhone(ClientPhone clientPhone);


  @Select("select currval('id')")
  int getId();

  @Select("select cl.id, cl.surname || ' ' || cl.name || ' ' || cl.patronymic as fio, ch.name as character, (extract(year from age(cl.birth_date))) as age, " +
    "sum(ac.money) as balance, min(ac.money) as balanceMin, max(ac.money) as balanceMax " +
    "from Client as cl " +
    "left join Charm as ch on cl.charm = ch.id " +
    "left join Client_account as ac on cl.id = ac.client " +
    "where cl.id = #{id} and cl.actual = 1 " +
    "group by cl.id, ch.name")
  ClientRecord getClientRecord(int id);

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

  @Update("update Client " +
    "set surname = #{surname}, name = #{name}, patronymic = #{patronymic}, birth_date = #{birth_date}, gender = #{gender}::gender, charm = #{charm} " +
    "where id = #{id} and actual = 1")
  void updateClient(Client client);

  @Update("update Client_addr " +
    "set street = #{street}, house = #{house}, flat = #{flat} " +
    "where client = #{client} and type = #{type}::addr and actual = 1")
  void updateClientAddr(ClientAddr clientAddr);

  @Update("update Client_phone " +
    "set number = #{number}, type = #{type}::phone " +
    "where id = #{id} and actual = 1")
  void updateClientPhone(ClientPhone clientPhone);

  @Update("update Client_phone " +
    "set actual = 0 " +
    "where id = #{id}")
  void deleteClientPhone(int id);

  @Update("update Client " +
    "set actual = 0 " +
    "where id = #{id}")
  void deleteClient(int id);
}
