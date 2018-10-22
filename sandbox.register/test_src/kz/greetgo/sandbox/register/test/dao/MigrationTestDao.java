package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.register.impl.jdbc.migration.model.*;
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
    "where migration_id = #{id}")
  CiaClient getClientByMigrationId(String id);

  @Select("select type, number " +
    "from Client_phone " +
    "where client = #{id}")
  List<CiaPhone> getClientPhonesById(int id);

  @Select("select type, street, house, flat " +
    "from Client_addr " +
    "where client = #{id}")
  List<CiaAddress> getClientAddressesById(int id);
}
