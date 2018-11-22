package kz.greetgo.sandbox.backend.config;

import kz.greetgo.conf.hot.DefaultStrValue;
import kz.greetgo.conf.hot.Description;

@Description("Parameters to access to DB")
public interface DbConfig {

  @Description("Jdbc URL to access to DB")
  @DefaultStrValue("jdbc:postgres:host:5432/db_name")
  String url();

  @Description("Username to access to DB")
  @DefaultStrValue("Some_User")
  String username();

  @Description("Password for username to access to DB")
  @DefaultStrValue("Secret")
  String password();

}
