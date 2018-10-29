package kz.greetgo.sandbox.register.beans.all;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.register.configs.DbConfig;
import kz.greetgo.sandbox.register.configs.MigrationConfig;
import kz.greetgo.sandbox.register.util.LocalConfigFactory;

@Bean
public class AllConfigFactory extends LocalConfigFactory {

  @Bean
  public DbConfig createPostgresDbConfig() {
    return createConfig(DbConfig.class);
  }

  @Bean
  public MigrationConfig createMigrationConfig() {
    return createConfig(MigrationConfig.class);
  }
}
