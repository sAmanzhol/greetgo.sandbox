package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dao_model.Character;
import kz.greetgo.sandbox.register.dao_model.*;
import kz.greetgo.sandbox.register.test.dao.CharacterTestDao;
import kz.greetgo.sandbox.register.test.dao.ClientTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import java.util.*;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest extends ParentTestNg {

  public BeanGetter<ClientRegister> clientRegister;

  public BeanGetter<ClientTestDao> clientTestDao;
  public BeanGetter<CharacterTestDao> characterTestDao;


  // FIXME: 10/8/18 Добавь тесты на count


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

  private ClientAccount insertClientAccountTest(int id, int client, float money, String number) {
    ClientAccount clientAccount = new ClientAccount();
    clientAccount.id = id;
    clientAccount.client = client;
    clientAccount.money = money;
    clientAccount.number = number;

    clientTestDao.get().insertClientAccount(clientAccount);
    return clientAccount;
  }

  private TransactionType insertTransactionTypeTest(int id, String name, String code) {
    TransactionType transactionType = new TransactionType();
    transactionType.id = id;
    transactionType.name = name;
    transactionType.code = code;

    clientTestDao.get().insertTransactionType(transactionType);
    return transactionType;
  }

  private ClientAccountTransaction insertClientAccountTransactionTest(int id, int account, float money, int type) {
    ClientAccountTransaction clientAccountTransaction = new ClientAccountTransaction();
    clientAccountTransaction.id = id;
    clientAccountTransaction.account = account;
    clientAccountTransaction.money = money;
    clientAccountTransaction.type = type;

    clientTestDao.get().insertClientAccountTransaction(clientAccountTransaction);
    return clientAccountTransaction;
  }

  private ClientAddr insertClientAddr(int client, String type, String street, String house, String flat) {
    ClientAddr clientAddr = new ClientAddr();
    clientAddr.client = client;
    clientAddr.type = type;
    clientAddr.street = street;
    clientAddr.house = house;
    clientAddr.flat = flat;

    clientTestDao.get().insertClientAddr(clientAddr);
    return clientAddr;
  }

  private ClientPhone insertClientPhone(int client, String type, String number) {
    ClientPhone clientPhone = new ClientPhone();
    clientPhone.client = client;
    clientPhone.type = type;
    clientPhone.number = number;

    clientTestDao.get().insertClientPhone(clientPhone);
    return clientPhone;
  }


  @Test
  public void list_check_validity() {

    // FIXME: 10/8/18 Должно валидироваться clientRegister.get().list(filter), а не тестовые инсерты

    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    Character charm = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100);

    // FIXME: 10/8/18 Сделай 5 клиента с разными значениями в аккаунте
    Client client = insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charm.id);

    ClientAccount clientAccount1 = insertClientAccountTest(101, client.id, 100, "000");
    ClientAccount clientAccount2 = insertClientAccountTest(102, client.id, 150, "001");

    TransactionType transactionType = insertTransactionTypeTest(101, "00001", "Payment");

    ClientAccountTransaction clientAccountTransaction1 = insertClientAccountTransactionTest(101, clientAccount1.id, 20, transactionType.id);
    ClientAccountTransaction clientAccountTransaction2 = insertClientAccountTransactionTest(102, clientAccount2.id, -100, transactionType.id);

    assertThat(charm.id).isNotNull().isEqualTo(client.charm);
    assertThat(charm.name).isNotNull().isEqualTo("Самовлюблённый");
    assertThat(charm.description).isNotNull().isEqualTo("Самовлюблённый Самовлюблённый");
    assertThat(charm.energy).isNotNull().isEqualTo(100);

    assertThat(client.id).isNotNull().isEqualTo(clientAccount1.client).isEqualTo(clientAccount2.client);
    assertThat(client.surname).isNotNull().isEqualTo("Колобова");
    assertThat(client.name).isNotNull().isEqualTo("Розалия");
    assertThat(client.patronymic).isNotNull().isEqualTo("Наумовна");
    assertThat(client.gender).isNotNull().isEqualTo("FEMALE");
    assertThat(client.birth_date).isNotNull().isInThePast().isEqualTo("1977-5-25");

    assertThat(clientAccount1.id).isNotNull().isEqualTo(clientAccountTransaction1.account);
    assertThat(clientAccount1.money).isNotNull().isEqualTo(100);
    assertThat(clientAccount1.number).isNotNull().isEqualTo("000");

    assertThat(clientAccount2.id).isNotNull().isEqualTo(clientAccountTransaction2.account);
    assertThat(clientAccount2.money).isNotNull().isEqualTo(150);
    assertThat(clientAccount2.number).isNotNull().isEqualTo("001");

    assertThat(clientAccountTransaction1.id).isNotNull();
    assertThat(clientAccountTransaction1.money).isNotNull().isEqualTo(20);
    assertThat(clientAccountTransaction2.id).isNotNull();
    assertThat(clientAccountTransaction2.money).isNotNull().isEqualTo(-100);
  }


  @Test
  public void render_list_sortColumn_id_sortDirection_asc() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "ASC", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(101);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(list.get(2).id).isEqualTo(103);
  }

  @Test
  public void render_list_sortColumn_id_sortDirection_desc() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "DESC", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(103);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(list.get(2).id).isEqualTo(101);
  }

  @Test
  public void render_list_sortColumn_fio_sortDirection_asc() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(121, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(122, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(123, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("fio", "ASC", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(121);
    assertThat(list.get(1).id).isEqualTo(123);
    assertThat(list.get(2).id).isEqualTo(122);
  }

  @Test
  public void render_list_sortColumn_fio_sortDirection_desc() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(111, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(112, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(113, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("fio", "DESC", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(112);
    assertThat(list.get(1).id).isEqualTo(113);
    assertThat(list.get(2).id).isEqualTo(111);
  }

  @Test
  public void render_list_sortColumn_age_sortDirection_asc() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("age", "ASC", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(102);
    assertThat(list.get(1).id).isEqualTo(101);
    assertThat(list.get(2).id).isEqualTo(103);
  }

  @Test
  public void render_list_sortColumn_age_sortDirection_desc() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("age", "DESC", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(103);
    assertThat(list.get(1).id).isEqualTo(101);
    assertThat(list.get(2).id).isEqualTo(102);
  }

  @Test
  public void render_list_sortColumn_balance_sortDirection_asc() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccountTest(101, 101, 100, "000");
    insertClientAccountTest(102, 101, 150, "001");

    insertClientAccountTest(103, 102, 50, "002");
    insertClientAccountTest(104, 102, 400, "003");

    insertClientAccountTest(105, 103, 1000, "004");
    insertClientAccountTest(106, 103, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balance", "ASC", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(101);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(list.get(2).id).isEqualTo(103);
  }

  @Test
  public void render_list_sortColumn_balance_sortDirection_desc() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccountTest(101, 101, 100, "000");
    insertClientAccountTest(102, 101, 150, "001");

    insertClientAccountTest(103, 102, 50, "002");
    insertClientAccountTest(104, 102, 400, "003");

    insertClientAccountTest(105, 103, 1000, "004");
    insertClientAccountTest(106, 103, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balance", "DESC", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(103);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(list.get(2).id).isEqualTo(101);
  }

  @Test
  public void render_list_sortColumn_balanceMax_sortDirection_asc() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccountTest(101, 101, 100, "000");
    insertClientAccountTest(102, 101, 150, "001");

    insertClientAccountTest(103, 102, 50, "002");
    insertClientAccountTest(104, 102, 400, "003");

    insertClientAccountTest(105, 103, 1000, "004");
    insertClientAccountTest(106, 103, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balanceMax", "ASC", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(101);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(list.get(2).id).isEqualTo(103);
  }

  @Test
  public void render_list_sortColumn_balanceMax_sortDirection_desc() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccountTest(101, 101, 100, "000");
    insertClientAccountTest(102, 101, 150, "001");

    insertClientAccountTest(103, 102, 50, "002");
    insertClientAccountTest(104, 102, 400, "003");

    insertClientAccountTest(105, 103, 1000, "004");
    insertClientAccountTest(106, 103, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balanceMax", "DESC", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(103);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(list.get(2).id).isEqualTo(101);
  }

  @Test
  public void render_list_sortColumn_balanceMin_sortDirection_asc() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccountTest(101, 101, 100, "000");
    insertClientAccountTest(102, 101, 150, "001");

    insertClientAccountTest(103, 102, 50, "002");
    insertClientAccountTest(104, 102, 400, "003");

    insertClientAccountTest(105, 103, 1000, "004");
    insertClientAccountTest(106, 103, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balanceMin", "ASC", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(102);
    assertThat(list.get(1).id).isEqualTo(101);
    assertThat(list.get(2).id).isEqualTo(103);
  }

  @Test
  public void render_list_sortColumn_balanceMin_sortDirection_desc() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccountTest(101, 101, 100, "000");
    insertClientAccountTest(102, 101, 150, "001");

    insertClientAccountTest(103, 102, 50, "002");
    insertClientAccountTest(104, 102, 400, "003");

    insertClientAccountTest(105, 103, 1000, "004");
    insertClientAccountTest(106, 103, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balanceMin", "DESC", "", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //


    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(103);
    assertThat(list.get(1).id).isEqualTo(101);
    assertThat(list.get(2).id).isEqualTo(102);
  }

  @Test
  public void render_list_with_filter_fio_surname() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1010, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(1020, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(1030, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "ASC", "К", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(2);

    assertThat(list.get(0).id).isEqualTo(1010);
    assertThat(list.get(1).id).isEqualTo(1030);
  }

  @Test
  public void render_list_with_filter_fio_name() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "ASC", "ли", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(2);

    assertThat(list.get(0).id).isEqualTo(101);
    assertThat(list.get(1).id).isEqualTo(102);
  }

  @Test
  public void render_list_with_filter_fio_patronymic() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "ASC", "Уле", 1, 10);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(1);

    assertThat(list.get(0).id).isEqualTo(103);
  }


  @Test
  public void list_with_pagination_top_items() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);
    insertClient(104, "Киселёв", "Юлиан", "Романович", "MALE", new GregorianCalendar(2000, 4, 16).getTime(), charmId);
    insertClient(105, "Исаева", "Ирина", "Сергеевна", "FEMALE", new GregorianCalendar(1974, 5, 11).getTime(), charmId);
    insertClient(106, "Большаков", "Мечеслав", "Куприянович", "MALE", new GregorianCalendar(1983, 8, 21).getTime(), charmId);
    insertClient(107, "Корнилов", "Захар", "Федосеевич", "MALE", new GregorianCalendar(1984, 6, 11).getTime(), charmId);
    insertClient(108, "Лихачёв", "Исак", "Кириллович", "MALE", new GregorianCalendar(1947, 8, 13).getTime(), charmId);
    insertClient(109, "Фёдорова", "Эмбер", "Руслановна", "FEMALE", new GregorianCalendar(1977, 2, 8).getTime(), charmId);
    insertClient(110, "Баранова", "Габриэлла", "Романовна", "FEMALE", new GregorianCalendar(2002, 1, 28).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "ASC", "", 1, 3);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(101);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(list.get(2).id).isEqualTo(103);
  }

  @Test
  public void list_with_pagination_middle_items() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);
    insertClient(104, "Киселёв", "Юлиан", "Романович", "MALE", new GregorianCalendar(2000, 4, 16).getTime(), charmId);
    insertClient(105, "Исаева", "Ирина", "Сергеевна", "FEMALE", new GregorianCalendar(1974, 5, 11).getTime(), charmId);
    insertClient(106, "Большаков", "Мечеслав", "Куприянович", "MALE", new GregorianCalendar(1983, 8, 21).getTime(), charmId);
    insertClient(107, "Корнилов", "Захар", "Федосеевич", "MALE", new GregorianCalendar(1984, 6, 11).getTime(), charmId);
    insertClient(108, "Лихачёв", "Исак", "Кириллович", "MALE", new GregorianCalendar(1947, 8, 13).getTime(), charmId);
    insertClient(109, "Фёдорова", "Эмбер", "Руслановна", "FEMALE", new GregorianCalendar(1977, 2, 8).getTime(), charmId);
    insertClient(110, "Баранова", "Габриэлла", "Романовна", "FEMALE", new GregorianCalendar(2002, 1, 28).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "ASC", "", 2, 3);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(104);
    assertThat(list.get(1).id).isEqualTo(105);
    assertThat(list.get(2).id).isEqualTo(106);
  }

  @Test
  public void list_with_pagination_bottom_items() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);
    insertClient(104, "Киселёв", "Юлиан", "Романович", "MALE", new GregorianCalendar(2000, 4, 16).getTime(), charmId);
    insertClient(105, "Исаева", "Ирина", "Сергеевна", "FEMALE", new GregorianCalendar(1974, 5, 11).getTime(), charmId);
    insertClient(106, "Большаков", "Мечеслав", "Куприянович", "MALE", new GregorianCalendar(1983, 8, 21).getTime(), charmId);
    insertClient(107, "Корнилов", "Захар", "Федосеевич", "MALE", new GregorianCalendar(1984, 6, 11).getTime(), charmId);
    insertClient(108, "Лихачёв", "Исак", "Кириллович", "MALE", new GregorianCalendar(1947, 8, 13).getTime(), charmId);
    insertClient(109, "Фёдорова", "Эмбер", "Руслановна", "FEMALE", new GregorianCalendar(1977, 2, 8).getTime(), charmId);
    insertClient(110, "Баранова", "Габриэлла", "Романовна", "FEMALE", new GregorianCalendar(2002, 1, 28).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "ASC", "", 4, 3);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(1);

    assertThat(list.get(0).id).isEqualTo(110);
  }

  @Test
  public void list_with_pagination_no_items() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);
    insertClient(104, "Киселёв", "Юлиан", "Романович", "MALE", new GregorianCalendar(2000, 4, 16).getTime(), charmId);
    insertClient(105, "Исаева", "Ирина", "Сергеевна", "FEMALE", new GregorianCalendar(1974, 5, 11).getTime(), charmId);
    insertClient(106, "Большаков", "Мечеслав", "Куприянович", "MALE", new GregorianCalendar(1983, 8, 21).getTime(), charmId);
    insertClient(107, "Корнилов", "Захар", "Федосеевич", "MALE", new GregorianCalendar(1984, 6, 11).getTime(), charmId);
    insertClient(108, "Лихачёв", "Исак", "Кириллович", "MALE", new GregorianCalendar(1947, 8, 13).getTime(), charmId);
    insertClient(109, "Фёдорова", "Эмбер", "Руслановна", "FEMALE", new GregorianCalendar(1977, 2, 8).getTime(), charmId);
    insertClient(110, "Баранова", "Габриэлла", "Романовна", "FEMALE", new GregorianCalendar(2002, 1, 28).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "ASC", "", 5, 3);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);
    //
    //

    assertThat(list).hasSize(0);
  }


  @Test
  public void save_create() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    Character charm = insertCharacterTest(RND.plusInt(999999), RND.str(10), RND.str(20), RND.plusInt(100));

    // FIXME: 10/8/18 если все пишешь в отдельную строчку, то лучше сделай присвоение каждого филда через =
    ClientToSave clientToSave = new ClientToSave(null, RND.str(10), RND.str(6), "", new GregorianCalendar(1977, 4, 25).getTime(), "MALE", charm.id, RND.str(15), RND.str(3), RND.str(1), "", "", "", new ArrayList<>(Collections.singletonList(new PhoneDisplay(0, "HOME", RND.str(11)))));

    //
    //
    ClientRecord clientRecord = clientRegister.get().save(clientToSave);
    //
    //


    // FIXME: 10/8/18 А как же остальные колонки в базе
    assertThat(clientRecord.id).isNotNull();
    assertThat(clientRecord.fio).isEqualTo(clientToSave.surname + " " + clientToSave.name + " " + clientToSave.patronymic);
    assertThat(clientRecord.character).isEqualTo(charm.name);
    assertThat(clientRecord.age).isEqualTo(41);
    assertThat(clientRecord.balance).isNotNull().isEqualTo(0);
    assertThat(clientRecord.balanceMax).isEqualTo(0);
    assertThat(clientRecord.balanceMin).isEqualTo(0);
  }

  @Test
  public void save_update() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    Character charm = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100);
    Character newCharm = insertCharacterTest(102, "Характерный", "Характерный Характерный", 100);

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charm.id);

    ClientToSave clientToSave = new ClientToSave(101, "Фамилия", "Имя", "Розалия", new GregorianCalendar(1976, 4, 25).getTime(), "FEMALE", newCharm.id, "Ломоносов Рег", "1", "10", "Lomonosov Фак", "2", "20", new ArrayList<>(Arrays.asList(new PhoneDisplay(0, "HOME", "77077070077"), new PhoneDisplay(0, "MOBILE", "77000000077"))));

    //
    //
    ClientRecord clientRecord = clientRegister.get().save(clientToSave);
    //
    //

    // FIXME: 10/8/18 А как же остальные колонки в базе
    assertThat(clientRecord.id).isNotNull().isEqualTo(101);
    assertThat(clientRecord.fio).isNotNull().isEqualTo(clientToSave.surname + " " + clientToSave.name + " " + clientToSave.patronymic);
    assertThat(clientRecord.character).isNotNull().isEqualTo(newCharm.name);
    assertThat(clientRecord.age).isNotNull().isEqualTo(42);
    assertThat(clientRecord.balance).isNotNull();
    assertThat(clientRecord.balanceMax).isNotNull();
    assertThat(clientRecord.balanceMin).isNotNull();
  }

  @Test
  public void details() {
    Character charm = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100);
    Client client = insertClient(201, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charm.id);

    ClientAddr clientAddrReg = insertClientAddr(201, "REG", "Lomonosov", "1", "2");
    ClientAddr clientAddrFact = insertClientAddr(201, "FACT", "Lomonosova Fact", "10", "20");

    ClientPhone clientPhoneHome = insertClientPhone(201, "HOME", "87077070077");
    ClientPhone clientPhoneMobile = insertClientPhone(201, "MOBILE", "12345678899");

    //
    //
    ClientDetails clientDisplay = clientRegister.get().details(client.id);
    //
    //

    assertThat(clientDisplay.id).isNotNull().isEqualTo(201);
    assertThat(clientDisplay.surname).isNotNull().isEqualTo(client.surname);
    assertThat(clientDisplay.name).isNotNull().isEqualTo(client.name);
    assertThat(clientDisplay.patronymic).isNotNull().isEqualTo(client.patronymic);
    assertThat(clientDisplay.gender).isNotNull().isEqualTo(client.gender);
    assertThat(clientDisplay.birthDate).isNotNull().isInThePast().isEqualTo(client.birth_date);
    assertThat(clientDisplay.characterId).isNotNull().isEqualTo(charm.id);
    assertThat(clientDisplay.streetRegistration).isNotNull().isEqualTo(clientAddrReg.street);
    assertThat(clientDisplay.houseRegistration).isNotNull().isEqualTo(clientAddrReg.house);
    assertThat(clientDisplay.apartmentRegistration).isNotNull().isEqualTo(clientAddrReg.flat);
    assertThat(clientDisplay.streetResidence).isNotNull().isEqualTo(clientAddrFact.street);
    assertThat(clientDisplay.houseResidence).isNotNull().isEqualTo(clientAddrFact.house);
    assertThat(clientDisplay.apartmentResidence).isNotNull().isEqualTo(clientAddrFact.flat);

    assertThat(clientDisplay.numbers).hasSize(2);

    assertThat(clientDisplay.numbers.get(0).type).isEqualTo(clientPhoneHome.type);
    assertThat(clientDisplay.numbers.get(0).number).isEqualTo(clientPhoneHome.number);

    assertThat(clientDisplay.numbers.get(1).type).isEqualTo(clientPhoneMobile.type);
    assertThat(clientDisplay.numbers.get(1).number).isEqualTo(clientPhoneMobile.number);
  }

  @Test
  public void delete() {
    Character charm = insertCharacterTest(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100);
    Client client = insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charm.id);

    //
    //
    clientRegister.get().delete(client.id);
    //
    //

    int actual = clientTestDao.get().getClientActual(client.id);
    assertThat(actual).isEqualTo(0);
  }
}
