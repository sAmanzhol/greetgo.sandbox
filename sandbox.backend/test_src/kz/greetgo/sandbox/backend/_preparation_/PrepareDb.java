package kz.greetgo.sandbox.backend._preparation_;

import kz.greetgo.sandbox.backend.test.beans.DbPreparation;
import kz.greetgo.sandbox.backend.test.util.ScannerForTests;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static kz.greetgo.sandbox.backend.test.util.DbKind.OPERATIVE;

public class PrepareDb {

  public static void main(String[] args) {
    new PrepareDb().exec();
  }

  private void exec() {
    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
      context.register(ScannerForTests.class);
      context.refresh();

      DbPreparation dbPreparation = context.getBean(DbPreparation.class);

      dbPreparation.prepareFolderD();

      dbPreparation.prepareDbConfig();

      dbPreparation.dropDb(OPERATIVE);

      dbPreparation.createDb(OPERATIVE);

      dbPreparation.applyLiquibase(OPERATIVE);
    }
  }
}
