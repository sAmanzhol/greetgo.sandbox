package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dao_model.Character;
import kz.greetgo.sandbox.register.dao_model.*;
import kz.greetgo.sandbox.register.report.ClientReportViewTest;
import kz.greetgo.sandbox.register.test.dao.CharacterTestDao;
import kz.greetgo.sandbox.register.test.dao.ClientTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import java.util.*;

import static org.fest.assertions.api.Assertions.assertThat;

@SuppressWarnings("WeakerAccess")
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

  private Character insertCharacter(int id, String name, String description, float energy) {
    Character character = new Character();
    character.id = id;
    character.name = name;
    character.description = description;
    character.energy = energy;

    characterTestDao.get().insertCharacter(character);
    return character;
  }

  private ClientAccount insertClientAccount(int id, int client, float money, String number) {
    ClientAccount clientAccount = new ClientAccount();
    clientAccount.id = id;
    clientAccount.client = client;
    clientAccount.money = money;
    clientAccount.number = number;

    clientTestDao.get().insertClientAccount(clientAccount);
    return clientAccount;
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

  private ClientPhone insertClientPhone(int id, int client, String type, String number) {
    ClientPhone clientPhone = new ClientPhone();
    clientPhone.id = id;
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

    Character charm1 = insertCharacter(301, "Самовлюблённый", "Самовлюблённый Описание", 100);
    Character charm2 = insertCharacter(302, "Замкнутый", "Замкнутый Описание", 90);

    // FIXME: 10/8/18 Сделай 5 клиента с разными значениями в аккаунте
    Client client1 = insertClient(301, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charm1.id);
    Client client2 = insertClient(302, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charm1.id);
    Client client3 = insertClient(303, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charm2.id);
    Client client4 = insertClient(304, "Киселёв", "Юлиан", "Романович", "MALE", new GregorianCalendar(2000, 4, 16).getTime(), charm2.id);
    Client client5 = insertClient(305, "Исаева", "Ирина", "Сергеевна", "FEMALE", new GregorianCalendar(1974, 5, 11).getTime(), charm2.id);

    ClientAccount clientAccount1 = insertClientAccount(301, client1.id, 3324, "300111111");

    ClientAccount clientAccount2 = insertClientAccount(302, client2.id, 23425, "300222111");
    ClientAccount clientAccount3 = insertClientAccount(303, client2.id, -342423, "300222222");

    ClientAccount clientAccount4 = insertClientAccount(304, client3.id, 34, "300333111");

    ClientAccount clientAccount5 = insertClientAccount(305, client4.id, 23434, "300444111");

    insertClientAccount(306, client5.id, 453, "300555111");
    insertClientAccount(307, client5.id, 4323, "300555222");
    insertClientAccount(308, client5.id, -100, "300555333");


    ClientToFilter filter = new ClientToFilter("id", "ASC", "", 1, 4);

    //
    //
    List<ClientRecord> clientRecords = clientRegister.get().list(filter);
    //
    //

    assertThat(clientRecords).hasSize(4);

    assertThat(clientRecords.get(0).id).isEqualTo(client1.id);
    assertThat(clientRecords.get(0).fio).isEqualTo(client1.surname + " " + client1.name + " " + client1.patronymic);
    assertThat(clientRecords.get(0).character).isEqualTo(charm1.name);
    assertThat(clientRecords.get(0).age).isEqualTo(41);
    assertThat(clientRecords.get(0).balance).isEqualTo(clientAccount1.money);
    assertThat(clientRecords.get(0).balanceMax).isEqualTo(clientAccount1.money);
    assertThat(clientRecords.get(0).balanceMin).isEqualTo(clientAccount1.money);

    assertThat(clientRecords.get(1).id).isEqualTo(client2.id);
    assertThat(clientRecords.get(1).fio).isEqualTo(client2.surname + " " + client2.name + " " + client2.patronymic);
    assertThat(clientRecords.get(1).character).isEqualTo(charm1.name);
    assertThat(clientRecords.get(1).age).isEqualTo(19);
    assertThat(clientRecords.get(1).balance).isEqualTo(clientAccount2.money + clientAccount3.money);
    assertThat(clientRecords.get(1).balanceMax).isEqualTo(clientAccount2.money);
    assertThat(clientRecords.get(1).balanceMin).isEqualTo(clientAccount3.money);

    assertThat(clientRecords.get(2).id).isEqualTo(client3.id);
    assertThat(clientRecords.get(2).fio).isEqualTo(client3.surname + " " + client3.name + " " + client3.patronymic);
    assertThat(clientRecords.get(2).character).isEqualTo(charm2.name);
    assertThat(clientRecords.get(2).age).isEqualTo(53);
    assertThat(clientRecords.get(2).balance).isEqualTo(clientAccount4.money);
    assertThat(clientRecords.get(2).balanceMax).isEqualTo(clientAccount4.money);
    assertThat(clientRecords.get(2).balanceMin).isEqualTo(clientAccount4.money);

    assertThat(clientRecords.get(3).id).isEqualTo(client4.id);
    assertThat(clientRecords.get(3).fio).isEqualTo(client4.surname + " " + client4.name + " " + client4.patronymic);
    assertThat(clientRecords.get(3).character).isEqualTo(charm2.name);
    assertThat(clientRecords.get(3).age).isEqualTo(18);
    assertThat(clientRecords.get(3).balance).isEqualTo(clientAccount5.money);
    assertThat(clientRecords.get(3).balanceMax).isEqualTo(clientAccount5.money);
    assertThat(clientRecords.get(3).balanceMin).isEqualTo(clientAccount5.money);
  }

  @Test
  public void render_check_validity() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    Character charm1 = insertCharacter(301, "Самовлюблённый", "Самовлюблённый Описание", 100);
    Character charm2 = insertCharacter(302, "Замкнутый", "Замкнутый Описание", 90);

    Client client1 = insertClient(301, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charm1.id);
    Client client2 = insertClient(302, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charm1.id);
    Client client3 = insertClient(303, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charm2.id);
    Client client4 = insertClient(304, "Киселёв", "Юлиан", "Романович", "MALE", new GregorianCalendar(2000, 4, 16).getTime(), charm2.id);
    Client client5 = insertClient(305, "Исаева", "Ирина", "Сергеевна", "FEMALE", new GregorianCalendar(1974, 5, 11).getTime(), charm2.id);

    ClientAccount clientAccount1 = insertClientAccount(301, client1.id, 3324, "300111111");

    ClientAccount clientAccount2 = insertClientAccount(302, client2.id, 23425, "300222111");
    ClientAccount clientAccount3 = insertClientAccount(303, client2.id, -342423, "300222222");

    ClientAccount clientAccount4 = insertClientAccount(304, client3.id, 34, "300333111");

    ClientAccount clientAccount5 = insertClientAccount(305, client4.id, 23434, "300444111");

    ClientAccount clientAccount6 = insertClientAccount(306, client5.id, 453, "300555111");
    ClientAccount clientAccount7 = insertClientAccount(307, client5.id, 4323, "300555222");
    ClientAccount clientAccount8 = insertClientAccount(308, client5.id, -100, "300555333");


    ClientToFilter filter = new ClientToFilter("id", "ASC", "", 1, 4);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(view.clientRecordList).hasSize(5);

    assertThat(view.clientRecordList.get(0).id).isEqualTo(client1.id);
    assertThat(view.clientRecordList.get(0).fio).isEqualTo(client1.surname + " " + client1.name + " " + client1.patronymic);
    assertThat(view.clientRecordList.get(0).character).isEqualTo(charm1.name);
    assertThat(view.clientRecordList.get(0).age).isEqualTo(41);
    assertThat(view.clientRecordList.get(0).balance).isEqualTo(clientAccount1.money);
    assertThat(view.clientRecordList.get(0).balanceMax).isEqualTo(clientAccount1.money);
    assertThat(view.clientRecordList.get(0).balanceMin).isEqualTo(clientAccount1.money);

    assertThat(view.clientRecordList.get(1).id).isEqualTo(client2.id);
    assertThat(view.clientRecordList.get(1).fio).isEqualTo(client2.surname + " " + client2.name + " " + client2.patronymic);
    assertThat(view.clientRecordList.get(1).character).isEqualTo(charm1.name);
    assertThat(view.clientRecordList.get(1).age).isEqualTo(19);
    assertThat(view.clientRecordList.get(1).balance).isEqualTo(clientAccount2.money + clientAccount3.money);
    assertThat(view.clientRecordList.get(1).balanceMax).isEqualTo(clientAccount2.money);
    assertThat(view.clientRecordList.get(1).balanceMin).isEqualTo(clientAccount3.money);

    assertThat(view.clientRecordList.get(2).id).isEqualTo(client3.id);
    assertThat(view.clientRecordList.get(2).fio).isEqualTo(client3.surname + " " + client3.name + " " + client3.patronymic);
    assertThat(view.clientRecordList.get(2).character).isEqualTo(charm2.name);
    assertThat(view.clientRecordList.get(2).age).isEqualTo(53);
    assertThat(view.clientRecordList.get(2).balance).isEqualTo(clientAccount4.money);
    assertThat(view.clientRecordList.get(2).balanceMax).isEqualTo(clientAccount4.money);
    assertThat(view.clientRecordList.get(2).balanceMin).isEqualTo(clientAccount4.money);

    assertThat(view.clientRecordList.get(3).id).isEqualTo(client4.id);
    assertThat(view.clientRecordList.get(3).fio).isEqualTo(client4.surname + " " + client4.name + " " + client4.patronymic);
    assertThat(view.clientRecordList.get(3).character).isEqualTo(charm2.name);
    assertThat(view.clientRecordList.get(3).age).isEqualTo(18);
    assertThat(view.clientRecordList.get(3).balance).isEqualTo(clientAccount5.money);
    assertThat(view.clientRecordList.get(3).balanceMax).isEqualTo(clientAccount5.money);
    assertThat(view.clientRecordList.get(3).balanceMin).isEqualTo(clientAccount5.money);

    assertThat(view.clientRecordList.get(4).id).isEqualTo(client5.id);
    assertThat(view.clientRecordList.get(4).fio).isEqualTo(client5.surname + " " + client5.name + " " + client5.patronymic);
    assertThat(view.clientRecordList.get(4).character).isEqualTo(charm2.name);
    assertThat(view.clientRecordList.get(4).age).isEqualTo(44);
    assertThat(view.clientRecordList.get(4).balance).isEqualTo(clientAccount6.money + clientAccount7.money + clientAccount8.money);
    assertThat(view.clientRecordList.get(4).balanceMax).isEqualTo(clientAccount7.money);
    assertThat(view.clientRecordList.get(4).balanceMin).isEqualTo(clientAccount8.money);
  }


  @Test
  public void render_list_sortColumn_id_sortDirection_asc() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "ASC", "", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(3);
    assertThat(view.clientRecordList).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(101);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(list.get(2).id).isEqualTo(103);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(101);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(102);
    assertThat(view.clientRecordList.get(2).id).isEqualTo(103);
  }

  @Test
  public void render_list_sortColumn_id_sortDirection_desc() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "DESC", "", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(3);
    assertThat(view.clientRecordList).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(103);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(list.get(2).id).isEqualTo(101);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(103);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(102);
    assertThat(view.clientRecordList.get(2).id).isEqualTo(101);
  }

  @Test
  public void render_list_sortColumn_fio_sortDirection_asc() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(121, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(122, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(123, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("fio", "ASC", "", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(3);
    assertThat(view.clientRecordList).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(121);
    assertThat(list.get(1).id).isEqualTo(123);
    assertThat(list.get(2).id).isEqualTo(122);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(121);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(123);
    assertThat(view.clientRecordList.get(2).id).isEqualTo(122);
  }

  @Test
  public void render_list_sortColumn_fio_sortDirection_desc() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(111, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(112, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(113, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("fio", "DESC", "", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(3);
    assertThat(view.clientRecordList).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(112);
    assertThat(list.get(1).id).isEqualTo(113);
    assertThat(list.get(2).id).isEqualTo(111);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(112);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(113);
    assertThat(view.clientRecordList.get(2).id).isEqualTo(111);
  }

  @Test
  public void render_list_sortColumn_age_sortDirection_asc() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("age", "ASC", "", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(3);
    assertThat(view.clientRecordList).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(102);
    assertThat(list.get(1).id).isEqualTo(101);
    assertThat(list.get(2).id).isEqualTo(103);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(102);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(101);
    assertThat(view.clientRecordList.get(2).id).isEqualTo(103);
  }

  @Test
  public void render_list_sortColumn_age_sortDirection_desc() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("age", "DESC", "", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(3);
    assertThat(view.clientRecordList).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(103);
    assertThat(list.get(1).id).isEqualTo(101);
    assertThat(list.get(2).id).isEqualTo(102);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(103);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(101);
    assertThat(view.clientRecordList.get(2).id).isEqualTo(102);
  }

  @Test
  public void render_list_sortColumn_balance_sortDirection_asc() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccount(101, 101, 100, "000");
    insertClientAccount(102, 101, 150, "001");

    insertClientAccount(103, 102, 50, "002");
    insertClientAccount(104, 102, 400, "003");

    insertClientAccount(105, 103, 1000, "004");
    insertClientAccount(106, 103, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balance", "ASC", "", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(3);
    assertThat(view.clientRecordList).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(101);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(list.get(2).id).isEqualTo(103);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(101);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(102);
    assertThat(view.clientRecordList.get(2).id).isEqualTo(103);
  }

  @Test
  public void render_list_sortColumn_balance_sortDirection_desc() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccount(101, 101, 100, "000");
    insertClientAccount(102, 101, 150, "001");

    insertClientAccount(103, 102, 50, "002");
    insertClientAccount(104, 102, 400, "003");

    insertClientAccount(105, 103, 1000, "004");
    insertClientAccount(106, 103, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balance", "DESC", "", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(3);
    assertThat(view.clientRecordList).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(103);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(list.get(2).id).isEqualTo(101);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(103);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(102);
    assertThat(view.clientRecordList.get(2).id).isEqualTo(101);
  }

  @Test
  public void render_list_sortColumn_balanceMax_sortDirection_asc() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccount(101, 101, 100, "000");
    insertClientAccount(102, 101, 150, "001");

    insertClientAccount(103, 102, 50, "002");
    insertClientAccount(104, 102, 400, "003");

    insertClientAccount(105, 103, 1000, "004");
    insertClientAccount(106, 103, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balanceMax", "ASC", "", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(3);
    assertThat(view.clientRecordList).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(101);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(list.get(2).id).isEqualTo(103);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(101);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(102);
    assertThat(view.clientRecordList.get(2).id).isEqualTo(103);
  }

  @Test
  public void render_list_sortColumn_balanceMax_sortDirection_desc() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccount(101, 101, 100, "000");
    insertClientAccount(102, 101, 150, "001");

    insertClientAccount(103, 102, 50, "002");
    insertClientAccount(104, 102, 400, "003");

    insertClientAccount(105, 103, 1000, "004");
    insertClientAccount(106, 103, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balanceMax", "DESC", "", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(3);
    assertThat(view.clientRecordList).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(103);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(list.get(2).id).isEqualTo(101);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(103);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(102);
    assertThat(view.clientRecordList.get(2).id).isEqualTo(101);
  }

  @Test
  public void render_list_sortColumn_balanceMin_sortDirection_asc() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccount(101, 101, 100, "000");
    insertClientAccount(102, 101, 150, "001");

    insertClientAccount(103, 102, 50, "002");
    insertClientAccount(104, 102, 400, "003");

    insertClientAccount(105, 103, 1000, "004");
    insertClientAccount(106, 103, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balanceMin", "ASC", "", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(3);
    assertThat(view.clientRecordList).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(102);
    assertThat(list.get(1).id).isEqualTo(101);
    assertThat(list.get(2).id).isEqualTo(103);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(102);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(101);
    assertThat(view.clientRecordList.get(2).id).isEqualTo(103);
  }

  @Test
  public void render_list_sortColumn_balanceMin_sortDirection_desc() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    insertClientAccount(101, 101, 100, "000");
    insertClientAccount(102, 101, 150, "001");

    insertClientAccount(103, 102, 50, "002");
    insertClientAccount(104, 102, 400, "003");

    insertClientAccount(105, 103, 1000, "004");
    insertClientAccount(106, 103, 10000, "005");

    ClientToFilter filter = new ClientToFilter("balanceMin", "DESC", "", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //


    assertThat(list).hasSize(3);
    assertThat(view.clientRecordList).hasSize(3);

    assertThat(list.get(0).id).isEqualTo(103);
    assertThat(list.get(1).id).isEqualTo(101);
    assertThat(list.get(2).id).isEqualTo(102);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(103);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(101);
    assertThat(view.clientRecordList.get(2).id).isEqualTo(102);
  }

  @Test
  public void render_list_with_filter_fio_surname() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(1010, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(1020, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(1030, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "ASC", "К", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(2);
    assertThat(view.clientRecordList).hasSize(2);

    assertThat(list.get(0).id).isEqualTo(1010);
    assertThat(list.get(1).id).isEqualTo(1030);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(1010);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(1030);
  }

  @Test
  public void render_list_with_filter_fio_name() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "ASC", "ли", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(2);
    assertThat(view.clientRecordList).hasSize(2);

    assertThat(list.get(0).id).isEqualTo(101);
    assertThat(list.get(1).id).isEqualTo(102);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(101);
    assertThat(view.clientRecordList.get(1).id).isEqualTo(102);
  }

  @Test
  public void render_list_with_filter_fio_patronymic() throws Exception {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

    insertClient(101, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charmId);
    insertClient(102, "Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), charmId);
    insertClient(103, "Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), charmId);

    ClientToFilter filter = new ClientToFilter("id", "ASC", "Уле", 1, 10);

    ClientReportViewTest view = new ClientReportViewTest();

    RenderFilter renderFilter = new RenderFilter(filter, "De Sali", new Date(), view);

    //
    //
    List<ClientRecord> list = clientRegister.get().list(filter);

    clientRegister.get().render(renderFilter);
    //
    //

    assertThat(list).hasSize(1);
    assertThat(view.clientRecordList).hasSize(1);

    assertThat(list.get(0).id).isEqualTo(103);
    assertThat(view.clientRecordList.get(0).id).isEqualTo(103);
  }


  @Test
  public void list_with_pagination_top_items() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

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

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

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

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

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

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

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
  public void count() {

    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

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

    ClientToFilter filter = new ClientToFilter("id", "ASC", "", 1, 5);

    //
    //
    int clientRecordCount = clientRegister.get().count(filter);
    //
    //

    assertThat(clientRecordCount).isEqualTo(10);
  }

  @Test
  public void count_with_filter_fio_surname() {

    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

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

    ClientToFilter filter = new ClientToFilter("id", "ASC", "ова", 1, 5);

    //
    //
    int clientRecordCount = clientRegister.get().count(filter);
    //
    //

    assertThat(clientRecordCount).isEqualTo(4);
  }

  @Test
  public void count_with_filter_fio_name() {

    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

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

    ClientToFilter filter = new ClientToFilter("id", "ASC", "али", 1, 5);

    //
    //
    int clientRecordCount = clientRegister.get().count(filter);
    //
    //

    assertThat(clientRecordCount).isEqualTo(2);
  }

  @Test
  public void count_with_filter_fio_patronymic() {

    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    int charmId = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100).id;

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

    ClientToFilter filter = new ClientToFilter("id", "ASC", "вич", 1, 5);

    //
    //
    int clientRecordCount = clientRegister.get().count(filter);
    //
    //

    assertThat(clientRecordCount).isEqualTo(5);
  }


  @Test
  public void save_create() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    Character charm = insertCharacter(RND.plusInt(999999), RND.str(10), RND.str(20), RND.plusInt(100));

    // FIXME: 10/8/18 если все пишешь в отдельную строчку, то лучше сделай присвоение каждого филда через =
    ClientToSave clientToSave = new ClientToSave(null, RND.str(10), RND.str(6), "", new GregorianCalendar(1977, 4, 25).getTime(), "MALE", charm.id, RND.str(15), RND.str(3), RND.str(1), "", "", "", new ArrayList<>(Collections.singletonList(new PhoneDisplay(0, "HOME", RND.str(11)))));

    //
    //
    ClientRecord clientRecord = clientRegister.get().save(clientToSave);
    //
    //

    ClientDetails clientDetails = clientTestDao.get().details(clientRecord.id);
    clientDetails.numbers = clientTestDao.get().getClientPhones(clientDetails.id);


    // FIXME: 10/8/18 А как же остальные колонки в базе
    assertThat(clientRecord.id).isNotNull();
    assertThat(clientRecord.fio).isEqualTo(clientDetails.surname + " " + clientDetails.name + " " + clientDetails.patronymic);
    assertThat(clientRecord.character).isEqualTo(charm.name);
    assertThat(clientRecord.age).isEqualTo(41);
    assertThat(clientRecord.balance).isNotNull().isEqualTo(0);
    assertThat(clientRecord.balanceMax).isEqualTo(0);
    assertThat(clientRecord.balanceMin).isEqualTo(0);


    assertThat(clientDetails.id).isNotNull().isEqualTo(clientRecord.id);
    assertThat(clientDetails.surname).isNotNull().isEqualTo(clientToSave.surname);
    assertThat(clientDetails.name).isNotNull().isEqualTo(clientToSave.name);
    assertThat(clientDetails.patronymic).isNotNull().isEqualTo(clientToSave.patronymic);
    assertThat(clientDetails.gender).isNotNull().isEqualTo(clientToSave.gender);
    assertThat(clientDetails.birthDate).isNotNull().isInThePast().isEqualTo(clientToSave.birthDate);
    assertThat(clientDetails.characterId).isNotNull().isEqualTo(charm.id);
    assertThat(clientDetails.streetRegistration).isNotNull().isEqualTo(clientToSave.streetRegistration);
    assertThat(clientDetails.houseRegistration).isNotNull().isEqualTo(clientToSave.houseRegistration);
    assertThat(clientDetails.apartmentRegistration).isNotNull().isEqualTo(clientToSave.apartmentRegistration);
    assertThat(clientDetails.streetResidence).isNotNull().isEqualTo(clientToSave.streetResidence);
    assertThat(clientDetails.houseResidence).isNotNull().isEqualTo(clientToSave.houseResidence);
    assertThat(clientDetails.apartmentResidence).isNotNull().isEqualTo(clientToSave.apartmentResidence);

    assertThat(clientDetails.numbers).hasSize(1);

    assertThat(clientDetails.numbers.get(0).type).isEqualTo(clientToSave.numbers.get(0).type);
    assertThat(clientDetails.numbers.get(0).number).isEqualTo(clientToSave.numbers.get(0).number);
  }

  @Test
  public void save_update() {
    clientTestDao.get().removeAll();
    characterTestDao.get().removeAll();

    Character charm = insertCharacter(7001, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100);
    Character newCharm = insertCharacter(7002, "Характерный", "Характерный Характерный", 100);

    Client client = insertClient(7001, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1950, 1, 1).getTime(), charm.id);

    insertClientAddr(client.id, "REG", "Lomonosov", "10", "1");
    insertClientAddr(client.id, "FACT", "Lomonosov Fact", "20", "2");

    insertClientPhone(7001, client.id, "HOME", "87077070077");
    insertClientPhone(7002, client.id, "MOBILE", "77007007070");


    ClientToSave clientToSave = new ClientToSave(7001, "Фамилия", "Имя", "Розалия", new GregorianCalendar(1980, 1, 1).getTime(), "FEMALE", newCharm.id, "Ломоносов Рег", "1", "10", "Lomonosov Факт", "2", "20", new ArrayList<>());

    clientToSave.numbersChange.get("created").add(new PhoneDisplay(0, "WORK", "11001010011"));

    clientToSave.numbersChange.get("updated").add(new PhoneDisplay(7001, "MOBILE", "87077070066"));

    clientToSave.numbersChange.get("deleted").add(new PhoneDisplay(7002, "", ""));

    //
    //
    ClientRecord clientRecord = clientRegister.get().save(clientToSave);
    //
    //

    // FIXME: 10/8/18 А как же остальные колонки в базе
    assertThat(clientRecord.id).isNotNull().isEqualTo(clientToSave.id);
    assertThat(clientRecord.fio).isNotNull().isEqualTo(clientToSave.surname + " " + clientToSave.name + " " + clientToSave.patronymic);
    assertThat(clientRecord.character).isNotNull().isEqualTo(newCharm.name);
    assertThat(clientRecord.age).isNotNull().isEqualTo(38);
    assertThat(clientRecord.balance).isNotNull();
    assertThat(clientRecord.balanceMax).isNotNull();
    assertThat(clientRecord.balanceMin).isNotNull();


    ClientDetails clientDetails = clientTestDao.get().details(clientRecord.id);
    clientDetails.numbers = clientTestDao.get().getClientPhones(clientDetails.id);

    assertThat(clientDetails.id).isNotNull().isEqualTo(clientRecord.id);
    assertThat(clientDetails.surname).isNotNull().isEqualTo(clientToSave.surname);
    assertThat(clientDetails.name).isNotNull().isEqualTo(clientToSave.name);
    assertThat(clientDetails.patronymic).isNotNull().isEqualTo(clientToSave.patronymic);
    assertThat(clientDetails.gender).isNotNull().isEqualTo(clientToSave.gender);
    assertThat(clientDetails.birthDate).isNotNull().isInThePast().isEqualTo(clientToSave.birthDate);
    assertThat(clientDetails.characterId).isNotNull().isEqualTo(newCharm.id);
    assertThat(clientDetails.streetRegistration).isNotNull().isEqualTo(clientToSave.streetRegistration);
    assertThat(clientDetails.houseRegistration).isNotNull().isEqualTo(clientToSave.houseRegistration);
    assertThat(clientDetails.apartmentRegistration).isNotNull().isEqualTo(clientToSave.apartmentRegistration);
    assertThat(clientDetails.streetResidence).isNotNull().isEqualTo(clientToSave.streetResidence);
    assertThat(clientDetails.houseResidence).isNotNull().isEqualTo(clientToSave.houseResidence);
    assertThat(clientDetails.apartmentResidence).isNotNull().isEqualTo(clientToSave.apartmentResidence);

    assertThat(clientDetails.numbers).hasSize(2);

    System.out.println(clientDetails.numbers);

    assertThat(clientDetails.numbers.get(0).type).isEqualTo("WORK");
    assertThat(clientDetails.numbers.get(0).number).isEqualTo("11001010011");

    assertThat(clientDetails.numbers.get(1).id).isEqualTo(7001);
    assertThat(clientDetails.numbers.get(1).type).isEqualTo("MOBILE");
    assertThat(clientDetails.numbers.get(1).number).isEqualTo("87077070066");
  }

  @Test
  public void details() {
    Character charm = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100);
    Client client = insertClient(201, "Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), charm.id);

    ClientAddr clientAddrReg = insertClientAddr(201, "REG", "Lomonosov", "1", "2");
    ClientAddr clientAddrFact = insertClientAddr(201, "FACT", "Lomonosova Fact", "10", "20");

    ClientPhone clientPhoneHome = insertClientPhone(900, 201, "HOME", "87077070077");
    ClientPhone clientPhoneMobile = insertClientPhone(901, 201, "MOBILE", "12345678899");

    //
    //
    ClientDetails clientDetails = clientRegister.get().details(client.id);
    //
    //

    assertThat(clientDetails.id).isNotNull().isEqualTo(201);
    assertThat(clientDetails.surname).isNotNull().isEqualTo(client.surname);
    assertThat(clientDetails.name).isNotNull().isEqualTo(client.name);
    assertThat(clientDetails.patronymic).isNotNull().isEqualTo(client.patronymic);
    assertThat(clientDetails.gender).isNotNull().isEqualTo(client.gender);
    assertThat(clientDetails.birthDate).isNotNull().isInThePast().isEqualTo(client.birth_date);
    assertThat(clientDetails.characterId).isNotNull().isEqualTo(charm.id);
    assertThat(clientDetails.streetRegistration).isNotNull().isEqualTo(clientAddrReg.street);
    assertThat(clientDetails.houseRegistration).isNotNull().isEqualTo(clientAddrReg.house);
    assertThat(clientDetails.apartmentRegistration).isNotNull().isEqualTo(clientAddrReg.flat);
    assertThat(clientDetails.streetResidence).isNotNull().isEqualTo(clientAddrFact.street);
    assertThat(clientDetails.houseResidence).isNotNull().isEqualTo(clientAddrFact.house);
    assertThat(clientDetails.apartmentResidence).isNotNull().isEqualTo(clientAddrFact.flat);

    assertThat(clientDetails.numbers).hasSize(2);

    assertThat(clientDetails.numbers.get(0).type).isEqualTo(clientPhoneHome.type);
    assertThat(clientDetails.numbers.get(0).number).isEqualTo(clientPhoneHome.number);

    assertThat(clientDetails.numbers.get(1).type).isEqualTo(clientPhoneMobile.type);
    assertThat(clientDetails.numbers.get(1).number).isEqualTo(clientPhoneMobile.number);
  }

  @Test
  public void delete() {
    Character charm = insertCharacter(101, "Самовлюблённый", "Самовлюблённый Самовлюблённый", 100);
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
