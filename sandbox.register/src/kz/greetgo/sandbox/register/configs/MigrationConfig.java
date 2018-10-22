package kz.greetgo.sandbox.register.configs;

import kz.greetgo.conf.hot.DefaultStrValue;
import kz.greetgo.conf.hot.Description;

@Description("Параметры миграции и бд")
public interface MigrationConfig {

  @Description("Временная директория миграционных файлов")
  @DefaultStrValue("/home/ssailaubayev/migration")
  String directory();

  @Description("Временная директория миграционных файлов для тестов")
  @DefaultStrValue("/home/ssailaubayev/migration/test")
  String directoryTest();


  @Description("URL доступа к БД")
  @DefaultStrValue("jdbc:postgresql://localhost/ssailaubayev_sandbox")
  String dbUrl();

  @Description("Пользователь для доступа к БД")
  @DefaultStrValue("ssailaubayev_sandbox")
  String dbUsername();

  @Description("Пароль для доступа к БД")
  @DefaultStrValue("111")
  String dbPassword();
}
