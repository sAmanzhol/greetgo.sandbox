package kz.greetgo.sandbox.register.configs;

import kz.greetgo.conf.hot.DefaultStrValue;
import kz.greetgo.conf.hot.Description;

@Description("Параметры миграции")
public interface MigrationConfig {

  @Description("Временная директория миграционных файлов")
  @DefaultStrValue("/home/ssailaubayev/migration")
  String directory();
}
