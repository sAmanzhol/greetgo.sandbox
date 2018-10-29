package kz.greetgo.sandbox.register.configs;

import kz.greetgo.conf.hot.DefaultStrValue;
import kz.greetgo.conf.hot.Description;

@Description("Параметры миграции")
public interface MigrationConfig {

  @Description("Хост для подключения")
  @DefaultStrValue("localhost")
  String ftpHostname();

  @Description("Логин для подключения")
  @DefaultStrValue("ssailaubayev")
  String ftpLogin();

  @Description("Пароль для подключения")
  @DefaultStrValue("111")
  String ftpPassword();

  @Description("Путь к директории где хранится реальные файлы для миграции")
  @DefaultStrValue("migration/real")
  String ftpRealPath();

  @Description("Путь к директории где хранится тестовые файлы для миграции")
  @DefaultStrValue("migration/test")
  String ftpTestPath();
}
