package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.register.dao_model.Character;
import kz.greetgo.sandbox.register.dao_model.*;
import kz.greetgo.sandbox.register.dao_model.temp.*;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MigrationTestDao {

  @Select("select client, account_number, registered_at " +
    "from Client_account_temp " +
    "where status = 1")
  List<FrsAccount> getAccounts();

  @Select("select transaction_type, account_number, finished_at, money " +
    "from Client_account_transaction_temp " +
    "where status = 1")
  List<FrsTransaction> getAccountTransactions();

  @Select("select status " +
    "from client_account_temp " +
    "where client = '' or client isnull or client = 'null'")
  List<Integer> getAccountsWithoutClients();

  @Select("select status " +
    "from client_account_transaction_temp " +
    "where account_number = '' or account_number isnull or account_number = 'null'")
  List<Integer> getTransactionsWithoutAccounts();


  @Select("select id, surname, name, patronymic, gender, birth_date, charm, status " +
    "from Client_temp " +
    "where status = 1")
  List<CiaClient> getClients();

  @Select("select status " +
    "from client_temp " +
    "where surname = '' or surname isnull or surname = 'null'")
  List<Integer> getClientsWithoutSurname();

  @Select("select status " +
    "from client_temp " +
    "where name = '' or name isnull or name = 'null'")
  List<Integer> getClientsWithoutName();

  @Select("select status " +
    "from client_temp " +
    "where birth_date = '' or birth_date isnull or birth_date = 'null' " +
    " or (extract(year from age(to_timestamp(birth_date, 'YYYY-MM-DD')))) < 3 " +
    " or (extract(year from age(to_timestamp(birth_date, 'YYYY-MM-DD')))) > 3000")
  List<Integer> getClientsWithoutBirthDate();

  @Select("select type, client, number " +
    "from Client_phone_temp " +
    "where status = 1")
  List<CiaPhone> getClientPhones();

  @Select("select type, client, street, house, flat " +
    "from Client_addr_temp " +
    "where status = 1")
  List<CiaAddress> getClientAddresses();

  @Select("select * " +
    "from Client " +
    "where migration_id = #{migration_id}")
  Client getClientByMigrationId(String migration_id);

  @Select("select * " +
    "from Transaction_type " +
    "where id = #{id}")
  TransactionType getTransactionTypeById(int id);

  @Select("select * " +
    "from Transaction_type " +
    "where name = #{name}")
  TransactionType getTransactionTypeByName(String name);

  @Select("select type, number " +
    "from Client_phone " +
    "where client = #{id}")
  List<ClientPhone> getClientPhonesById(int id);

  @Select("select type, street, house, flat " +
    "from Client_addr " +
    "where client = #{id}")
  List<ClientAddress> getClientAddressesById(int id);

  @Select("select * " +
    "from Charm " +
    "where id = #{id}")
  Character getCharmById(int id);

  @Select("select * " +
    "from Charm " +
    "where name = #{name}")
  Character getCharmByName(String name);

  @Select("select * " +
    "from Client_account " +
    "where number = #{account_number} " +
    "and actual = 1")
  ClientAccount getAccountByNumber(String account_number);

  @Select("select * " +
    "from Client_account_transaction " +
    "where money = #{money} and finished_at = to_timestamp(#{finished_at}, 'YYYY-MM-DD hh24:mi:ss') and account = #{account}")
  ClientAccountTransaction getTransaction(@Param("money") Double money, @Param("finished_at") String finished_at, @Param("account") int account);


  @Insert("insert into Client (id, surname, name, patronymic, gender, birth_date, charm, migration_id) " +
    "values (#{id}, #{surname}, #{name}, #{patronymic}, #{gender}::gender, #{birthDate}, #{charm}, #{migration_id}) " +
    "on conflict (id) do update set actual = 1;")
  void insertClient(Client client);


  @Select("select currval('migration_order')")
  Integer getCurrentMigrationOrder();

  @Insert("insert into Client_temp (id, surname, name, patronymic, gender, birth_date, charm, status, migration_order) " +
    "values (#{id}, #{surname}, #{name}, #{patronymic}, #{gender}, #{birthDate}, #{charm}, #{status}, nextval('migration_order'))")
  void insertClientTemp(ClientTemp clientTemp);

  @Insert("insert into Client_addr_temp (type, client, street, house, flat, status, migration_order) " +
    "values (#{type}, #{client}, #{street}, #{house}, #{flat}, #{status}, #{migrationOrder})")
  void insertClientAddressTemp(ClientAddressTemp clientAddressTemp);

  @Insert("insert into Client_phone_temp (type, client, number, status, migration_order) " +
    "values (#{type}, #{client}, #{number}, #{status}, #{migrationOrder})")
  void insertClientPhoneTemp(ClientPhoneTemp clientPhoneTemp);


  @Insert("insert into Client_account_temp (client, account_number, registered_at, status, migration_order) " +
    "values (#{client}, #{accountNumber}, #{registeredAt}, #{status}, nextval('migration_order'))")
  void insertClientAccountTemp(ClientAccountTemp clientAccountTemp);

  @Insert("insert into Client_account_transaction_temp (transaction_type, account_number, finished_at, money, status) " +
    "values (#{transactionType}, #{accountNumber}, #{finishedAt}, #{money}, #{status})")
  void insertClientAccountTransactionTemp(ClientAccountTransactionTemp clientAccountTransactionTemp);

  @Insert("insert into Client_account (id, client, money, number, registered_at, actual, migration_client) " +
    "values (nextval('id'), #{client}, #{money}, #{number}, #{registeredAt}, #{actual}, #{migrationClient}) " +
    "on conflict (number) do update " +
    "set actual = 1")
  void insertClientAccount(ClientAccount clientAccount);

  @Insert("insert into Client_account_transaction (id, account, money, finished_at, type, actual, migration_account) " +
    "values (nextval('id'), #{account}, #{money}, #{finishedAt}, #{type}, #{actual}, #{migrationAccount}) " +
    "on conflict (migration_account, money, finished_at) do update " +
    "set actual = 1")
  void insertClientAccountTransaction(ClientAccountTransaction clientAccountTransaction);

  @Insert("insert into Transaction_type (id, code, name) " +
    "values (nextval('id'), nextval('code'), #{name}) " +
    "on conflict (name) do update " +
    "set actual = 1")
  void insertTransactionType(TransactionType transactionType);
}
