package kz.greetgo.sandbox.backend.configuration.beans;

import kz.greetgo.db.DbType;
import kz.greetgo.db.nf36.core.Nf36Upserter;
import kz.greetgo.db.nf36.core.SequenceNext;
import kz.greetgo.sandbox.backend.configuration.util.JdbcTemplateBridge;
import kz.greetgo.sandbox.backend.configuration.util.LocalSqlLogAcceptor;
import kz.greetgo.sandbox.backend.nf36.impl.AbstractDbUpserter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static kz.greetgo.db.nf36.Nf36Builder.newNf36Builder;

@Component
public class DbUpserterBridge extends AbstractDbUpserter {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  protected Nf36Upserter createUpserter() {
    return newNf36Builder()
        .upserter()
        .database(DbType.Postgres)
        .setJdbc(new JdbcTemplateBridge(jdbcTemplate))
        .setLogAcceptor(LocalSqlLogAcceptor.get())
        .build()
        ;
  }

  @Override
  protected SequenceNext getSequenceNext() {
    return newNf36Builder()
        .sequenceNext()
        .database(DbType.Postgres)
        .setJdbc(new JdbcTemplateBridge(jdbcTemplate))
        .setLogAcceptor(LocalSqlLogAcceptor.get())
        .build()
        ;
  }
}
