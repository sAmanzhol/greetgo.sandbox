package ka.greetgo.db.session;

import ka.greetgo.db.session.jdbc.SelectBytesOrNull;
import ka.greetgo.db.session.jdbc.SelectDateOrNull;
import ka.greetgo.db.session.jdbc.SelectStrOrNull;
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
      structure.jdbc.execute(new SelectStrOrNull(checkTableExistsSql()));
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
    List<Object> sqlParams = new ArrayList<>();
    String sql = zeroSessionAgeSql(sqlParams, sessionId);
    return structure.jdbc.execute(new Update(sql, sqlParams)) > 0;
  }

  protected abstract String zeroSessionAgeSql(List<Object> sqlParams, String sessionId);

  @Override
  public <T> T loadSessionData(String sessionId) {
    List<Object> sqlParams = new ArrayList<>();
    String sql = loadSessionDataSql(sqlParams, sessionId);
    byte[] bytes = structure.jdbc.execute(new SelectBytesOrNull(sql, sqlParams));
    return Serializer.deserialize(bytes);
  }

  protected abstract String loadSessionDataSql(List<Object> sqlParams, String sessionId);

  @Override
  public String loadToken(String sessionId) {
    List<Object> sqlParams = new ArrayList<>();
    String sql = loadTokenSql(sqlParams, sessionId);
    return structure.jdbc.execute(new SelectStrOrNull(sql, sqlParams));
  }

  protected abstract String loadTokenSql(List<Object> sqlParams, String sessionId);

  @Override
  public Date loadInsertedAt(String sessionId) {
    List<Object> sqlParams = new ArrayList<>();
    String sql = loadInsertedAtSql(sqlParams, sessionId);
    return structure.jdbc.execute(new SelectDateOrNull(sql, sqlParams));
  }

  protected abstract String loadInsertedAtSql(List<Object> sqlParams, String sessionId);

  @Override
  public Date loadLastTouchedAt(String sessionId) {
    List<Object> sqlParams = new ArrayList<>();
    String sql = loadLastTouchedAtSql(sqlParams, sessionId);
    return structure.jdbc.execute(new SelectDateOrNull(sql, sqlParams));
  }

  protected abstract String loadLastTouchedAtSql(List<Object> sqlParams, String sessionId);

  @Override
  public int removeSessionsOlderThan(int ageInHours) {
    List<Object> sqlParams = new ArrayList<>();
    String sql = removeSessionsOlderThanSql(sqlParams, ageInHours);
    return structure.jdbc.execute(new Update(sql, sqlParams));
  }

  protected abstract String removeSessionsOlderThanSql(List<Object> sqlParams, int ageInHours);
}
