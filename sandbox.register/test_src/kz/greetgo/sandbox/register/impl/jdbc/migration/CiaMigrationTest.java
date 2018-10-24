package kz.greetgo.sandbox.register.impl.jdbc.migration;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.register.configs.MigrationConfig;
import kz.greetgo.sandbox.register.dao_model.Client;
import kz.greetgo.sandbox.register.dao_model.ClientAddress;
import kz.greetgo.sandbox.register.dao_model.ClientPhone;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaAddress;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaClient;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaPhone;
import kz.greetgo.sandbox.register.test.dao.MigrationTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@SuppressWarnings("WeakerAccess")
public class CiaMigrationTest extends ParentTestNg {

  public BeanGetter<MigrationConfig> migrationConfig;
  public BeanGetter<MigrationTestDao> migrationTestDao;

  private CiaMigrationCallbackImpl ciaMigration;

  private void prepareTempTables() throws Exception {
    ciaMigration = new CiaMigrationCallbackImpl("");

    ciaMigration.dropTemplateTables();
    ciaMigration.createTempTables();
  }

  @Test
  public void parseAndFillData_client() throws Exception {

    prepareTempTables();

    String fileName = "from_cia_2018-02-21-154532-1-300.xml";
    String filePath = String.format("%s/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    ciaMigration = new CiaMigrationCallbackImpl(filePath);
    ciaMigration.parseAndFillData();
    //
    //

    List<CiaClient> clients = migrationTestDao.get().getClients();

    assertThat(clients).hasSize(4);

    assertThat(clients.get(0).id).isEqualTo("0-B9N-HT-PU-04wolRBPzj");
    assertThat(clients.get(0).surname).isEqualTo("");
    assertThat(clients.get(0).name).isEqualTo("WCИTБЯ7щАо");
    assertThat(clients.get(0).patronymic).isEqualTo("");
    assertThat(clients.get(0).gender).isEqualTo("FEMALE");
    assertThat(clients.get(0).birthDate).isEqualTo("1995-07-07");
    assertThat(clients.get(0).charm).isEqualTo("ЩlВOpФpЪИШ");

    assertThat(clients.get(1).id).isEqualTo("0-B9N-HT-PU-04wolRBPzj");
    assertThat(clients.get(1).surname).isEqualTo("ЕDфзEуЧь57");
    assertThat(clients.get(1).name).isEqualTo("NIfТDтуЯkТ");
    assertThat(clients.get(1).patronymic).isEqualTo("УДтЮцКp5ЁЛайl");
    assertThat(clients.get(1).gender).isEqualTo("MALE");
    assertThat(clients.get(1).birthDate).isEqualTo("1966-03-10");
    assertThat(clients.get(1).charm).isEqualTo("БОШХы5UЪМЗ");

    assertThat(clients.get(2).id).isEqualTo("2-MFR-HN-2X-LPT6Ex2SPW");
    assertThat(clients.get(2).surname).isEqualTo("ФAcgsHZЪкp");
    assertThat(clients.get(2).name).isEqualTo("2вцнwкПnУи");
    assertThat(clients.get(2).patronymic).isEqualTo("ЁWЛ2я1КЬОLAФъ");
    assertThat(clients.get(2).gender).isEqualTo("MALE");
    assertThat(clients.get(2).birthDate).isEqualTo("1941-07-23");
    assertThat(clients.get(2).charm).isEqualTo("пlJХёsъСAШ");

    assertThat(clients.get(3).id).isEqualTo("8-3K0-1Q-NP-8EPS3lmuxb");
    assertThat(clients.get(3).surname).isEqualTo("цWбш0мVцdТ");
    assertThat(clients.get(3).name).isEqualTo("");
    assertThat(clients.get(3).patronymic).isEqualTo("Sq88КцЪE3ИЗyу");
    assertThat(clients.get(3).gender).isEqualTo("MALE");
    assertThat(clients.get(3).birthDate).isEqualTo("5359-08-16");
    assertThat(clients.get(3).charm).isEqualTo("ЪЧйLGШmАЙГ");
  }

  @Test
  public void parseAndFillData_client_phone() throws Exception {

    prepareTempTables();

    String fileName = "from_cia_2018-02-21-154532-1-300.xml";
    String filePath = String.format("%s/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    ciaMigration = new CiaMigrationCallbackImpl(filePath);
    ciaMigration.parseAndFillData();
    //
    //

    List<CiaPhone> phones = migrationTestDao.get().getClientPhones();

    assertThat(phones).hasSize(19);

    assertThat(phones.get(0).type).isEqualTo("HOME");
    assertThat(phones.get(0).client).isEqualTo("0-B9N-HT-PU-04wolRBPzj");
    assertThat(phones.get(0).number).isEqualTo("+7-878-241-63-94");

    assertThat(phones.get(1).type).isEqualTo("WORK");
    assertThat(phones.get(1).client).isEqualTo("0-B9N-HT-PU-04wolRBPzj");
    assertThat(phones.get(1).number).isEqualTo("+7-385-253-53-56");

    assertThat(phones.get(18).type).isEqualTo("WORK");
    assertThat(phones.get(18).client).isEqualTo("8-3K0-1Q-NP-8EPS3lmuxb");
    assertThat(phones.get(18).number).isEqualTo("+7-653-288-86-15");
  }

  @Test
  public void parseAndFillData_client_address() throws Exception {

    prepareTempTables();

    String fileName = "from_cia_2018-02-21-154532-1-300.xml";
    String filePath = String.format("%s/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    ciaMigration = new CiaMigrationCallbackImpl(filePath);
    ciaMigration.parseAndFillData();
    //
    //

    List<CiaAddress> addresses = migrationTestDao.get().getClientAddresses();

    assertThat(addresses).hasSize(8);

    assertThat(addresses.get(0).type).isEqualTo("FACT");
    assertThat(addresses.get(0).client).isEqualTo("0-B9N-HT-PU-04wolRBPzj");
    assertThat(addresses.get(0).street).isEqualTo("RцВWAаEkMкёнkOзДfжГк");
    assertThat(addresses.get(0).house).isEqualTo("RП");
    assertThat(addresses.get(0).flat).isEqualTo("hИ");

    assertThat(addresses.get(1).type).isEqualTo("REG");
    assertThat(addresses.get(1).client).isEqualTo("0-B9N-HT-PU-04wolRBPzj");
    assertThat(addresses.get(1).street).isEqualTo("ХfАИKлFщсiхДЗрPгWЗdЭ");
    assertThat(addresses.get(1).house).isEqualTo("оz");
    assertThat(addresses.get(1).flat).isEqualTo("РБ");

    assertThat(addresses.get(7).type).isEqualTo("REG");
    assertThat(addresses.get(7).client).isEqualTo("8-3K0-1Q-NP-8EPS3lmuxb");
    assertThat(addresses.get(7).street).isEqualTo("9BЧфдРЯКЪжlЭйгйFХ3ёи");
    assertThat(addresses.get(7).house).isEqualTo("Eщ");
    assertThat(addresses.get(7).flat).isEqualTo("дЙ");
  }


  @Test
  public void checkForValidness_client_surname() throws Exception {

    prepareTempTables();

    String fileName = "from_cia_2018-02-21-154532-1-300.xml";
    String filePath = String.format("%s/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    ciaMigration = new CiaMigrationCallbackImpl(filePath);
    ciaMigration.parseAndFillData();
    ciaMigration.checkForValidness();
    //
    //

    List<Integer> statuses = migrationTestDao.get().getClientsWithoutSurname();

    assertThat(statuses).hasSize(1);

    for (Integer status : statuses) {
      assertThat(status).isEqualTo(2);
    }
  }

  @Test
  public void checkForValidness_client_name() throws Exception {

    prepareTempTables();

    String fileName = "from_cia_2018-02-21-154532-1-300.xml";
    String filePath = String.format("%s/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    ciaMigration = new CiaMigrationCallbackImpl(filePath);
    ciaMigration.parseAndFillData();
    ciaMigration.checkForValidness();
    //
    //

    List<Integer> statuses = migrationTestDao.get().getClientsWithoutName();

    assertThat(statuses).hasSize(1);

    for (Integer status : statuses) {
      assertThat(status).isEqualTo(2);
    }
  }

  @Test
  public void checkForValidness_client_birth_date() throws Exception {

    prepareTempTables();

    String fileName = "from_cia_2018-02-21-154532-1-300.xml";
    String filePath = String.format("%s/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    ciaMigration = new CiaMigrationCallbackImpl(filePath);
    ciaMigration.parseAndFillData();
    ciaMigration.checkForValidness();
    //
    //

    List<Integer> statuses = migrationTestDao.get().getClientsWithoutBirthDate();

    assertThat(statuses).hasSize(1);

    for (Integer status : statuses) {
      assertThat(status).isEqualTo(2);
    }
  }


  @Test
  public void validateAndMigrateData_insert_client() throws Exception {

    prepareTempTables();

    String fileName = "from_cia_2018-02-21-154532-1-300.xml";
    String filePath = String.format("%s/insert_client/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    ciaMigration = new CiaMigrationCallbackImpl(filePath);
    ciaMigration.parseAndFillData();
    ciaMigration.checkForValidness();
    ciaMigration.validateAndMigrateData();
    //
    //

    String migrationId = "0-B9N-ic-PU-04wolRBPzj";

    Client client = migrationTestDao.get().getClientByMigrationId(migrationId);
    String charm = migrationTestDao.get().getCharmById(client.charm).name;

    assertThat(client.surname).isEqualTo("Лолололо");
    assertThat(client.name).isEqualTo("WCИTБЯ7щАо");
    assertThat(client.patronymic).isEqualTo("");
    assertThat(charm).isEqualTo("ЩlВOpФpЪИШ");
    assertThat(client.birthDate).isEqualTo("1995-07-07");
    assertThat(client.gender).isEqualTo("FEMALE");
  }

  @Test
  public void validateAndMigrateData_update_duplicate_client() throws Exception {

    prepareTempTables();

    String fileName = "from_cia_2018-02-21-154532-1-300.xml";
    String filePath = String.format("%s/update_duplicate_client/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    ciaMigration = new CiaMigrationCallbackImpl(filePath);
    ciaMigration.parseAndFillData();
    ciaMigration.checkForValidness();
    ciaMigration.validateAndMigrateData();
    //
    //

    String migrationId = "0-B9N-uc-PU-04wolRBPzj";

    Client client = migrationTestDao.get().getClientByMigrationId(migrationId);
    String charm = migrationTestDao.get().getCharmById(client.charm).name;

    assertThat(client.surname).isEqualTo("Фамилия");
    assertThat(client.name).isEqualTo("Имя");
    assertThat(client.patronymic).isEqualTo("Отчество");
    assertThat(charm).isEqualTo("Характер");
    assertThat(client.birthDate).isEqualTo("2000-10-07");
    assertThat(client.gender).isEqualTo("MALE");
  }


  @Test
  public void validateAndMigrateData_insert_address() throws Exception {

    prepareTempTables();

    String fileName = "from_cia_2018-02-21-154532-1-300.xml";
    String filePath = String.format("%s/insert_address/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    ciaMigration = new CiaMigrationCallbackImpl(filePath);
    ciaMigration.parseAndFillData();
    ciaMigration.checkForValidness();
    ciaMigration.validateAndMigrateData();
    //
    //

    String migrationId = "0-B9N-ia-PU-04wolRBPzj";

    Client client = migrationTestDao.get().getClientByMigrationId(migrationId);
    List<ClientAddress> addresses = migrationTestDao.get().getClientAddressesById(client.id);

    assertThat(addresses.get(0).type).isEqualTo("FACT");
    assertThat(addresses.get(0).street).isEqualTo("Ломоносов");
    assertThat(addresses.get(0).house).isEqualTo("100");
    assertThat(addresses.get(0).flat).isEqualTo("1");

    assertThat(addresses.get(1).type).isEqualTo("REG");
    assertThat(addresses.get(1).street).isEqualTo("Абая");
    assertThat(addresses.get(1).house).isEqualTo("12");
    assertThat(addresses.get(1).flat).isEqualTo("2");
  }

  @Test
  public void validateAndMigrateData_update_duplicate_address() throws Exception {

    prepareTempTables();

    String fileName = "from_cia_2018-02-21-154532-1-300.xml";
    String filePath = String.format("%s/update_duplicate_address/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    ciaMigration = new CiaMigrationCallbackImpl(filePath);
    ciaMigration.parseAndFillData();
    ciaMigration.checkForValidness();
    ciaMigration.validateAndMigrateData();
    //
    //

    String migrationId = "0-B9N-ua-PU-04wolRBPzj";

    Client client = migrationTestDao.get().getClientByMigrationId(migrationId);
    List<ClientAddress> addresses = migrationTestDao.get().getClientAddressesById(client.id);

    assertThat(addresses.get(0).type).isEqualTo("FACT");
    assertThat(addresses.get(0).street).isEqualTo("Новый Факт");
    assertThat(addresses.get(0).house).isEqualTo("1");
    assertThat(addresses.get(0).flat).isEqualTo("4");

    assertThat(addresses.get(1).type).isEqualTo("REG");
    assertThat(addresses.get(1).street).isEqualTo("Новый Рег");
    assertThat(addresses.get(1).house).isEqualTo("1");
    assertThat(addresses.get(1).flat).isEqualTo("4");
  }


  @Test
  public void validateAndMigrateData_insert_phone() throws Exception {

    prepareTempTables();

    String fileName = "from_cia_2018-02-21-154532-1-300.xml";
    String filePath = String.format("%s/insert_phone/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    ciaMigration = new CiaMigrationCallbackImpl(filePath);
    ciaMigration.parseAndFillData();
    ciaMigration.checkForValidness();
    ciaMigration.validateAndMigrateData();
    //
    //

    String migrationId = "0-B9N-ip-PU-04wolRBPzj";

    Client client = migrationTestDao.get().getClientByMigrationId(migrationId);
    List<ClientPhone> phones = migrationTestDao.get().getClientPhonesById(client.id);

    assertThat(phones).hasSize(3);

    assertThat(phones.get(0).type).isEqualTo("WORK");
    assertThat(phones.get(0).number).isEqualTo("+7-385-253-53-56");

    assertThat(phones.get(1).type).isEqualTo("MOBILE");
    assertThat(phones.get(1).number).isEqualTo("+7-418-204-55-17");

    assertThat(phones.get(2).type).isEqualTo("HOME");
    assertThat(phones.get(2).number).isEqualTo("+7-878-241-63-94");
  }

  @Test
  public void validateAndMigrateData_duplicate_phone() throws Exception {

    prepareTempTables();

    String fileName = "from_cia_2018-02-21-154532-1-300.xml";
    String filePath = String.format("%s/duplicate_phone/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    ciaMigration = new CiaMigrationCallbackImpl(filePath);
    ciaMigration.parseAndFillData();
    ciaMigration.checkForValidness();
    ciaMigration.validateAndMigrateData();
    //
    //

    String migrationId = "0-B9N-up-PU-04wolRBPzj";

    Client client = migrationTestDao.get().getClientByMigrationId(migrationId);
    List<ClientPhone> phones = migrationTestDao.get().getClientPhonesById(client.id);

    assertThat(phones).hasSize(2);

    assertThat(phones.get(0).type).isEqualTo("WORK");
    assertThat(phones.get(0).number).isEqualTo("+7-385-253-53-22");

    assertThat(phones.get(1).type).isEqualTo("HOME");
    assertThat(phones.get(1).number).isEqualTo("+7-878-241-63-11");
  }


  @Test
  public void validateAndMigrateData() throws Exception {

    prepareTempTables();

    String fileName = "from_cia_2018-02-21-154532-1-300.xml";
    String filePath = String.format("%s/full/%s", migrationConfig.get().directoryTest(), fileName);

    //
    //
    ciaMigration = new CiaMigrationCallbackImpl(filePath);
    ciaMigration.parseAndFillData();
    ciaMigration.checkForValidness();
    ciaMigration.validateAndMigrateData();
    //
    //

    String migrationId1 = "1-B9N-vv-PU-04wolRBPzj";
    String migrationId2 = "2-B9N-vv-PU-04wolRBPzj";

    Client client1 = migrationTestDao.get().getClientByMigrationId(migrationId1);
    List<ClientAddress> addresses1 = migrationTestDao.get().getClientAddressesById(client1.id);
    List<ClientPhone> phones1 = migrationTestDao.get().getClientPhonesById(client1.id);
    String charm1 = migrationTestDao.get().getCharmById(client1.charm).name;

    assertThat(client1.surname).isEqualTo("Лолололо");
    assertThat(client1.name).isEqualTo("WCИTБЯ7щАо");
    assertThat(client1.patronymic).isEqualTo("");
    assertThat(charm1).isEqualTo("ЩlВOpФpЪИШ");
    assertThat(client1.birthDate).isEqualTo("1995-07-07");
    assertThat(client1.gender).isEqualTo("FEMALE");

    assertThat(addresses1).hasSize(2);

    assertThat(addresses1.get(0).type).isEqualTo("FACT");
    assertThat(addresses1.get(0).street).isEqualTo("RцВWAаEkMкёнkOзДfжГк");
    assertThat(addresses1.get(0).house).isEqualTo("RП");
    assertThat(addresses1.get(0).flat).isEqualTo("hИ");

    assertThat(addresses1.get(1).type).isEqualTo("REG");
    assertThat(addresses1.get(1).street).isEqualTo("ХfАИKлFщсiхДЗрPгWЗdЭ");
    assertThat(addresses1.get(1).house).isEqualTo("оz");
    assertThat(addresses1.get(1).flat).isEqualTo("РБ");

    assertThat(phones1).hasSize(3);

    assertThat(phones1.get(0).type).isEqualTo("WORK");
    assertThat(phones1.get(0).number).isEqualTo("+7-222-253-53-56");

    assertThat(phones1.get(1).type).isEqualTo("MOBILE");
    assertThat(phones1.get(1).number).isEqualTo("+7-333-204-55-17");

    assertThat(phones1.get(2).type).isEqualTo("HOME");
    assertThat(phones1.get(2).number).isEqualTo("+7-111-241-63-94");


    Client client2 = migrationTestDao.get().getClientByMigrationId(migrationId2);
    List<ClientAddress> addresses2 = migrationTestDao.get().getClientAddressesById(client2.id);
    List<ClientPhone> phones2 = migrationTestDao.get().getClientPhonesById(client2.id);
    String charm2 = migrationTestDao.get().getCharmById(client2.charm).name;

    assertThat(client2.surname).isEqualTo("Фамилия");
    assertThat(client2.name).isEqualTo("Имя");
    assertThat(client2.patronymic).isEqualTo("Отчество");
    assertThat(charm2).isEqualTo("Характерный");
    assertThat(client2.birthDate).isEqualTo("1910-10-10");
    assertThat(client2.gender).isEqualTo("FEMALE");

    assertThat(addresses2).hasSize(2);

    assertThat(addresses2.get(0).type).isEqualTo("FACT");
    assertThat(addresses2.get(0).street).isEqualTo("Новый аддрес");
    assertThat(addresses2.get(0).house).isEqualTo("100 дом");
    assertThat(addresses2.get(0).flat).isEqualTo("12");

    assertThat(addresses2.get(1).type).isEqualTo("REG");
    assertThat(addresses2.get(1).street).isEqualTo("Новый аддрес рег");
    assertThat(addresses2.get(1).house).isEqualTo("119");
    assertThat(addresses2.get(1).flat).isEqualTo("90");

    assertThat(phones2).hasSize(1);

    assertThat(phones2.get(0).type).isEqualTo("HOME");
    assertThat(phones2.get(0).number).isEqualTo("+7-111-111-11-11");
  }
}