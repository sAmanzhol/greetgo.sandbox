package kz.greetgo.sandbox.register.test.beans.develop;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.enums.AddressType;
import kz.greetgo.sandbox.controller.model.enums.Gender;
import kz.greetgo.sandbox.controller.model.enums.PhoneType;
import kz.greetgo.sandbox.controller.register.*;
import kz.greetgo.sandbox.register.beans.all.IdGenerator;
import kz.greetgo.sandbox.register.test.dao.AuthTestDao;
import kz.greetgo.security.password.PasswordEncoder;
import kz.greetgo.util.RND;
import org.apache.log4j.Logger;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Bean
public class DbLoader {
  final Logger logger = Logger.getLogger(getClass());

  String[] name = {"Пушкин","Александр","Сталин","Пушкин","Берия","Лавров","Есенин","Путин","Назик","Менделей","Дмитрий"};

  String[] surname = {"Менделеев","Назарбаев","Лаврентий","Ломоносов","Бутлеров","Лавров","Есенин","Путин","Кыртымбаев","Менделей"};

  String[] patronymic = {"Сергеевич","Виссарионович","Павлович","Александрович","Владимирович","Абишевич","Иванович","Васильевич"};

  public BeanGetter<AuthTestDao> authTestDao;
  public BeanGetter<IdGenerator> idGenerator;
  public BeanGetter<PasswordEncoder> passwordEncoder;

  public BeanGetter <CharmRegister> charmRegister;
  public BeanGetter <AddressRegister> addressRegister;
  public BeanGetter <ClientRegister> clientRegister;
  public BeanGetter <PhoneRegister> phoneRegister;
  public BeanGetter <AccountRegister> accountRegister;

  public List<Charm> charmList;
  public List<Long> clientList = new ArrayList<>();

  public void loadTestData() throws Exception {

    loadPersons();
    loadCharms();
    loadClients();
    loadAddress();
    loadPhones();
    loadAccounts();

    logger.info("FINISH");
  }
  private void loadAccounts(){
    for (Long id:clientList) {
      accountRegister.get().insert(new Account(null,id,Double.valueOf(RND.intStr(5)),"KZ"+RND.str(16),new Timestamp(System.currentTimeMillis()),true));
      accountRegister.get().insert(new Account(null,id,Double.valueOf(RND.intStr(3)),"KZ"+RND.str(16),new Timestamp(System.currentTimeMillis()),true));
      accountRegister.get().insert(new Account(null,id,Double.valueOf(RND.intStr(4)),"KZ"+RND.str(16),new Timestamp(System.currentTimeMillis()),true));
    }
  }


  private void loadPhones() {
    for (Long id:clientList) {
      phoneRegister.get().insert(new Phone(null,id,RND.intStr(8),PhoneType.HOME,true));
      phoneRegister.get().insert(new Phone(null,id,RND.intStr(8),PhoneType.WORK,true));
      phoneRegister.get().insert(new Phone(null,id,RND.intStr(8),PhoneType.MOBILE,true));
      phoneRegister.get().insert(new Phone(null,id,RND.intStr(8),PhoneType.MOBILE,true));
    }
  }

  private void loadAddress() {
    for (Long id:clientList) {
      addressRegister.get().insert(new Address(null,id,AddressType.FACT,"Улица пушкина "+(id*6l) +" ","Дом кукушкина " +id,"Кв : " + id*3,true));
      addressRegister.get().insert(new Address(null,id,AddressType.REG,"Улица Кукушкина "+(id*2l) +" ","Дом пушкина " +id*7,"Кв : " + id*5,true));
    }
  }

  private void loadClients() {
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
    clientList.add(clientRegister.get().insert(generateClient()));
  }

  private void loadCharms() {
    charmRegister.get().insert(new Charm(null,"Балабол","Балаболит как може",40.0,true));
    charmRegister.get().insert(new Charm(null,"Строгий","Строгий",40.0,true));
    charmRegister.get().insert(new Charm(null,"Безумец","Безумец",40.0,true));
    charmRegister.get().insert(new Charm(null,"Хороший","Хороший",40.0,true));
    charmRegister.get().insert(new Charm(null,"Остряк,","Остряк",40.0,true));
    charmRegister.get().insert(new Charm(null,"Добряк","Добряк",40.0,true));
    charmRegister.get().insert(new Charm(null,"Красавчик","Красавчик",40.0,true));
    charmRegister.get().insert(new Charm(null,"Маладец","Маладец",40.0,true));
    charmRegister.get().insert(new Charm(null,"Волевой","Волевой",40.0,true));
    charmRegister.get().insert(new Charm(null,"Высокомерный","Высокомерный",40.0,true));

    charmList = charmRegister.get().list();
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

  private Client generateClient()
  {
    try {
      Random random = new Random();
      int sl = surname.length;
      int nl = name.length;
      int pl = patronymic.length;
      int chl = charmList.size();
      Gender gender = RND.someEnum(Gender.FEMALE, Gender.MALE);

      Timestamp birthDate = new Timestamp(new Date().getTime());

      Client client = new Client(null, surname[random.nextInt(sl)], name[random.nextInt(nl)], patronymic[random.nextInt(pl)], gender,
              birthDate,charmList.get(random.nextInt(chl)
      ));

      return client;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
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
}
