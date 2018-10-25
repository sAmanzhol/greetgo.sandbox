package kz.greetgo.sandbox.register.impl.jdbc.migration;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.register.configs.MigrationConfig;
import kz.greetgo.sandbox.register.dao_model.Character;
import kz.greetgo.sandbox.register.dao_model.*;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.FrsAccount;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.FrsTransaction;
import kz.greetgo.sandbox.register.test.dao.CharacterTestDao;
import kz.greetgo.sandbox.register.test.dao.MigrationTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@SuppressWarnings("WeakerAccess")
public class FrsMigrationTest extends ParentTestNg {

  public BeanGetter<MigrationConfig> migrationConfig;
  public BeanGetter<MigrationTestDao> migrationTestDao;
  public BeanGetter<CharacterTestDao> characterTestDao;

  private FrsMigrationCallbackImpl frsMigration;

  private void prepareTempTables() throws Exception {
    frsMigration = new FrsMigrationCallbackImpl("");

    frsMigration.dropTemplateTables();
    frsMigration.createTempTables();
  }


  @Test
  public void parseAndFillData_account() throws Exception {

    this.prepareTempTables();

    String fileName = "from_frs_2018-02-21-154543-1-30009.json_row";
    String filePath = String.format("%s/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    //
    //

    List<FrsAccount> accounts = migrationTestDao.get().getAccounts();

    assertThat(accounts).hasSize(3);

    assertThat(accounts.get(0).client).isEqualTo("1-A69-QA-PJ-G6hRzbEf2W");
    assertThat(accounts.get(0).accountNumber).isEqualTo("19382KZ865-20725-98987-8267359");
    assertThat(accounts.get(0).registeredAt).isEqualTo("2001-02-21T15:51:14.111");

    assertThat(accounts.get(1).client).isEqualTo("");
    assertThat(accounts.get(1).accountNumber).isEqualTo("22382KZ865");
    assertThat(accounts.get(1).registeredAt).isEqualTo("2001-02-21T15:51:14.111");

    assertThat(accounts.get(2).client).isEqualTo("10-A69-QA-PJ-G6hRzbEf2W");
    assertThat(accounts.get(2).accountNumber).isEqualTo("19382KZ865-20725-11111-8267359");
    assertThat(accounts.get(2).registeredAt).isEqualTo("2001-02-21T15:11:11.111");
  }

  @Test
  public void parseAndFillData_account_transaction() throws Exception {

    this.prepareTempTables();

    String fileName = "from_frs_2018-02-21-154543-1-30009.json_row";
    String filePath = String.format("%s/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    //
    //

    List<FrsTransaction> transactions = migrationTestDao.get().getAccountTransactions();

    assertThat(transactions).hasSize(5);

    assertThat(transactions.get(0).transactionType).isEqualTo("null");
    assertThat(transactions.get(0).accountNumber).isEqualTo("19382KZ865-20725-98987-8267359");
    assertThat(transactions.get(0).finishedAt).isEqualTo("2011-02-21T15:51:18.996");
    assertThat(transactions.get(0).money).isEqualTo("+7000");


    assertThat(transactions.get(1).transactionType).isEqualTo("Перевод в офшоры");
    assertThat(transactions.get(1).accountNumber).isEqualTo("19382KZ865-20725-98987-8267359");
    assertThat(transactions.get(1).finishedAt).isEqualTo("2011-02-21T15:51:16.996");
    assertThat(transactions.get(1).money).isEqualTo("-4321.5");

    assertThat(transactions.get(2).transactionType).isEqualTo("Списывание с регионального бюджета Алматинской области");
    assertThat(transactions.get(2).accountNumber).isNullOrEmpty();
    assertThat(transactions.get(2).finishedAt).isEqualTo("2011-02-21T15:51:14.996");
    assertThat(transactions.get(2).money).isEqualTo("+4321.5");

    assertThat(transactions.get(3).transactionType).isEqualTo("null");
    assertThat(transactions.get(3).accountNumber).isEqualTo("57743KZ372-07560-15462-1553047");
    assertThat(transactions.get(3).finishedAt).isEqualTo("2011-02-21T15:51:26.996");
    assertThat(transactions.get(3).money).isEqualTo("+0");

    assertThat(transactions.get(4).transactionType).isEqualTo("Отмывание на компьютерной технике");
    assertThat(transactions.get(4).accountNumber).isEqualTo("57743KZ372-07560-15462-1553047");
    assertThat(transactions.get(4).finishedAt).isEqualTo("2011-02-21T15:51:25.996");
    assertThat(transactions.get(4).money).isEqualTo("-3732");
  }


  @Test
  public void checkForValidness_account_client() throws Exception {

    this.prepareTempTables();

    String fileName = "from_frs_2018-02-21-154543-1-30009.json_row";
    String filePath = String.format("%s/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    frsMigration.checkForValidness();
    //
    //

    List<Integer> statuses = migrationTestDao.get().getAccountsWithoutClients();

    assertThat(statuses).hasSize(1);

    for (Integer status : statuses) {
      assertThat(status).isEqualTo(2);
    }
  }

  @Test
  public void checkForValidness_account_transaction_account_number() throws Exception {

    this.prepareTempTables();

    String fileName = "from_frs_2018-02-21-154543-1-30009.json_row";
    String filePath = String.format("%s/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    frsMigration.checkForValidness();
    //
    //

    List<Integer> statuses = migrationTestDao.get().getTransactionsWithoutAccounts();

    assertThat(statuses).hasSize(1);

    for (Integer status : statuses) {
      assertThat(status).isEqualTo(2);
    }
  }


  @Test
  public void validateAndMigrateData_insert_account() throws Exception {

    this.prepareTempTables();

    String fileName = "from_frs_2018-02-21-154543-1-30009.json_row";
    String filePath = String.format("%s/insert_account/%s", migrationConfig.get().directoryTest(), fileName);


    Character character = new Character();
    character.name = "Character insert account";
    character.description = "Description insert account";
    character.energy = 100;

    characterTestDao.get().insertCharacter(character);

    character.id = migrationTestDao.get().getCharmByName(character.name).id;

    Client client = new Client();
    client.id = 111;
    client.surname = "S";
    client.name = "N";
    client.patronymic = "";
    client.gender = "MALE";
    client.birthDate = new GregorianCalendar(2010, 10, 10).getTime();
    client.charm = character.id;
    client.migration_id = "1-A69-ia-PJ-G6hRzbEf2W";

    migrationTestDao.get().insertClient(client);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    frsMigration.checkForValidness();
    frsMigration.validateAndMigrateData();
    frsMigration.disableUnusedRecords();
    //
    //

    String accountNumber = "19382KZ865-20725-11111-8267359";

    ClientAccount account = migrationTestDao.get().getAccountByAccountNumber(accountNumber);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date parsedDate = dateFormat.parse("2001-02-21 15:51:14");
    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

    assertThat(account.client).isEqualTo(111);
    assertThat(account.number).isEqualTo("19382KZ865-20725-11111-8267359");
    assertThat(account.registeredAt).isEqualTo(timestamp);
  }

  @Test
  public void validateAndMigrateData_insert_account_no_client() throws Exception {

    this.prepareTempTables();

    String fileName = "from_frs_2018-02-21-154543-1-30009.json_row";
    String filePath = String.format("%s/insert_account_no_client/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    frsMigration.checkForValidness();
    frsMigration.validateAndMigrateData();
    frsMigration.disableUnusedRecords();
    //
    //

    String accountNumber = "19382KZ865-20725-33333-8267359";

    ClientAccount account = migrationTestDao.get().getAccountByAccountNumber(accountNumber);

    assertThat(account).isNull();
  }

  @Test
  public void validateAndMigrateData_duplicate_account() throws Exception {

    this.prepareTempTables();

    String fileName = "from_frs_2018-02-21-154543-1-30009.json_row";
    String filePath = String.format("%s/duplicate_account/%s", migrationConfig.get().directoryTest(), fileName);

    Character character = new Character();
    character.name = "Character update account";
    character.description = "Description update account";
    character.energy = 100;

    characterTestDao.get().insertCharacter(character);

    character.id = migrationTestDao.get().getCharmByName(character.name).id;

    Client client = new Client();
    client.id = 222;
    client.surname = "S";
    client.name = "N";
    client.patronymic = "";
    client.gender = "MALE";
    client.birthDate = new GregorianCalendar(2010, 10, 10).getTime();
    client.charm = character.id;
    client.migration_id = "1-A69-ua-PJ-G6hRzbEf2W";

    migrationTestDao.get().insertClient(client);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    frsMigration.checkForValidness();
    frsMigration.validateAndMigrateData();
    frsMigration.disableUnusedRecords();
    //
    //

    String accountNumber = "19382KZ865-20725-22222-8267359";

    ClientAccount account = migrationTestDao.get().getAccountByAccountNumber(accountNumber);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date parsedDate = dateFormat.parse("2001-02-21 15:51:14");
    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

    assertThat(account.client).isEqualTo(222);
    assertThat(account.number).isEqualTo("19382KZ865-20725-22222-8267359");
    assertThat(account.registeredAt).isEqualTo(timestamp);
  }


  @Test
  public void validateAndMigrateData_insert_transaction() throws Exception {

    this.prepareTempTables();

    String fileName = "from_frs_2018-02-21-154543-1-30009.json_row";
    String filePath = String.format("%s/insert_transaction/%s", migrationConfig.get().directoryTest(), fileName);


    Character character = new Character();
    character.name = "Character insert transaction";
    character.description = "Desctiption insert transaction";
    character.energy = 100;

    characterTestDao.get().insertCharacter(character);

    character.id = migrationTestDao.get().getCharmByName(character.name).id;

    Client client = new Client();
    client.id = 333;
    client.surname = "S";
    client.name = "N";
    client.patronymic = "";
    client.gender = "MALE";
    client.birthDate = new GregorianCalendar(2010, 10, 10).getTime();
    client.charm = character.id;
    client.migration_id = "1-A69-it-PJ-G6hRzbEf2W";

    migrationTestDao.get().insertClient(client);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    frsMigration.checkForValidness();
    frsMigration.validateAndMigrateData();
    frsMigration.disableUnusedRecords();
    //
    //

    ClientAccount account = migrationTestDao.get().getAccountByAccountNumber("19382KZ865-20725-44444-8267359");
    ClientAccountTransaction transaction = migrationTestDao.get().getTransaction(-4321.5, "2011-02-21 15:51:16", account.id);
    TransactionType transactionType = migrationTestDao.get().getTransactionTypeById(transaction.type);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date parsedDate = dateFormat.parse("2011-02-21 15:51:16");
    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

    assertThat(transaction.money).isEqualTo(-4321.5);
    assertThat(transaction.finishedAt).isEqualTo(timestamp);
    assertThat(transaction.account).isEqualTo(account.id);
    assertThat(transactionType.name).isEqualTo("Переводик в офшоры");
  }

  @Test
  public void validateAndMigrateData_insert_transaction_no_account() throws Exception {

    this.prepareTempTables();

    String fileName = "from_frs_2018-02-21-154543-1-30009.json_row";
    String filePath = String.format("%s/insert_transaction_no_account/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    frsMigration.checkForValidness();
    frsMigration.validateAndMigrateData();
    frsMigration.disableUnusedRecords();
    //
    //

    ClientAccount account = migrationTestDao.get().getAccountByAccountNumber("19382KZ865-20725-55555-8267359");

    assertThat(account).isNull();
  }

  @Test
  public void validateAndMigrateData_duplicate_transaction() throws Exception {

    this.prepareTempTables();

    Character character = new Character();
    character.name = "Character update transaction";
    character.description = "Desctiption update transaction";
    character.energy = 100;

    characterTestDao.get().insertCharacter(character);

    character.id = migrationTestDao.get().getCharmByName(character.name).id;

    Client client = new Client();
    client.id = 888;
    client.surname = "S";
    client.name = "N";
    client.patronymic = "";
    client.gender = "MALE";
    client.birthDate = new GregorianCalendar(2010, 10, 10).getTime();
    client.charm = character.id;
    client.migration_id = "1-A69-ut-PJ-G6hRzbEf2W";

    migrationTestDao.get().insertClient(client);

    String fileName = "from_frs_2018-02-21-154543-1-30009.json_row";
    String filePath = String.format("%s/duplicate_transaction/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    frsMigration.checkForValidness();
    frsMigration.validateAndMigrateData();
    frsMigration.disableUnusedRecords();
    //
    //

    ClientAccount account = migrationTestDao.get().getAccountByAccountNumber("19382KZ865-20725-66666-8267359");
    ClientAccountTransaction transaction = migrationTestDao.get().getTransaction(-4321.5, "2011-02-21 15:51:16", account.id);
    TransactionType transactionType = migrationTestDao.get().getTransactionTypeById(transaction.type);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date parsedDate = dateFormat.parse("2011-02-21 15:51:16");
    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

    assertThat(transaction.money).isEqualTo(-4321.5);
    assertThat(transaction.finishedAt).isEqualTo(timestamp);
    assertThat(transaction.account).isEqualTo(account.id);
    assertThat(transactionType.name).isEqualTo("Перевод");
  }


  @Test
  public void checkForLateUpdates_account_no_client() throws Exception {

    this.prepareTempTables();

    String fileName = "from_frs_2018-02-21-154543-1-30009.json_row";
    String filePath = String.format("%s/check_late_update_account/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    frsMigration.checkForValidness();
    frsMigration.validateAndMigrateData();
    frsMigration.disableUnusedRecords();
    frsMigration.checkForLateUpdates();
    //
    //

    String accountNumber = "19382KZ865-20725-12312-8267359";

    ClientAccount account = migrationTestDao.get().getAccountByAccountNumber(accountNumber);

    assertThat(account).isNull();


    Character character = new Character();
    character.name = "Character check update account";
    character.description = "Description check update account";
    character.energy = 100;

    characterTestDao.get().insertCharacter(character);

    character.id = migrationTestDao.get().getCharmByName(character.name).id;

    Client client = new Client();
    client.id = 12312;
    client.surname = "S";
    client.name = "N";
    client.patronymic = "";
    client.gender = "MALE";
    client.birthDate = new GregorianCalendar(2010, 10, 10).getTime();
    client.charm = character.id;
    client.migration_id = "1-A69-cua-PJ-G6hRzbEf2W";

    migrationTestDao.get().insertClient(client);

    fileName = "from_frs_2018-02-21-154543-1-30010.json_row";
    filePath = String.format("%s/check_late_update_account/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    frsMigration.checkForValidness();
    frsMigration.validateAndMigrateData();
    frsMigration.disableUnusedRecords();
    frsMigration.checkForLateUpdates();
    //
    //

    account = migrationTestDao.get().getAccountByAccountNumber(accountNumber);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date parsedDate = dateFormat.parse("2001-02-21 10:10:10");
    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

    assertThat(account.client).isEqualTo(12312);
    assertThat(account.number).isEqualTo("19382KZ865-20725-12312-8267359");
    assertThat(account.registeredAt).isEqualTo(timestamp);
  }

  @Test
  public void checkForLateUpdates_transaction_no_account() throws Exception {

    this.prepareTempTables();

    String fileName = "from_frs_2018-02-21-154543-1-30009.json_row";
    String filePath = String.format("%s/check_late_update_transaction/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    frsMigration.checkForValidness();
    frsMigration.validateAndMigrateData();
    frsMigration.disableUnusedRecords();
    frsMigration.checkForLateUpdates();
    //
    //

    String accountNumber = "19382KZ865-20725-32132-8267359";

    ClientAccount account = migrationTestDao.get().getAccountByAccountNumber(accountNumber);

    assertThat(account).isNull();


    Character character = new Character();
    character.name = "Character check update transaction";
    character.description = "Description check update transaction";
    character.energy = 100;

    characterTestDao.get().insertCharacter(character);

    character.id = migrationTestDao.get().getCharmByName(character.name).id;

    Client client = new Client();
    client.id = 32132;
    client.surname = "S";
    client.name = "N";
    client.patronymic = "";
    client.gender = "MALE";
    client.birthDate = new GregorianCalendar(2010, 10, 10).getTime();
    client.charm = character.id;
    client.migration_id = "1-A69-cut-PJ-G6hRzbEf2W";

    migrationTestDao.get().insertClient(client);

    fileName = "from_frs_2018-02-21-154543-1-30010.json_row";
    filePath = String.format("%s/check_late_update_transaction/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    frsMigration.checkForValidness();
    frsMigration.validateAndMigrateData();
    frsMigration.disableUnusedRecords();
    frsMigration.checkForLateUpdates();
    //
    //

    account = migrationTestDao.get().getAccountByAccountNumber(accountNumber);
    ClientAccountTransaction transaction = migrationTestDao.get().getTransaction(-4321.5, "2011-02-21 11:11:11", account.id);
    TransactionType transactionType = migrationTestDao.get().getTransactionTypeById(transaction.type);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date parsedDate = dateFormat.parse("2011-02-21 11:11:11");
    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

    assertThat(transaction.money).isEqualTo(-4321.5);
    assertThat(transaction.finishedAt).isEqualTo(timestamp);
    assertThat(transaction.account).isEqualTo(account.id);
    assertThat(transactionType.name).isEqualTo("Перевод в никуда");
  }


  @Test
  public void validateAndMigrateData() throws Exception {

    this.prepareTempTables();

    String fileName = "from_frs_2018-02-21-154543-1-30009.json_row";
    String filePath = String.format("%s/full/%s", migrationConfig.get().directoryTest(), fileName);

    Character character = new Character();
    character.name = "Character";
    character.description = "Character full";
    character.energy = 100;

    characterTestDao.get().insertCharacter(character);

    character.id = migrationTestDao.get().getCharmByName(character.name).id;

    Client client = new Client();
    client.id = 999;
    client.surname = "S";
    client.name = "N";
    client.patronymic = "";
    client.gender = "MALE";
    client.birthDate = new GregorianCalendar(2010, 10, 10).getTime();
    client.charm = character.id;
    client.migration_id = "7-A69-ff-PJ-G6hRzbEf2W";

    migrationTestDao.get().insertClient(client);

    //
    //
    frsMigration = new FrsMigrationCallbackImpl(filePath);
    frsMigration.parseAndFillData();
    frsMigration.checkForValidness();
    frsMigration.validateAndMigrateData();
    frsMigration.disableUnusedRecords();
    //
    //

    String accountNumber1 = "19382KZ865-20725-77777-8267359";

    ClientAccount account1 = migrationTestDao.get().getAccountByAccountNumber(accountNumber1);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date parsedDate = dateFormat.parse("2001-02-21 15:51:14");
    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

    assertThat(account1.client).isEqualTo(999);
    assertThat(account1.number).isEqualTo("19382KZ865-20725-77777-8267359");
    assertThat(account1.registeredAt).isEqualTo(timestamp);


    String accountNumber2 = "19382KZ865-20725-88888-8267359";

    ClientAccount account2 = migrationTestDao.get().getAccountByAccountNumber(accountNumber2);

    assertThat(account2).isNull();

    ClientAccountTransaction transaction1 = migrationTestDao.get().getTransaction(-4321.5, "2011-02-21T15:51:16", account1.id);
    TransactionType transactionType = migrationTestDao.get().getTransactionTypeById(transaction1.type);

    dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    parsedDate = dateFormat.parse("2011-02-21 15:51:16");
    timestamp = new java.sql.Timestamp(parsedDate.getTime());

    assertThat(transaction1.money).isEqualTo(-4321.5);
    assertThat(transaction1.finishedAt).isEqualTo(timestamp);
    assertThat(transaction1.account).isEqualTo(account1.id);
    assertThat(transactionType.name).isEqualTo("Перевод в оффтоп");

    ClientAccountTransaction transaction2 = migrationTestDao.get().getTransaction(100100.5, "2011-02-21T15:51:16", account1.id);

    assertThat(transaction2).isNull();
  }
}