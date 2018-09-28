package kz.greetgo.sandbox.register.test.beans.develop;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.UserCan;
import kz.greetgo.sandbox.register.beans.all.IdGenerator;
import kz.greetgo.sandbox.register.test.dao.AuthTestDao;
import kz.greetgo.sandbox.register.test.dao.CharacterTestDao;
import kz.greetgo.sandbox.register.test.dao.ClientTestDao;
import kz.greetgo.security.password.PasswordEncoder;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

@Bean
public class DbLoader {
  final Logger logger = Logger.getLogger(getClass());


  public BeanGetter<AuthTestDao> authTestDao;
  public BeanGetter<IdGenerator> idGenerator;
  public BeanGetter<PasswordEncoder> passwordEncoder;

  public BeanGetter<CharacterTestDao> characterTestDao;
  public BeanGetter<ClientTestDao> clientTestDao;

  public void loadTestData() throws Exception {

    loadPersons();

    loadClientData();

    logger.info("FINISH");
  }

  @SuppressWarnings("SpellCheckingInspection")
  private void loadPersons() throws Exception {
    logger.info("Start loading persons...");

    user("Пушкин Александр Сергеевич", "1799-06-06", "pushkin");
    user("Сталин Иосиф Виссарионович", "1878-12-18", "stalin");
    user("Берия Лаврентий Павлович", "1899-03-17", "beria");
    user("Есенин Сергей Александрович", "1895-09-21", "esenin");
    user("Путин Владимир Владимирович", "1952-10-07", "putin");
    user("Назарбаев Нурсултан Абишевич", "1940-07-06", "papa");
    user("Менделеев Дмитрий Иванович", "1834-02-08", "mendeleev");
    user("Ломоносов Михаил Васильевич", "1711-11-19", "lomonosov");
    user("Бутлеров Александр Михайлович", "1828-09-15", "butlerov");

    add_can("pushkin", UserCan.VIEW_USERS);
    add_can("stalin", UserCan.VIEW_USERS);
    add_can("stalin", UserCan.VIEW_ABOUT);

    logger.info("Finish loading persons");
  }

  private void user(String fioStr, String birthDateStr, String accountName) throws Exception {
    String id = idGenerator.get().newId();
    String[] fio = fioStr.split("\\s+");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date birthDate = sdf.parse(birthDateStr);
    String encryptPassword = passwordEncoder.get().encode("111");

    authTestDao.get().insertPerson(id, accountName, encryptPassword);
    authTestDao.get().updatePersonField(id, "birth_date", new Timestamp(birthDate.getTime()));
    authTestDao.get().updatePersonField(id, "surname", fio[0]);
    authTestDao.get().updatePersonField(id, "name", fio[1]);
    authTestDao.get().updatePersonField(id, "patronymic", fio[2]);
  }

  private void add_can(String username, UserCan... cans) {
    for (UserCan can : cans) {
      authTestDao.get().upsert(can.name());
      authTestDao.get().personCan(username, can.name());
    }
  }

  private void loadClientData() {
    logger.info("Start loading all client data...");

    loadCharms();
    loadClients();
    loadClientsAddr();
    loadClientsPhone();
    loadClientsAccounts();
    loadTransactionTypes();
    loadClientsAccountTransactions();

    logger.info("Finish loading all client data!");
  }

  private void charm(String name, String description, float energy) {
    characterTestDao.get().insertCharacter(name, description, energy);
  }

  private void loadCharms() {
    logger.info("Start loading charms...");

    charm("Самовлюблённый", "Самовлюблённый Описание", 100);
    charm("Замкнутый", "Замкнутый Описание", 100);
    charm("Великодушный", "Великодушный Описание", 100);
    charm("Бессердечный", "Бессердечный Описание", 100);
    charm("Грубый", "Грубый Описание", 100);
    charm("Целеустремлённый", "Целеустремлённый Описание", 100);
    charm("Мизантроп", "Мизантроп Описание", 100);
    charm("Строгий", "Строгий Описание", 100);
    charm("Гениальный", "Гениальный Описание", 100);
    charm("Харизматичный", "Харизматичный Описание", 100);
    charm("Безответственный", "Безответственный Описание", 100);

    logger.info("Finish loading charms!");
  }

  private void client(String surname, String name, String patronymic, String gender, Date birth_date, int charm) {
    clientTestDao.get().insertClient(surname, name, patronymic, gender, birth_date, charm);
  }

