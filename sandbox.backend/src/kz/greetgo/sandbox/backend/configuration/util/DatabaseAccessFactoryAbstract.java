package kz.greetgo.sandbox.backend.configuration.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import kz.greetgo.sandbox.backend.configuration.util.my_batis.CustomBooleanTypeHandler;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.util.concurrent.atomic.AtomicReference;

public abstract class DatabaseAccessFactoryAbstract {

  protected final JdbcTemplate jdbcTemplate = new JdbcTemplate();

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
    HikariConfig config = createHikariConfig();

    HikariDataSource old = hikariDataSourceReference.getAndSet(new HikariDataSource(config));
    if (old != null) {
      old.close();
    }
  }

  protected abstract HikariConfig createHikariConfig();

  protected final DataSourceTransactionManager txManager = new DataSourceTransactionManager();

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

  protected SqlSessionFactory sqlSessionFactory;

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
