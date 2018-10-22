package kz.greetgo.sandbox.register.impl.jdbc.migration;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.register.configs.MigrationConfig;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.FrsAccount;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.FrsTransaction;
import kz.greetgo.sandbox.register.test.dao.MigrationTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.sandbox.register.util.JdbcSandbox;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.fest.assertions.api.Assertions.assertThat;

@SuppressWarnings("WeakerAccess")
public class FrsMigrationTest extends ParentTestNg {

  public BeanGetter<MigrationConfig> migrationConfig;
  public BeanGetter<MigrationTestDao> migrationTestDao;

  private FrsMigrationCallbackImpl frsMigration;

  private void parseAndFillData() throws Exception {
    frsMigration = new FrsMigrationCallbackImpl("");
    frsMigration.dropTemplateTables();
    frsMigration.createTempTables();

    File migrationFolder = new File(migrationConfig.get().directoryTest());
    ArrayList<File> frsFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(migrationFolder.listFiles((file, name) -> name.toLowerCase().endsWith(".json_row")))));

    while (frsFiles.size() > 0) {
      //
      //
      frsMigration = new FrsMigrationCallbackImpl(frsFiles.get(0).getPath());
      frsMigration.parseAndFillData();
      //
      //
      frsFiles.remove(0);
    }
  }

  private void checkValidity() throws Exception {

    this.parseAndFillData();

    //
    //
    frsMigration.checkForValidness();
    //
    //
  }

  @Test
  public void parseAndFillData_account() throws Exception {

    this.parseAndFillData();

    List<FrsAccount> accounts = migrationTestDao.get().getAccounts();

    assertThat(accounts).hasSize(3);

    assertThat(accounts.get(0).client).isEqualTo("1-A69-QA-PJ-G6hRzbEf2W");
    assertThat(accounts.get(0).accountNumber).isEqualTo("19382KZ865-20725-98987-8267359");
    assertThat(accounts.get(0).registeredAt).isEqualTo("2001-02-21T15:51:14.991");

    assertThat(accounts.get(1).client).isEqualTo("");
    assertThat(accounts.get(1).accountNumber).isEqualTo("22382KZ865");
    assertThat(accounts.get(1).registeredAt).isEqualTo("2001-02-21T15:51:14.992");

    assertThat(accounts.get(2).client).isEqualTo("10-A69-QA-PJ-G6hRzbEf2W");
    assertThat(accounts.get(2).accountNumber).isEqualTo("19382KZ865-20725-11111-8267359");
    assertThat(accounts.get(2).registeredAt).isEqualTo("2001-02-21T15:66:11.991");
  }

  @Test
  public void parseAndFillData_account_transaction() throws Exception {

    this.parseAndFillData();

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

    this.checkValidity();

    List<Integer> statuses = migrationTestDao.get().getAccountsWithoutClients();

    assertThat(statuses).hasSize(1);

    for (Integer status : statuses) {
      assertThat(status).isEqualTo(2);
    }
  }

  @Test
  public void checkForValidness_account_transaction_account_number() throws Exception {

    this.checkValidity();

    List<Integer> statuses = migrationTestDao.get().getTransactionsWithoutAccounts();

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
  public void validateAndMigrateData_duplicate_account() {
    assertThat(1).isEqualTo(2);
  }

  @Test
  public void validateAndMigrateData_duplicate_transaction() {
    assertThat(1).isEqualTo(2);
  }

  @Test
  public void migrate() {
    assertThat(1).isEqualTo(2);
  }
}