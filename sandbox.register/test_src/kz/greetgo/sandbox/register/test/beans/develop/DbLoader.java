package kz.greetgo.sandbox.register.test.beans.develop;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.UserCan;
import kz.greetgo.sandbox.register.beans.all.IdGenerator;
import kz.greetgo.sandbox.register.test.dao.AuthTestDao;
import kz.greetgo.sandbox.register.test.dao.ClientTestDao;
import kz.greetgo.security.password.PasswordEncoder;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Bean
public class DbLoader {
  final Logger logger = Logger.getLogger(getClass());


  public BeanGetter<AuthTestDao> authTestDao;
  public BeanGetter<ClientTestDao> clientTestDao;
  public BeanGetter<IdGenerator> idGenerator;
  public BeanGetter<PasswordEncoder> passwordEncoder;

  public void loadTestData() throws Exception {

    loadPersons();
    loadClients();

    logger.info("FINISH");
  }

  @SuppressWarnings("SpellCheckingInspection")
  private void loadClients() throws ParseException {
    load_charm_list();
    load_client_list();
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

  private Timestamp formateBirthDate(String birthDateStr) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
    Date birthDate = sdf.parse(birthDateStr);
    return new Timestamp(birthDate.getTime());
  }

  private void load_charm_list () {
    //load charm list
    clientTestDao.get().insertCharm(1, "sociable");
    clientTestDao.get().insertCharm(2, "polite");
    clientTestDao.get().insertCharm(3, "quiet");
    clientTestDao.get().insertCharm(4, "aggressive");
    clientTestDao.get().insertCharm(5, "ambitious");
    clientTestDao.get().insertCharm(6, "intelligent");
    clientTestDao.get().insertCharm(7, "honest");
    clientTestDao.get().insertCharm(8, "daring");
    clientTestDao.get().insertCharm(9, "reliable");
    clientTestDao.get().insertCharm(10, "artistic");
    clientTestDao.get().insertCharm(11, "patient");
    clientTestDao.get().insertCharm(12, "sensitive");
  }


  private void load_client_list () throws ParseException {
    //load client list
    //insert into client (id, surname, name, gender, birth_date, charm)
    clientTestDao.get().insertClient(1, "Kim", "Igor", "MALE", formateBirthDate("11.12.1993"), 2);
    clientTestDao.get().insertClient(2, "Ivanov", "Alexey", "MALE", formateBirthDate("11.03.1995"), 1);
    clientTestDao.get().insertClient(3, "Coi", "Vika", "FEMALE", formateBirthDate("11.02.1992"), 5);
    clientTestDao.get().insertClient(4, "Li", "Andrey", "MALE", formateBirthDate("10.12.1995"), 12);
    clientTestDao.get().insertClient(5, "Mihailova", "Nadezhda", "FEMALE", formateBirthDate("01.10.1955"), 9);
    clientTestDao.get().insertClient(6, "Nikulin", "Yuriy", "MALE", formateBirthDate("19.07.1997"), 5);
    clientTestDao.get().insertClient(7, "Ahmetov", "Ahmet", "MALE", formateBirthDate("05.09.1982"), 3);
    clientTestDao.get().insertClient(8, "Igoreva", "Natazha", "FEMALE", formateBirthDate("17.04.1986"), 5);
    clientTestDao.get().insertClient(9, "Nikitin", "Alex", "MALE", formateBirthDate("06.08.1991"), 2);
    clientTestDao.get().insertClient(10, "Medvedeva", "Tanya", "FEMALE", formateBirthDate("11.01.1994"), 4);
    clientTestDao.get().insertClient(11, "Iureva", "Viktoria", "FEMALE", formateBirthDate("18.06.1998"), 8);
    clientTestDao.get().insertClient(12, "Li", "Dmitriy", "MALE", formateBirthDate("25.08.1994"), 1);
    clientTestDao.get().insertClient(13, "Kim", "Kristina", "FEMALE", formateBirthDate("16.04.1990"), 10);
    clientTestDao.get().insertClient(14, "Romanov", "Sasha", "MALE", formateBirthDate("17.03.1997"), 11);
    clientTestDao.get().insertClient(15, "Romanova", "Kim", "FEMALE", formateBirthDate("11.12.1991"), 2);

  }
}
