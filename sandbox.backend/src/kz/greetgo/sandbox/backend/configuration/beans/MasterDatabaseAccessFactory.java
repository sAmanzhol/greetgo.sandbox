package kz.greetgo.sandbox.backend.configuration.beans;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import kz.greetgo.sandbox.backend.config.DbConfig;
import kz.greetgo.sandbox.backend.configuration.util.DelegatingDataSource;
import kz.greetgo.sandbox.backend.configuration.util.my_batis.CustomBooleanTypeHandler;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class MasterDatabaseAccessFactory {

  @Autowired
  private DbConfig dbConfig;

  private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

  @Bean("masterJdbcTemplate")
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @PreDestroy
  public void closeDataSource() {
    HikariDataSource ds = hikariDataSourceReference.get();
    if (ds != null) {
      ds.close();
    }
  }

  private final AtomicReference<HikariDataSource> hikariDataSourceReference = new AtomicReference<>(null);
  private final DataSource dataSource = new DelegatingDataSource(hikariDataSourceReference);

  private void recreateDataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(dbConfig.url());
    config.setUsername(dbConfig.username());
    config.setPassword(dbConfig.password());

    HikariDataSource old = hikariDataSourceReference.getAndSet(new HikariDataSource(config));
    if (old != null) {
      old.close();
    }
  }

  private final DataSourceTransactionManager txManager = new DataSourceTransactionManager();

  @Bean("masterTxManager")
  public DataSourceTransactionManager getTxManager() {
    return txManager;
  }

  public void reset() {
    recreateDataSource();
  }

  @PostConstruct
  private void initialize() {
    recreateDataSource();

    jdbcTemplate.setDataSource(dataSource);
    txManager.setDataSource(dataSource);

    prepareMyBatisSqlSessionFactory();
  }

  private SqlSessionFactory sqlSessionFactory;

  @Bean("masterSqlSessionFactory")
  public SqlSessionFactory getSqlSessionFactory() {
    return sqlSessionFactory;
  }

  private void prepareMyBatisSqlSessionFactory() {
    SpringManagedTransactionFactory transactionFactory = new SpringManagedTransactionFactory();

    Environment environment = new Environment("MASTER", transactionFactory, dataSource);

    Configuration configuration = new Configuration(environment);
    configuration.setJdbcTypeForNull(JdbcType.NULL);
    //configuration.setLogImpl(Log4jImpl.class);

    configuration.setMapUnderscoreToCamelCase(true);

    TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

    typeHandlerRegistry.register(Boolean.class, new CustomBooleanTypeHandler());
    typeHandlerRegistry.register(boolean.class, new CustomBooleanTypeHandler());
    typeHandlerRegistry.register(JdbcType.BOOLEAN, new CustomBooleanTypeHandler());

    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

    sqlSessionFactory = sqlSessionFactoryBuilder.build(configuration);
  }
}
