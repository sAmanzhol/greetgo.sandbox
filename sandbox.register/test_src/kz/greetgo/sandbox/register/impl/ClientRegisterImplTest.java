package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.ClientToFilter;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dao_model.Character;
import kz.greetgo.sandbox.register.dao_model.*;
import kz.greetgo.sandbox.register.test.dao.CharacterTestDao;
import kz.greetgo.sandbox.register.test.dao.ClientTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest extends ParentTestNg {

  public BeanGetter<ClientRegister> clientRegister;

  public BeanGetter<ClientTestDao> clientTestDao;
  public BeanGetter<CharacterTestDao> characterTestDao;

  private Client insertClient(int id, String surname, String name, String patronymic, String gender, Date birth_date, int charm) {
    Client client = new Client();
    client.id = id;
    client.surname = surname;
    client.name = name;
    client.patronymic = patronymic;
    client.gender = gender;
    client.birth_date = birth_date;
    client.charm = charm;

    clientTestDao.get().insertClient(client);
    return client;
  }

  private Character insertCharacterTest(int id, String name, String description, float energy) {
    Character character = new Character();
    character.id = id;
    character.name = name;
    character.description = description;
    character.energy = energy;

    characterTestDao.get().insertCharacter(character);
    return character;
  }

  private Client_account insertClientAccountTest(int id, int client, float money, String number) {
    Client_account client_account = new Client_account();
    client_account.id = id;
    client_account.client = client;
    client_account.money = money;
    client_account.number = number;

    clientTestDao.get().insertClientAccount(client_account);
    return client_account;
  }

  private Transaction_type insertTransactionTypeTest(int id, String name, String code) {
    Transaction_type transaction_type = new Transaction_type();
    transaction_type.id = id;
    transaction_type.name = name;
    transaction_type.code = code;

    clientTestDao.get().insertTransactionType(transaction_type);
    return transaction_type;
  }

  private Client_account_transaction insertClientAccountTransactionTest(int id, int account, float money, int type) {
    Client_account_transaction client_account_transaction = new Client_account_transaction();
    client_account_transaction.id = id;
    client_account_transaction.account = account;
    client_account_transaction.money = money;
    client_account_transaction.type = type;

    clientTestDao.get().insertClientAccountTransaction(client_account_transaction);
    return client_account_transaction;
  }

  @Test
  public void list_check_validity() {
    clientTestDao.get().removeAll();

    Character charm = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100);

    Client client = insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charm.id);

    Client_account client_account1 = insertClientAccountTest(11, client.id, 100, "000");
    Client_account client_account2 = insertClientAccountTest(12, client.id, 150, "001");

    Transaction_type transaction_type = insertTransactionTypeTest(1, "00001", "Payment");

    Client_account_transaction client_account_transaction1 = insertClientAccountTransactionTest(1, client_account1.id, 20, transaction_type.id);
    Client_account_transaction client_account_transaction2 = insertClientAccountTransactionTest(2, client_account2.id, -100, transaction_type.id);

    assertThat(charm.id).isNotNull().isEqualTo(client.charm);
    assertThat(charm.name).isNotNull().isEqualTo("Самовлюблённый");
    assertThat(charm.description).isNotNull().isEqualTo("Самовлюблённый Самовлюблённый");
    assertThat(charm.energy).isNotNull().isEqualTo(100);

    assertThat(client.id).isNotNull().isEqualTo(client_account1.client).isEqualTo(client_account2.client);
    assertThat(client.surname).isNotNull().isEqualTo("Колобова");
    assertThat(client.name).isNotNull().isEqualTo("Розалия");
    assertThat(client.patronymic).isNotNull().isEqualTo("Наумовна");
    assertThat(client.gender).isNotNull().isEqualTo("FEMALE");
    assertThat(client.birth_date).isNotNull().isInThePast().isEqualTo("1977-5-25");

    assertThat(client_account1.id).isNotNull().isEqualTo(client_account_transaction1.account);
    assertThat(client_account1.money).isNotNull().isEqualTo(100);
    assertThat(client_account1.number).isNotNull().isEqualTo("000");

    assertThat(client_account2.id).isNotNull().isEqualTo(client_account_transaction2.account);
    assertThat(client_account2.money).isNotNull().isEqualTo(150);
    assertThat(client_account2.number).isNotNull().isEqualTo("001");

    assertThat(client_account_transaction1.id).isNotNull();
    assertThat(client_account_transaction1.money).isNotNull().isEqualTo(20);
    assertThat(client_account_transaction2.id).isNotNull();
    assertThat(client_account_transaction2.money).isNotNull().isEqualTo(-100);
  }

  @Test
  public void list_sortColumn_id_sortDirection_asc() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "asc", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(1);
    assertThat(list.get(1).id).isEqualTo(2);
    assertThat(list.get(2).id).isEqualTo(3);
  }

  @Test
  public void list_sortColumn_id_sortDirection_desc() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "desc", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(3);
    assertThat(list.get(1).id).isEqualTo(2);
    assertThat(list.get(2).id).isEqualTo(1);
  }

  @Test
  public void list_sortColumn_fio_sortDirection_asc() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("fio", "asc", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(1);
    assertThat(list.get(1).id).isEqualTo(3);
    assertThat(list.get(2).id).isEqualTo(2);
  }

  @Test
  public void list_sortColumn_fio_sortDirection_desc() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("fio", "desc", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(2);
    assertThat(list.get(1).id).isEqualTo(3);
    assertThat(list.get(2).id).isEqualTo(1);
  }

  @Test
  public void list_sortColumn_age_sortDirection_asc() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("age", "asc", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(2);
    assertThat(list.get(1).id).isEqualTo(1);
    assertThat(list.get(2).id).isEqualTo(3);
  }

  @Test
  public void list_sortColumn_age_sortDirection_desc() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("age", "desc", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(3);
    assertThat(list.get(1).id).isEqualTo(1);
    assertThat(list.get(2).id).isEqualTo(2);
  }

  @Test
  public void list_sortColumn_balance_sortDirection_asc() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccountTest(11, 1, 100, "000");
    insertClientAccountTest(12, 1, 150, "001");

    insertClientAccountTest(21, 2, 50, "002");
    insertClientAccountTest(22, 2, 400, "003");

    insertClientAccountTest(31, 3, 1000, "004");
    insertClientAccountTest(32, 3, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balance", "asc", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(1);
    assertThat(list.get(1).id).isEqualTo(2);
    assertThat(list.get(2).id).isEqualTo(3);
  }

  @Test
  public void list_sortColumn_balance_sortDirection_desc() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccountTest(11, 1, 100, "000");
    insertClientAccountTest(12, 1, 150, "001");

    insertClientAccountTest(21, 2, 50, "002");
    insertClientAccountTest(22, 2, 400, "003");

    insertClientAccountTest(31, 3, 1000, "004");
    insertClientAccountTest(32, 3, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balance", "desc", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(3);
    assertThat(list.get(1).id).isEqualTo(2);
    assertThat(list.get(2).id).isEqualTo(1);
  }

  @Test
  public void list_sortColumn_balanceMax_sortDirection_asc() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccountTest(11, 1, 100, "000");
    insertClientAccountTest(12, 1, 150, "001");

    insertClientAccountTest(21, 2, 50, "002");
    insertClientAccountTest(22, 2, 400, "003");

    insertClientAccountTest(31, 3, 1000, "004");
    insertClientAccountTest(32, 3, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balanceMax", "asc", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(1);
    assertThat(list.get(1).id).isEqualTo(2);
    assertThat(list.get(2).id).isEqualTo(3);
  }

  @Test
  public void list_sortColumn_balanceMax_sortDirection_desc() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccountTest(11, 1, 100, "000");
    insertClientAccountTest(12, 1, 150, "001");

    insertClientAccountTest(21, 2, 50, "002");
    insertClientAccountTest(22, 2, 400, "003");

    insertClientAccountTest(31, 3, 1000, "004");
    insertClientAccountTest(32, 3, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balanceMax", "desc", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(3);
    assertThat(list.get(1).id).isEqualTo(2);
    assertThat(list.get(2).id).isEqualTo(1);
  }

  @Test
  public void list_sortColumn_balanceMin_sortDirection_asc() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccountTest(11, 1, 100, "000");
    insertClientAccountTest(12, 1, 150, "001");

    insertClientAccountTest(21, 2, 50, "002");
    insertClientAccountTest(22, 2, 400, "003");

    insertClientAccountTest(31, 3, 1000, "004");
    insertClientAccountTest(32, 3, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balanceMin", "asc", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(2);
    assertThat(list.get(1).id).isEqualTo(1);
    assertThat(list.get(2).id).isEqualTo(3);
  }

  @Test
  public void list_sortColumn_balanceMin_sortDirection_desc() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccountTest(11, 1, 100, "000");
    insertClientAccountTest(12, 1, 150, "001");

    insertClientAccountTest(21, 2, 50, "002");
    insertClientAccountTest(22, 2, 400, "003");

    insertClientAccountTest(31, 3, 1000, "004");
    insertClientAccountTest(32, 3, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balanceMin", "desc", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(3);
    assertThat(list.get(1).id).isEqualTo(1);
    assertThat(list.get(2).id).isEqualTo(2);
  }

  @Test
  public void list_with_filter_fio_surname() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "asc", "К", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(2);

    assertThat(list.get(0).id).isEqualTo(1);
    assertThat(list.get(1).id).isEqualTo(3);
  }

  @Test
  public void list_with_filter_fio_name() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "asc", "ли", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(2);

    assertThat(list.get(0).id).isEqualTo(2);
    assertThat(list.get(1).id).isEqualTo(1);
  }

  @Test
  public void list_with_filter_fio_patronymic() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "asc", "Уле", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(1);

    assertThat(list.get(0).id).isEqualTo(3);
  }

  @Test
  public void list_with_pagination_top_items() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);
    insertClient(4, "Киселёв", "Юлиан", "Романович", "MALE", new GregorianCalendar(2000, 4, 16).getTime(), charmId);
    insertClient(5, "Исаева", "Ирина", "Сергеевна", "FEMALE", new GregorianCalendar(1974, 5, 11).getTime(), charmId);
    insertClient(6, "Большаков", "Мечеслав", "Куприянович", "MALE", new GregorianCalendar(1983, 8, 21).getTime(), charmId);
    insertClient(7, "Корнилов", "Захар", "Федосеевич", "MALE", new GregorianCalendar(1984, 6, 11).getTime(), charmId);
    insertClient(8, "Лихачёв", "Исак", "Кириллович", "MALE", new GregorianCalendar(1947, 8, 13).getTime(), charmId);
    insertClient(9, "Фёдорова", "Эмбер", "Руслановна", "FEMALE", new GregorianCalendar(1977, 2, 8).getTime(), charmId);
    insertClient(10, "Баранова", "Габриэлла", "Романовна", "FEMALE", new GregorianCalendar(2002, 1, 28).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "asc", "", 1, 3);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(1);
    assertThat(list.get(1).id).isEqualTo(2);
    assertThat(list.get(2).id).isEqualTo(3);
  }

  @Test
  public void list_with_pagination_middle_items() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);
    insertClient(4, "Киселёв", "Юлиан", "Романович", "MALE", new GregorianCalendar(2000, 4, 16).getTime(), charmId);
    insertClient(5, "Исаева", "Ирина", "Сергеевна", "FEMALE", new GregorianCalendar(1974, 5, 11).getTime(), charmId);
    insertClient(6, "Большаков", "Мечеслав", "Куприянович", "MALE", new GregorianCalendar(1983, 8, 21).getTime(), charmId);
    insertClient(7, "Корнилов", "Захар", "Федосеевич", "MALE", new GregorianCalendar(1984, 6, 11).getTime(), charmId);
    insertClient(8, "Лихачёв", "Исак", "Кириллович", "MALE", new GregorianCalendar(1947, 8, 13).getTime(), charmId);
    insertClient(9, "Фёдорова", "Эмбер", "Руслановна", "FEMALE", new GregorianCalendar(1977, 2, 8).getTime(), charmId);
    insertClient(10, "Баранова", "Габриэлла", "Романовна", "FEMALE", new GregorianCalendar(2002, 1, 28).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "asc", "", 2, 3);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(4);
    assertThat(list.get(1).id).isEqualTo(5);
    assertThat(list.get(2).id).isEqualTo(6);
  }

  @Test
  public void list_with_pagination_bottom_items() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);
    insertClient(4, "Киселёв", "Юлиан", "Романович", "MALE", new GregorianCalendar(2000, 4, 16).getTime(), charmId);
    insertClient(5, "Исаева", "Ирина", "Сергеевна", "FEMALE", new GregorianCalendar(1974, 5, 11).getTime(), charmId);
    insertClient(6, "Большаков", "Мечеслав", "Куприянович", "MALE", new GregorianCalendar(1983, 8, 21).getTime(), charmId);
    insertClient(7, "Корнилов", "Захар", "Федосеевич", "MALE", new GregorianCalendar(1984, 6, 11).getTime(), charmId);
    insertClient(8, "Лихачёв", "Исак", "Кириллович", "MALE", new GregorianCalendar(1947, 8, 13).getTime(), charmId);
    insertClient(9, "Фёдорова", "Эмбер", "Руслановна", "FEMALE", new GregorianCalendar(1977, 2, 8).getTime(), charmId);
    insertClient(10, "Баранова", "Габриэлла", "Романовна", "FEMALE", new GregorianCalendar(2002, 1, 28).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "asc", "", 4, 3);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(1);

    assertThat(list.get(0).id).isEqualTo(10);
  }

  @Test
  public void list_with_pagination_no_items() {
    clientTestDao.get().removeAll();

    int charmId = insertCharacterTest(1, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(2, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(3, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);
    insertClient(4, "Киселёв", "Юлиан", "Романович", "MALE", new GregorianCalendar(2000, 4, 16).getTime(), charmId);
    insertClient(5, "Исаева", "Ирина", "Сергеевна", "FEMALE", new GregorianCalendar(1974, 5, 11).getTime(), charmId);
    insertClient(6, "Большаков", "Мечеслав", "Куприянович", "MALE", new GregorianCalendar(1983, 8, 21).getTime(), charmId);
    insertClient(7, "Корнилов", "Захар", "Федосеевич", "MALE", new GregorianCalendar(1984, 6, 11).getTime(), charmId);
    insertClient(8, "Лихачёв", "Исак", "Кириллович", "MALE", new GregorianCalendar(1947, 8, 13).getTime(), charmId);
    insertClient(9, "Фёдорова", "Эмбер", "Руслановна", "FEMALE", new GregorianCalendar(1977, 2, 8).getTime(), charmId);
    insertClient(10, "Баранова", "Габриэлла", "Романовна", "FEMALE", new GregorianCalendar(2002, 1, 28).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "asc", "", 5, 3);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(0);
  }
}
