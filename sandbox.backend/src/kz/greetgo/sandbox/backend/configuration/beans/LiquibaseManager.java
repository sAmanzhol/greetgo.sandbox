package kz.greetgo.sandbox.backend.configuration.beans;

import kz.greetgo.sandbox.backend.config.DbConfig;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class LiquibaseManager {

  @Autowired
  private DbConfig dbConfig;

  public void apply() throws Exception {

    Class.forName("org.postgresql.Driver");

    try (Connection connection = DriverManager.getConnection(dbConfig.url(), dbConfig.username(), dbConfig.password())) {


      Database database = new PostgresDatabase();

      database.setConnection(new JdbcConnection(connection));

      Liquibase liquibase = new Liquibase(
          "liquibase/changelog-master.xml",
          new ClassLoaderResourceAccessor(),
          database
      );

      liquibase.update("");
    }

  }

}
