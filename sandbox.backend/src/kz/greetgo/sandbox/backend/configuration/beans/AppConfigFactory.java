package kz.greetgo.sandbox.backend.configuration.beans;

import kz.greetgo.sandbox.backend.config.DbConfig;
import kz.greetgo.sandbox.backend.configuration.util.LocalConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AppConfigFactory extends LocalConfigFactory {

  @Bean
  public DbConfig createDbConfig() {
    return createConfig(DbConfig.class);
  }

}