  private void loadClients() {
    logger.info("Start loading clients...");

    client("Колобова", "Розалия", "Наумовна", "FEMALE", new GregorianCalendar(1977, 4, 25).getTime(), 1);
    client("Панова", "Алира", "Иосифовна", "FEMALE", new GregorianCalendar(1999, 7, 16).getTime(), 2);
    client("Крюков", "Игнатий", "Улебович", "MALE", new GregorianCalendar(1965, 2, 2).getTime(), 3);
    client("Киселёв", "Юлиан", "Романович", "MALE", new GregorianCalendar(2000, 4, 16).getTime(), 4);
    client("Исаева", "Ирина", "Сергеевна", "FEMALE", new GregorianCalendar(1974, 5, 11).getTime(), 5);
    client("Большаков", "Мечеслав", "Куприянович", "MALE", new GregorianCalendar(1983, 8, 21).getTime(), 6);
    client("Корнилов", "Захар", "Федосеевич", "MALE", new GregorianCalendar(1984, 6, 11).getTime(), 7);
    client("Лихачёв", "Исак", "Кириллович", "MALE", new GregorianCalendar(1947, 8, 13).getTime(), 8);
    client("Фёдорова", "Эмбер", "Руслановна", "FEMALE", new GregorianCalendar(1977, 2, 8).getTime(), 9);
    client("Баранова", "Габриэлла", "Романовна", "FEMALE", new GregorianCalendar(2002, 1, 28).getTime(), 10);
    client("Никонов", "Лев", "Викторович", "MALE", new GregorianCalendar(2000, 7, 11).getTime(), 11);

    logger.info("Finish loading clients!");
  }

  private void client_addr(int client, String type, String street, String house, String flat) {
    clientTestDao.get().insertClientAddr(client, type, street, house, flat);
  }

  private void loadClientsAddr() {
    logger.info("Start loading clients Addresses...");

    client_addr(1, "REG", "Ломоносова", "11", "1");
    client_addr(2, "REG", "Пр.абылай Хана", "22", "2");
    client_addr(3, "REG", "Ломоносова", "99", "4");
    client_addr(4, "REG", "Пр.абылай Хана", "33", "5");
    client_addr(5, "REG", "Ломоносова", "88", "2");
    client_addr(6, "REG", "Пр.абылай Хана", "44", "1");
    client_addr(7, "REG", "Ломоносова", "34", "1");
    client_addr(8, "REG", "Пр.абылай Хана", "55", "2");
    client_addr(9, "REG", "Ломоносова", "77", "4");
    client_addr(10, "REG", "Пр.абылай Хана", "66", "3");
    client_addr(11, "REG", "Ломоносова", "27", "3");

    logger.info("Finish loading clients Addresses!");
  }

  private void client_phone(int client, String type, String number) {
    clientTestDao.get().insertClientPhone(client, type, number);
  }

  private void loadClientsPhone() {
    logger.info("Start loading clients Phones...");

    client_phone(1, "HOME", "77077070071");
    client_phone(2, "HOME", "77077070072");
    client_phone(3, "HOME", "77077070073");
    client_phone(4, "HOME", "77077070074");
    client_phone(5, "HOME", "77077070075");
    client_phone(6, "HOME", "77077070076");
    client_phone(7, "HOME", "77077070077");
    client_phone(8, "HOME", "77077070078");
    client_phone(9, "HOME", "77077070079");
    client_phone(10, "HOME", "77077070080");
    client_phone(11, "HOME", "77077070081");

    logger.info("Finish loading clients Phones!");
  }

  private void client_account(int client, String number) {
    clientTestDao.get().insertClientAccount(client, number);
  }

  private void loadClientsAccounts() {
    logger.info("Start loading clients Accounts...");

    client_account(1, "1111111111");
    client_account(2, "1111111112");
    client_account(3, "1111111113");
    client_account(4, "1111111114");
    client_account(5, "1111111115");
    client_account(6, "1111111116");
    client_account(7, "1111111117");
    client_account(8, "1111111118");
    client_account(9, "1111111119");
    client_account(10, "1111111120");
    client_account(11, "1111111121");

    logger.info("Finish loading clients Accounts!");
  }

  private void transaction_type(String code, String name) {
    clientTestDao.get().insertTransactionType(code, name);
  }

  private void loadTransactionTypes() {
    logger.info("Start loading transaction types...");

    transaction_type("00001", "Payment");
    transaction_type("00002", "Abort");
    transaction_type("00003", "Repeat");
    transaction_type("00004", "Refund");
    transaction_type("00005", "Void");

    logger.info("Finish loading transaction types!");
  }

  private void client_account_transaction(int account, float money, int type) {
    clientTestDao.get().insertClientAccountTransaction(account, money, type);
  }

  private void loadClientsAccountTransactions() {
    logger.info("Start loading clients Account Transactions...");

    client_account_transaction(1, -10, 1);
    client_account_transaction(1, 100, 4);
    client_account_transaction(2, -10, 1);
    client_account_transaction(3, -10, 1);
    client_account_transaction(4, -10, 1);
    client_account_transaction(5, -10, 1);
    client_account_transaction(6, -10, 1);
    client_account_transaction(7, -10, 1);
    client_account_transaction(8, -10, 1);
    client_account_transaction(9, -10, 1);
    client_account_transaction(10, -10, 1);
    client_account_transaction(11, -10, 1);

    logger.info("Finish loading clients Account Transactions!");
  }
}
