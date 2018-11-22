package kz.greetgo.sandbox.backend.configuration.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

  public static String name() {
    return "sandbox";
  }

  public static Path folderD() {
    return Paths.get(System.getProperty("user.home"), "sandbox.d");
  }

  public static Path do_not_run_liquibase() {
    return folderD().resolve("do_not_run_liquibase");
  }
}
