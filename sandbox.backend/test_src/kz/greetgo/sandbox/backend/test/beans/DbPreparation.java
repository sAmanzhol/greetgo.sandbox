package kz.greetgo.sandbox.backend.test.beans;

import kz.greetgo.sandbox.backend.configuration.logging.LOG;
import kz.greetgo.sandbox.backend.test.util.DbKind;
import org.springframework.stereotype.Component;

@Component
public class DbPreparation {

  private final LOG log = LOG.byClass(getClass());

  public void prepareFolderD() {
    log.info(() -> "Prepare folder D");
  }

  public void prepareDbConfig() {
    log.info(() -> "Prepare DB Config");
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
}
