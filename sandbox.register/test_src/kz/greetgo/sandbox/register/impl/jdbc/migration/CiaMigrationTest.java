package kz.greetgo.sandbox.register.impl.jdbc.migration;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.register.configs.MigrationConfig;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaAddress;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaClient;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaPhone;
import kz.greetgo.sandbox.register.test.dao.MigrationTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.testng.Assert.*;

import static org.fest.assertions.api.Assertions.assertThat;

@SuppressWarnings("WeakerAccess")
public class CiaMigrationTest extends ParentTestNg {

  public BeanGetter<MigrationConfig> migrationConfig;
  public BeanGetter<MigrationTestDao> migrationTestDao;

  private CiaMigrationCallbackImpl ciaMigration;

  private void parseAndFillData() throws Exception {
    ciaMigration = new CiaMigrationCallbackImpl("");
    ciaMigration.dropTemplateTables();
    ciaMigration.createTempTables();

    File migrationFolder = new File(migrationConfig.get().directoryTest());
    ArrayList<File> ciaFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(migrationFolder.listFiles((file, name) -> name.toLowerCase().endsWith(".xml")))));

    while (ciaFiles.size() > 0) {
      //
      CiaMigrationCallbackImpl
      ciaMigration = new CiaMigrationCallbackImpl(ciaFiles.get(0).getPath());
      ciaMigration.parseAndFillData();
      //
      //
      ciaFiles.remove(0);
    }
  }

  private void checkValidity() throws Exception {

    this.parseAndFillData();

    //
    //
    ciaMigration.checkForValidness();
    //
    //
  }

  @Test
  public void parseAndFillData_client() throws Exception {

    this.parseAndFillData();

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

    this.parseAndFillData();

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
    this.parseAndFillData();

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
    this.checkValidity();

    List<Integer> statuses = migrationTestDao.get().getClientsWithoutSurname();

    assertThat(statuses).hasSize(1);

    for (Integer status : statuses) {
      assertThat(status).isEqualTo(2);
    }
  }

  @Test
  public void checkForValidness_client_name() throws Exception {
    this.checkValidity();

    List<Integer> statuses = migrationTestDao.get().getClientsWithoutName();

    assertThat(statuses).hasSize(1);

    for (Integer status : statuses) {
      assertThat(status).isEqualTo(2);
    }
  }

  @Test
  public void checkForValidness_client_birth_date() throws Exception {
    this.checkValidity();

    List<Integer> statuses = migrationTestDao.get().getClientsWithoutBirthDate();

    assertThat(statuses).hasSize(1);

    for (Integer status : statuses) {
      assertThat(status).isEqualTo(2);
    }
  }

  @Test
  public void validateAndMigrateData() {
    assertThat(1).isEqualTo(2);
  }

  @Test
  public void validateAndMigrateData_duplicate_client() {
    assertThat(1).isEqualTo(2);
  }

  @Test
  public void validateAndMigrateData_duplicate_client_phone() {
    assertThat(1).isEqualTo(2);
  }

  @Test
  public void validateAndMigrateData_duplicate_client_address() {
    assertThat(1).isEqualTo(2);
  }

  @Test
  public void validateAndMigrateData_insert_client() {
    assertThat(1).isEqualTo(2);
  }

  @Test
  public void validateAndMigrateData_update_client() {
    assertThat(1).isEqualTo(2);
  }

  @Test
  public void validateAndMigrateData_insert_address() {
    assertThat(1).isEqualTo(2);
  }

  @Test
  public void validateAndMigrateData_update_address() {
    assertThat(1).isEqualTo(2);
  }

  @Test
  public void migrate() {
    assertThat(1).isEqualTo(2);
  }
}