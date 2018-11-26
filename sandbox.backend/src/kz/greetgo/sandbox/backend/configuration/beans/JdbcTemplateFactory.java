package kz.greetgo.sandbox.backend.configuration.beans;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import kz.greetgo.sandbox.backend.config.DbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class JdbcTemplateFactory {

  @Autowired
  private DbConfig dbConfig;

  private HikariDataSource dataSource = null;

  private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

  @Bean
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @PreDestroy
  public void closeDataSource() {
    HikariDataSource ds = dataSource;
    dataSource = null;
    if (ds != null) {
      ds.close();
    }
  }

  @PostConstruct
  public void reset() {
    closeDataSource();

    createDataSource();

    jdbcTemplate.setDataSource(dataSource);
  }

  private void createDataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(dbConfig.url());
    config.setUsername(dbConfig.username());
    config.setPassword(dbConfig.password());

    dataSource = new HikariDataSource(config);

    dataSourceTransactionManager.setDataSource(dataSource);
  }

  private final DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();

  @Bean("txManager")
  public DataSourceTransactionManager txManager() {
    return dataSourceTransactionManager;
  }
}
