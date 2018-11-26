package kz.greetgo.sandbox.backend._preparation_;

import kz.greetgo.sandbox.backend.test.beans.DbPreparation;
import kz.greetgo.sandbox.backend.test.beans.TestDataLoader;
import kz.greetgo.sandbox.backend.test.util.ScannerForTests;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static kz.greetgo.sandbox.backend.test.util.DbKind.MASTER;

public class PrepareDb_and_LoadTestData {

  public static void main(String[] args) throws Exception {
    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
      context.register(ScannerForTests.class);
      context.refresh();

      DbPreparation dbPreparation = context.getBean(DbPreparation.class);

      dbPreparation.prepareFolderD();

      dbPreparation.prepareDbConfig();

      dbPreparation.dropDb(MASTER);

      dbPreparation.createDb(MASTER);

      dbPreparation.applyLiquibaseToOperative();

      context.getBean(TestDataLoader.class).load();
    }
  }

}
