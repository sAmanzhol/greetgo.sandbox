package kz.greetgo.sandbox.register.configs;


import kz.greetgo.conf.hot.DefaultStrValue;
import kz.greetgo.conf.hot.Description;

@Description("Параметры доступа к БД (используется только БД Postgresql)")
public interface DbConfig {

  @Description("URL доступа к БД")
  @DefaultStrValue("jdbc:postgresql://localhost/ssailaubayev_sandbox")
  String url();

  @Description("Пользователь для доступа к БД")
  @DefaultStrValue("ssailaubayev_sandbox")
  String username();

  @Description("Пароль для доступа к БД")
  @DefaultStrValue("111")
  String password();
}
