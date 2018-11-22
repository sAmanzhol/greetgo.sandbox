package kz.greetgo.sandbox.backend.test.beans;

import kz.greetgo.conf.hot.DefaultStrValue;
import kz.greetgo.sandbox.backend.config.DbConfig;
import kz.greetgo.sandbox.backend.configuration.beans.AppConfigFactory;
import kz.greetgo.sandbox.backend.configuration.logging.LOG;
import kz.greetgo.sandbox.backend.configuration.util.App;
import kz.greetgo.sandbox.backend.test.util.ConnectParams;
import kz.greetgo.sandbox.backend.test.util.ConnectionKind;
import kz.greetgo.sandbox.backend.test.util.DbKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kz.greetgo.conf.sys_params.SysParams.pgAdminUrl;
import static kz.greetgo.sandbox.backend.test.util.ConnectParamsUtil.extractConnectParams;
import static kz.greetgo.sandbox.backend.test.util.DbUrlUtils.changeUrlDbName;

@Component
public class DbPreparation {

  private final LOG log = LOG.byClass(getClass());

  public void prepareFolderD() throws Exception {
    log.info(() -> "Prepare folder D");

    App.do_not_run_liquibase().toFile().getParentFile().mkdirs();
    App.do_not_run_liquibase().toFile().createNewFile();
  }

  @Autowired
  private AppConfigFactory appConfigFactory;

  @Autowired
  private DbConfig dbConfig;

  public void prepareDbConfig() throws Exception {
    log.info(() -> "Prepare DB Config");

    String defaultUrlValue = DbConfig.class.getMethod("url").getAnnotation(DefaultStrValue.class).value();
    if (defaultUrlValue.equals(dbConfig.url())) {

      Map<String, String> params = new HashMap<>();

      params.put("url", changeUrlDbName(pgAdminUrl(), System.getProperty("user.name") + "_" + App.name()));
      params.put("username", App.name());
      params.put("password", App.name() + "111");

      File file = appConfigFactory.storageFileFor(DbConfig.class);

      List<String> configLines = Files.readAllLines(file.toPath());

      Files.write(file.toPath(), configLines
          .stream()
          .map(line -> replaceParameterOrReturnSame(line, params))
          .collect(Collectors.toList()));

    }
  }

  private static String replaceParameterOrReturnSame(String line, Map<String, String> params) {
    int i = line.indexOf("=");
    if (i < 0) {
      return line;
    }

    String key = line.substring(0, i).trim();

    String newValue = params.get(key);
    if (newValue != null) {
      return key + "=" + newValue;
    }

    return line;
  }

  public void dropDb(DbKind kind) {
    log.info(() -> "Drop " + kind + " DB");
  }

  public void createDb(DbKind kind) {
    log.info(() -> "Create " + kind + " DB");
  }

  public void applyLiquibase(DbKind kind) {
    log.info(() -> "Apply Liquibase to " + kind + " DB");
  }

  public void applyCurrentStructure(DbKind kind) {
    log.info(() -> "Apply Current Structure to " + kind + " DB");
  }

  /**
   * TARGET apply DIFF ==> SOURCE
   *
   * @param target target DB kind
   * @param source source DB kind
   */
  public void generateDiffSql(DbKind target, DbKind source) {
    log.info(() -> "Generating diff to apply to " + target + " DB to be equals to " + source + " DB");
  }

  private Connection connectTo(ConnectionKind connectionKind) throws Exception {
    Class.forName("org.postgresql.Driver");
    ConnectParams params = extractConnectParams(dbConfig, connectionKind);
    return DriverManager.getConnection(params.url(), params.username(), params.password());
  }
}
