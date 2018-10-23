package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.register.dao_model.Character;
import kz.greetgo.sandbox.register.dao_model.*;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.*;
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
    "where number = #{account_number}")
  ClientAccount getAccountByAccountNumber(String account_number);

  @Select("select * " +
    "from Client_account_transaction " +
    "where money = #{money} and finished_at = to_timestamp(#{finished_at}, 'YYYY-MM-DD hh24:mi:ss') and account = #{account}")
  ClientAccountTransaction getTransaction(@Param("money") Double money, @Param("finished_at") String finished_at, @Param("account") int account);
}
