package kz.greetgo.sandbox.backend._preparation_;

import kz.greetgo.sandbox.backend.test.beans.DbPreparation;
import kz.greetgo.sandbox.backend.test.util.ScannerForTests;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static kz.greetgo.sandbox.backend.test.util.DbKind.DIFF;
import static kz.greetgo.sandbox.backend.test.util.DbKind.MASTER;

public class ShowDiff {
  public static void main(String[] args) throws Exception {

    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
      context.register(ScannerForTests.class);
      context.refresh();

      DbPreparation dbPreparation = context.getBean(DbPreparation.class);

      dbPreparation.prepareFolderD();

      dbPreparation.prepareDbConfig();

      dbPreparation.dropDb(DIFF);
      dbPreparation.dropDb(MASTER);

      dbPreparation.createDb(DIFF);
      dbPreparation.createDb(MASTER);

      dbPreparation.applyLiquibaseToOperative();
      dbPreparation.applyCurrentStructureTo(DIFF);

      dbPreparation.generateDiffSql(MASTER, DIFF);
    }
  }
}
