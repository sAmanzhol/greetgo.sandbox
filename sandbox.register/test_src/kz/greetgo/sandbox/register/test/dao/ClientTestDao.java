package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.controller.model.db.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClientTestDao {

  @Select("insert into charm(name, description, energy, actual)\n" +
    "    values ('OPENNESS', 'OPENNESS', 1, true),\n" +
    "      ('CONSCIENTIOUSNESS', 'CONSCIENTIOUSNESS', 2, true),\n" +
    "      ('EXTRAVERSION', 'EXTRAVERSION', 3, true),\n" +
    "      ('AGREEABLENESS', 'AGREEABLENESS', 4, true),\n" +
    "      ('NEUROTICISM', 'NEUROTICISM', 5, true);")
  void insertDefaultCharms();

  @Select("with charm as (insert into charm (name, description, energy, actual) " +
    "values (#{charmDb.name}, #{charmDb.description}, #{charmDb.energy}, true) returning id)" +
    " select * from charm")
  int insertCharm(@Param("charmDb") CharmDb charmDb);


  @Select("with client as (insert into client (surname, name, patronymic, gender, birth_date, charm, actual) " +
    "values (#{clientDb.surname}, #{clientDb.name}, #{clientDb.patronymic}, #{clientDb.gender}, #{clientDb.birthDate}, #{clientDb.charm}, true)" +
    " returning id) " +
    "select * from client")
  int insertClientDb(@Param("clientDb") ClientDb clientDb);

  @Select("with client_account as (insert into client_account(client, money, number, registered_at, actual)\n" +
    "    values(#{clientAccountDb.client}, #{clientAccountDb.money},#{clientAccountDb.number}, #{clientAccountDb.registeredAt},\n" +
    "true) returning id) select * from client_account")
  int insertClientAccountDb(@Param("clientAccountDb") ClientAccountDb clientAccountDb);


  @Select("with transaction_type as(insert into transaction_type(code, name, actual) " +
    "values(#{transactionTypeDb.code}, #{transactionTypeDb.name}, true) returning id) " +
    "select * from transaction_type")
  int insertTransactionType(@Param("transactionTypeDb") TransactionTypeDb transactionTypeDb);

  @Select("with client_account_transaction as (insert into client_account_transaction(account, money, finished_at, type, actual)" +
    "values(#{clientATDb.account}, #{clientATDb.money}, #{clientATDb.finishedAt}, #{clientATDb.type}, true) returning id)" +
    "select * from client_account_transaction")
  int insertClientAccountTransaction(@Param("clientATDb") ClientAccountTransactionDb clientATDb);

  @Select("select addr.client, addr.street, addr.house, addr.flat " +
    "from client_addr addr " +
    "where addr.type = #{type} and addr.client = #{id}")
  ClientAddrDb getAddress(@Param("id") int id,
                          @Param("type") String type);

  @Select("select * from client_phone where client = #{id} and actual = true")
  List<ClientPhoneDb> getPhoneList(@Param("id") int id);

  @Select("select id, surname, name, patronymic, gender, birth_date, charm from client where id=#{id} and actual = true")
  ClientDb getClientDb(@Param("id") int id);


  @Select("select *\n" +
    "from client_addr\n" +
    "where client = #{client} and type = #{type} and actual = true")
  ClientAddrDb getAddr(@Param("client") int client, @Param("type") String type);

  @Insert("insert into client_addr(client, type, street, house, flat)\n" +
    "values(#{addr.client}, #{addr.type}, #{addr.street}, #{addr.house}, #{addr.flat})\n" +
    "on conflict (client, type) do update set\n" +
    "street = excluded.street,\n" +
    "house = excluded.house,\n" +
    "flat = excluded.flat;")
  void insertAddress(@Param("addr") ClientAddrDb addr);


  @Select("select * from charm where  name = #{name} and actual = true")
  CharmDb getCharmByName(@Param("name") String name);


  @Select("insert into  client (id, surname, name, patronymic, gender, birth_date, charm, actual)\n" +
    "  values (#{client.id}, #{client.surname}, #{client.name}, #{client.patronymic}, " +
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

  @Select("select * from client_account where id = #{id} and actual = true")
  ClientAccountDb getClientAcc(@Param("id") int id);

  @Select("select * from client_account_transaction  where account = #{accountId} and actual = true;")
  ClientAccountTransactionDb getAccountTransaction(@Param("accountId") int accountId);

  @Select("select * from client_addr where client = #{clientId} and type = #{type} and actual = true;")
  ClientAddrDb getAddressDb(@Param("clientId") int clientId, @Param("type") String type);

  @Select("select * from client_phone where select * from client_phone where client = #{id} and number = #{number} " +
    "actual = true")
  ClientPhoneDb getPhone(@Param("id") int id, @Param("number") String number);

  @Select("select * from client_account_transaction where account = #{account} and actual = true;")
  List<ClientAccountTransactionDb> getTransactions(@Param("account") int account);

  @Delete("truncate table client cascade;")
  void truncateClient();

  @Delete("truncate table client_account_transaction cascade;")
  void truncateAccountTransaction();

  @Delete("truncate table client_account cascade;")
  void truncateAccount();

  @Delete("truncate table client_addr cascade;")
  void truncateAddress();

  @Delete("truncate table client_phone cascade;")
  void truncatePhones();

  @Delete("truncate table transaction_type cascade;")
  void trunccateTransactionType();

  @Delete("truncate table charm cascade;")
  void truncateCharms();


}
