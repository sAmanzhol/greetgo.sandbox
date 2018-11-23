package kz.greetgo.sandbox.backend.configuration.beans;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import kz.greetgo.sandbox.backend.config.DbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class JdbcTemplateFactory {

  @Autowired
  private DbConfig dbConfig;

  private HikariDataSource dataSource = null;

  @Bean
  public JdbcTemplate getJdbcTemplate() {

    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(dbConfig.url());
    config.setUsername(dbConfig.username());
    config.setPassword(dbConfig.password());

    dataSource = new HikariDataSource(config);

    return new JdbcTemplate(dataSource);
  }

  @PreDestroy
  private void closeDataSource() {
    HikariDataSource ds = dataSource;
    if (ds != null) {
      ds.close();
    }
  }
}
