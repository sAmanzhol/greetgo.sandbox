///MODIFY replace sandbox PROJECT_NAME
package kz.greetgo.sandbox.register.util;

import java.io.File;

public class App {
  public static String appDir() {
///MODIFY replace sandbox PROJECT_NAME
    return System.getProperty("user.home") + "/sandbox.d";
  }

  public static String securityDir() {
    return appDir() + "/security";
  }

  public static File do_not_run_liquibase_on_deploy_war() {
    return new File(appDir() + "/do_not_run_liquibase_on_deploy_war");
  }
}
