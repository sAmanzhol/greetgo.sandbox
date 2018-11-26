package kz.greetgo.sandbox.backend.configuration.util;

import kz.greetgo.db.nf36.core.SqlLogAcceptor;
import kz.greetgo.db.nf36.model.SqlLog;
import kz.greetgo.sandbox.backend.configuration.logging.LOG;

public class LocalSqlLogAcceptor implements SqlLogAcceptor {

  private LocalSqlLogAcceptor() {}

  private static final LocalSqlLogAcceptor instance = new LocalSqlLogAcceptor();

  public static LocalSqlLogAcceptor get() {
    return instance;
  }

  private final LOG log = LOG.byClass(getClass());

  @Override
  public boolean isTraceEnabled() {
    return log.isTraceEnabled();
  }

  @Override
  public boolean isErrorEnabled() {
    return true;
  }

  @Override
  public void accept(SqlLog sqlLog) {
    log.trace(() -> sqlLog.toStr(true, true, true));
  }
}
