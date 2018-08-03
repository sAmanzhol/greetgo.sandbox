package ka.greetgo.db.session;

import ka.greetgo.db.session.jdbc.SelectOneStrOrNull;
import ka.greetgo.db.session.jdbc.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractSessionStorageAdapter implements SessionStorage {

  protected final SessionStorageBuilder.Structure structure;

  public AbstractSessionStorageAdapter(SessionStorageBuilder.Structure structure) {
    this.structure = structure;
    init();
  }

  private void init() {
    try {
      structure.jdbc.execute(new SelectOneStrOrNull(checkTableExistsSql()));
    } catch (RuntimeException e) {
      if (e.getCause() instanceof SQLException) {
        SQLException sqlException = (SQLException) e.getCause();
        if (isExceptionAboutTableDoesNotExists(sqlException)) {
          createSessionTable();
          return;
        }
        throw new RuntimeException("SQL State = " + sqlException.getSQLState(), sqlException);
      }
      throw e;
    }
  }

  private void createSessionTable() {
    structure.jdbc.execute(new Update(createSessionTableSql()));
  }

  protected abstract String createSessionTableSql();

  protected abstract boolean isExceptionAboutTableDoesNotExists(SQLException sqlException);

  protected abstract String checkTableExistsSql();

  protected abstract String insertSessionSql(List<Object> sqlParams, SessionIdentity identity, Object sessionData);

  @Override
  public void insertSession(SessionIdentity identity, Object sessionData) {
    List<Object> sqlParams = new ArrayList<>();
    String sql = insertSessionSql(sqlParams, identity, sessionData);
    structure.jdbc.execute(new Update(sql, sqlParams));
  }

  @Override
  public boolean zeroSessionAge(String sessionId) {
    return false;
  }

  @Override
  public int removeSessionsOlderThan(int ageInHours) {
    return 0;
  }

  @Override
  public Object loadSessionData(String sessionId) {
    return null;
  }

  @Override
  public String loadToken(String sessionId) {
    return null;
  }

  @Override
  public Date loadInsertedAt(String sessionId) {
    return null;
  }

  @Override
  public Date loadLastTouchedAt(String sessionId) {
    return null;
  }

}
