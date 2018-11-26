package kz.greetgo.sandbox.backend.configuration.beans;

import com.zaxxer.hikari.HikariConfig;
import kz.greetgo.sandbox.backend.config.DbConfig;
import kz.greetgo.sandbox.backend.configuration.util.DatabaseAccessFactoryAbstract;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

@Component
public class MasterDatabaseAccessFactory extends DatabaseAccessFactoryAbstract {

  @Autowired
  private DbConfig dbConfig;

  @Override
  protected HikariConfig createHikariConfig() {

    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(dbConfig.url());
    config.setUsername(dbConfig.username());
    config.setPassword(dbConfig.password());

    return config;
  }

  @Bean("masterJdbcTemplate")
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Bean("masterTxManager")
  public DataSourceTransactionManager getTxManager() {
    return txManager;
  }

  @Bean("masterSqlSessionFactory")
  public SqlSessionFactory getSqlSessionFactory() {
    return sqlSessionFactory;
  }
}
