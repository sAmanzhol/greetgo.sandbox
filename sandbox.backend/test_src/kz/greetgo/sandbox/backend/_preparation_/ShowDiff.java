package kz.greetgo.sandbox.backend._preparation_;

import kz.greetgo.sandbox.backend.test.beans.DbPreparation;
import kz.greetgo.sandbox.backend.test.util.ScannerForTests;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ShowDiff {
  public static void main(String[] args) {

    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
      context.register(ScannerForTests.class);
      context.refresh();

      DbPreparation dbPreparation = context.getBean(DbPreparation.class);

      dbPreparation.prepareFolderD();

      dbPreparation.prepareDbConfig();

      dbPreparation.dropDb();

      dbPreparation.createDb();

      dbPreparation.applyLiquibase();
    }
  }
}
