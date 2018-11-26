package kz.greetgo.sandbox.backend.test.beans;

import kz.greetgo.conf.hot.DefaultStrValue;
import kz.greetgo.sandbox.backend.config.DbConfig;
import kz.greetgo.sandbox.backend.configuration.beans.AppConfigFactory;
import kz.greetgo.sandbox.backend.configuration.beans.MasterDatabaseAccessFactory;
import kz.greetgo.sandbox.backend.configuration.beans.LiquibaseManager;
import kz.greetgo.sandbox.backend.configuration.logging.LOG;
import kz.greetgo.sandbox.backend.configuration.util.App;
import kz.greetgo.sandbox.backend.test.util.ConnectParams;
import kz.greetgo.sandbox.backend.test.util.DbKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kz.greetgo.conf.sys_params.SysParams.pgAdminUrl;
import static kz.greetgo.sandbox.backend.test.util.ConnectParamsUtil.replaceParameterOrReturnSame;
import static kz.greetgo.sandbox.backend.test.util.ConnectionKind.ADMIN;
import static kz.greetgo.sandbox.backend.test.util.DbUrlUtils.changeUrlDbName;
import static kz.greetgo.sandbox.backend.test.util.SqlErrorType.DB_ABSENT;
import static kz.greetgo.sandbox.backend.test.util.SqlErrorType.ROLE_ABSENT;

@Component
public class DbPreparation extends DbPreparationParent {

  @Override
  protected LOG log() {
    return LOG.byClass(getClass());
  }

  public void prepareFolderD() throws Exception {
    log().info(() -> "Prepare folder D");

    App.do_not_run_liquibase().toFile().getParentFile().mkdirs();
    App.do_not_run_liquibase().toFile().createNewFile();
  }

  @Autowired
  private AppConfigFactory appConfigFactory;

  @Autowired
  private MasterDatabaseAccessFactory masterDatabaseAccessFactory;

  public void prepareDbConfig() throws Exception {
    log().info(() -> "Prepare DB Config");

    String defaultUrlValue = DbConfig.class.getMethod("url").getAnnotation(DefaultStrValue.class).value();
    if (defaultUrlValue.equals(dbConfig.url())) {

      Map<String, String> params = new HashMap<>();

      params.put("url", changeUrlDbName(pgAdminUrl(), System.getProperty("user.name") + "_" + App.name()));
      params.put("username", System.getProperty("user.name") + "_" + App.name());
      params.put("password", App.name() + "-111");

      File file = appConfigFactory.storageFileFor(DbConfig.class);

      List<String> configLines = Files.readAllLines(file.toPath());

      Files.write(file.toPath(), configLines
          .stream()
          .map(line -> replaceParameterOrReturnSame(line, params))
          .collect(Collectors.toList()));

      appConfigFactory.reset();
      masterDatabaseAccessFactory.reset();
    }
  }

  public void dropDb(DbKind kind) {
    log().info(() -> "Drop " + kind + " DB");

    masterDatabaseAccessFactory.closeDataSource();

    ConnectParams params = getConnectParams(kind.connection());

    exec(ADMIN, "drop database " + params.dbName(), DB_ABSENT);
    exec(ADMIN, "drop user " + params.username(), ROLE_ABSENT);
  }

  public void createDb(DbKind kind) {
    log().info(() -> "Create " + kind + " DB");

    ConnectParams params = getConnectParams(kind.connection());

    exec(ADMIN, "create user " + params.username() + " with password '" + params.password() + "'");
    exec(ADMIN, "create database " + params.dbName() + " with owner " + params.username());

    masterDatabaseAccessFactory.reset();
  }

  @Autowired
  private LiquibaseManager liquibaseManager;

  public void applyLiquibaseToOperative() throws Exception {
    log().info(() -> "Apply Liquibase to " + DbKind.MASTER + " DB");

    liquibaseManager.apply();
  }

  public void applyCurrentStructureTo(DbKind kind) {
    log().info(() -> "Apply Current Structure to " + kind + " DB");
  }

  /**
   * TARGET apply DIFF ==> SOURCE
   *
   * @param target target DB kind
   * @param source source DB kind
   */
  public void generateDiffSql(DbKind target, DbKind source) {
    log().info(() -> "Generating diff to apply to " + target + " DB to be equals to " + source + " DB");
  }
}
