package ka.greetgo.db.session;

import java.sql.SQLException;
import java.util.List;

class SessionStoragePostgresAdapter extends AbstractSessionStorageAdapter implements SessionStorage {
  SessionStoragePostgresAdapter(SessionStorageBuilder.Structure structure) {
    super(structure);
  }

  @Override
  protected String checkTableExistsSql() {
    return "select " + structure.id + " from " + structure.tableName + " limit 1";
  }

  @Override
  protected String insertSessionSql(List<Object> sqlParams, SessionIdentity identity, Object sessionData) {
    sqlParams.add(identity.id);
    sqlParams.add(identity.token);
    sqlParams.add(Serializer.serialize(sessionData));

    return "insert into " + structure.tableName + " (" +
      structure.id +
      ", " +
      structure.token +
      ", " +
      structure.sessionData +
      ") values (?, ?, ?)";
  }

  @Override
  protected String createSessionTableSql() {
    return "create table " + structure.tableName + " (" +
      "  " + structure.id + " varchar(50) not null," +
      "  " + structure.token + " varchar(50)," +
      "  " + structure.sessionData + " byTea," +
      "  " + structure.insertedAt + " timestamp not null default clock_timestamp()," +
      "  " + structure.lastTouchedAt + " timestamp not null default clock_timestamp()," +
      "  primary key(" + structure.id + ")" +
      ")";
  }

  @Override
  protected boolean isExceptionAboutTableDoesNotExists(SQLException sqlException) {
    return "42P01".equals(sqlException.getSQLState());
  }
}
