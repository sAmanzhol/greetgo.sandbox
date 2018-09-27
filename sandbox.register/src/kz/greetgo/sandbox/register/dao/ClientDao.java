package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.Address;
import kz.greetgo.sandbox.controller.model.TransactionInfo;
import kz.greetgo.sandbox.controller.model.db.CharmDb;
import kz.greetgo.sandbox.controller.model.db.ClientAddrDb;
import kz.greetgo.sandbox.controller.model.db.ClientDb;
import kz.greetgo.sandbox.controller.model.db.ClientPhoneDb;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ClientDao {


  @Select("select id, surname, name, patronymic, gender, birth_date, charm from client where id=#{id}")
  ClientDb getClientDb(@Param("id") int id);

  @Select("select client, type, street, house, flat from client_addr where client=#{client} and type = #{type}")
  ClientAddrDb getClientAddrDb(@Param("id") int id,
                               @Param("type") String type);
//  @Insert("insert into ")

  @Select("select client, number, type from client_phone where client=#{client} and number=#{number}")
  ClientPhoneDb getClientPhoneDb(@Param("client") int client,
                                 @Param("number") String number);

  @Select("select * from charm")
  List<CharmDb> getCharacters();

  @Select("select distinct gender from client")
  List<String> getGenders();

  @Select("select distinct type\n" +
    "from client_phone")
  List<String> getPhoneD();

  @Select("select charm.name from charm join client c on charm.id = c.charm and c.id = #{id}")
  String getCharmName(@Param("id") int id);

  @Select("select * from charm join client c on charm.id = c.charm and c.id = #{id}")
  CharmDb getCharm(@Param("id") int id);

  @Select("select addr.street, addr.house, addr.flat " +
    "from client_addr addr " +
    "join client c on addr.client = c.id and addr.type = #{type} and c.id = #{id}")
  Address getAddress(@Param("id") int id,
                     @Param("type") String type);

  @Select("select * from client_phone where client = #{id}")
  List<ClientPhoneDb> phoneList(@Param("id") int id);


  //save OrUpdate
  @Select("select id from charm where name = #{name} and actual = true")
  Integer getCharmByName(@Param("name") String name);


  @Select("insert into  client (surname, name, patronymic, gender, birth_date, charm, actual)\n" +
    "  values (#{client.surname}, #{client.name}, #{client.patronymic}, " +
    "    #{client.gender}, #{client.birthDate}, #{client.charm}, true)\n" +
    "  on conflict (id) do update set\n" +
    "    surname = excluded.surname,\n" +
    "    name = excluded.name,\n" +
    "    patronymic = excluded.patronymic,\n" +
    "    gender = excluded.gender,\n" +
    "    birth_date = excluded.birth_date,\n" +
    "    charm = excluded.charm,\n" +
    "    actual = true;")
  void saveOrUpdateClient(@Param("client") ClientDb client);


  @Select("with client as (insert into client (surname, name, patronymic, gender, birth_date, charm, actual) " +
    "values (#{clientDb.surname}, #{clientDb.name}, #{clientDb.patronymic}, #{clientDb.gender}, #{clientDb.birthDate}, 1, true)" +
    " returning id) " +
    "select * from client")
  int insertClient(@Param("clientDb") ClientDb clientDb);

  @Select("select * from client_phone where client = #{id} and actual = true")
  List<ClientPhoneDb> getPhoneList(@Param("id") int id);

  @Select("insert into client_addr(client, type, street, house, flat, actual)\n" +
    "values(#{address.client}, #{address.type}, #{address.street}, #{address.house}, #{address.flat}, true)\n" +
    "on conflict (client, type) do update set\n" +
    "street = excluded.street,\n" +
    "house = excluded.house,\n" +
    "flat = excluded.flat," +
    "actual = true;")
  void saveOrUpdateAddress(@Param("address") ClientAddrDb address);

  @Select("insert into client_phone(client, number, type, actual)\n" +
    "    values(#{phone.client}, #{phone.number}, #{phone.type}, true)\n" +
    "on conflict(client, number) do update set\n" +
    "actual = true,\n" +
    "type = excluded.type;")
  void saveOrUpdatePhone(@Param("phone") ClientPhoneDb phone);

  @Select("update client_phone set actual = false where client " +
    "= #{client} and number= #{number}")
  void deactualPhone(@Param("client") int client, @Param("number") String number);

  @Select("select\n" +
    "  x1.id,\n" +
    "  x.sum,\n" +
    "  x.min,\n" +
    "  x.max\n" +
    "from (\n" +
    "       select\n" +
    "         max(a.money),\n" +
    "         min(a.money),\n" +
    "         sum(a.money),\n" +
    "         c.id\n" +
    "       from client as c\n" +
    "         left join client_account as a\n" +
    "           on c.id = a.client\n" +
    "       group by c.id\n" +
    "     ) x\n" +
    "  join client x1 on x1.id = x.id\n" +
    "--   join client_account x2 on x1.id = x2.client\n" +
    "where x1.id = #{id};")
  TransactionInfo getTransactionInfo(@Param("id") int id);

  //delete
  @Update("update client set actual = false where id = #{id};")
  void deactualClient(@Param("id") int id);

  @Select("with client_account as(update client_account set actual = false " +
    "where client = #{clientId} returning id) select * from client_account;")
  List<Integer> deactualAccounts(@Param("clientId") int clientId);


  @Update("update client_account_transaction set actual = false where account = #{accountId};")
  void deactualTransactions(@Param("accountId") int accountId);

  @Update("update client_addr set actual = false where client = #{clientId} and type = #{type};")
  void deactualAddress(@Param("clientId") int clientId, @Param("type") String type);

  @Insert("insert into charm (name, description, energy) values (#{name}, #{description}, #{energy})" +
    " on conflict (name) do nothing")
  void upsert(@Param("name") String name, @Param("description") String description, @Param("energy") double energy);

}
