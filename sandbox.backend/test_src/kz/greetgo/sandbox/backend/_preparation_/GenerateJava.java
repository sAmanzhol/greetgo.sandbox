package kz.greetgo.sandbox.backend._preparation_;

import kz.greetgo.sandbox.backend.test.beans.Nf36Generators;
import kz.greetgo.sandbox.backend.test.util.ScannerForTests;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;

public class GenerateJava {
  public static void main(String[] args) throws Exception {
    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
      context.register(ScannerForTests.class);
      context.refresh();

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

//      context.getBean(Nf36Generators.class)
//          .generateTo(Modules
//              .backend()
//              .resolve("src_resources")
//              .resolve("liquibase")
//              .resolve("difference-" + sdf.format(new Date()) + ".sql")
//          );

      context.getBean(Nf36Generators.class).generateJava();
    }
  }
}
