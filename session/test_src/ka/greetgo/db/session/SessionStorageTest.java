package ka.greetgo.db.session;

import ka.greetgo.db.factory.JdbcFactory;
import ka.greetgo.db.jdbc.SelectBytesField;
import ka.greetgo.db.jdbc.SelectDateField;
import ka.greetgo.db.jdbc.SelectNow;
import ka.greetgo.db.jdbc.SelectStrField;
import ka.greetgo.db.session.jdbc.SelectIntOrNull;
import ka.greetgo.db.session.jdbc.Update;
import kz.greetgo.db.DbType;
import kz.greetgo.db.Jdbc;
import kz.greetgo.util.RND;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Collections.singletonList;
import static org.fest.assertions.api.Assertions.assertThat;

public class SessionStorageTest {
  JdbcFactory jdbcFactory = new JdbcFactory();

  @BeforeMethod
  public void createJdbcFactory() {
    jdbcFactory.defineDbNameFrom("session_storage");
  }

  @DataProvider
  private Object[][] dbTypeDataProvider() {
    return new Object[][]{
      {DbType.Postgres}, {DbType.Postgres},
    };
  }

  public static class TestSessionData implements Serializable {
    public String userId;
    public String role;
  }

  @Test(dataProvider = "dbTypeDataProvider")
  public void insertAndGet(DbType dbType) {
    jdbcFactory.dbType = dbType;
    Jdbc jdbc = jdbcFactory.create();

    SessionStorage sessionStorage = SessionBuilders.newStorageBuilder()
      .setJdbc(dbType, jdbc)
      .setTableName("s_storage")
      .setFieldId("id")
      .setFieldToken("unique_token")
      .setFieldSessionData("user_data")
      .setFieldInsertedAt("ins_at")
      .setFieldLastTouchedAt("touched_at")
      .build();


    SessionIdentity identity = new SessionIdentity(RND.intStr(15), RND.str(10));

    TestSessionData sessionData = new TestSessionData();
    sessionData.userId = RND.str(10);
    sessionData.role = RND.str(10);

    //
    //
    sessionStorage.insertSession(identity, sessionData);
    //
    //

    String actualToken = jdbc.execute(new SelectStrField("unique_token", "s_storage", identity.id));
    assertThat(actualToken).isEqualTo(identity.token);

    assertThat(jdbc.execute(new SelectDateField("ins_at", "s_storage", identity.id))).isNotNull();
    assertThat(jdbc.execute(new SelectDateField("touched_at", "s_storage", identity.id))).isNotNull();
    byte[] bytes = jdbc.execute(new SelectBytesField("user_data", "s_storage", identity.id));
    TestSessionData actualSessionData = Serializer.deserialize(bytes);
    assertThat(actualSessionData).isNotNull();
    assert actualSessionData != null;
    assertThat(actualSessionData.userId).isEqualTo(sessionData.userId);
    assertThat(actualSessionData.role).isEqualTo(sessionData.role);
  }

  @Test(dataProvider = "dbTypeDataProvider")
  public void loadSessionData(DbType dbType) {
    jdbcFactory.dbType = dbType;
    Jdbc jdbc = jdbcFactory.create();

    SessionStorage sessionStorage = SessionBuilders.newStorageBuilder()
      .setJdbc(dbType, jdbc)
      .build();

    SessionIdentity identity = new SessionIdentity(RND.intStr(15), RND.str(10));

    TestSessionData sessionData = new TestSessionData();
    sessionData.userId = RND.str(10);
    sessionData.role = RND.str(10);
    sessionStorage.insertSession(identity, sessionData);


    //
    //
    TestSessionData actual = sessionStorage.loadSessionData(identity.id);
    //
    //

    assert actual != null;
    assertThat(actual.userId).isEqualTo(sessionData.userId);
    assertThat(actual.role).isEqualTo(sessionData.role);
  }

  @Test(dataProvider = "dbTypeDataProvider")
  public void loadSessionData_null(DbType dbType) {
    jdbcFactory.dbType = dbType;
    Jdbc jdbc = jdbcFactory.create();

    SessionStorage sessionStorage = SessionBuilders.newStorageBuilder()
      .setJdbc(dbType, jdbc)
      .build();

    SessionIdentity identity = new SessionIdentity(RND.intStr(15), RND.str(10));

    sessionStorage.insertSession(identity, null);

    //
    //
    TestSessionData actual = sessionStorage.loadSessionData(identity.id);
    //
    //

    assertThat(actual).isNull();
  }

  @Test(dataProvider = "dbTypeDataProvider")
  public void loadToken(DbType dbType) {
    jdbcFactory.dbType = dbType;
    Jdbc jdbc = jdbcFactory.create();

    SessionStorage sessionStorage = SessionBuilders.newStorageBuilder()
      .setJdbc(dbType, jdbc)
      .build();

    SessionIdentity identity = new SessionIdentity(RND.intStr(15), RND.str(10));

    sessionStorage.insertSession(identity, null);

    //
    //
    String token = sessionStorage.loadToken(identity.id);
    //
    //

    assertThat(token).isEqualTo(identity.token);
  }


  @Test(dataProvider = "dbTypeDataProvider")
  public void loadToken_null(DbType dbType) {
    jdbcFactory.dbType = dbType;
    Jdbc jdbc = jdbcFactory.create();

    SessionStorage sessionStorage = SessionBuilders.newStorageBuilder()
      .setJdbc(dbType, jdbc)
      .build();

    SessionIdentity identity = new SessionIdentity(RND.intStr(15), null);

    sessionStorage.insertSession(identity, null);

    //
    //
    String token = sessionStorage.loadToken(identity.id);
    //
    //

    assertThat(token).isNull();
  }

  private void setDateFieldInAllTable(Jdbc jdbc, String fieldName, Date time) {
    jdbc.execute(new Update("update session_storage set " + fieldName + " = ?",
      singletonList(new Timestamp(time.getTime()))));
  }

  @Test(dataProvider = "dbTypeDataProvider")
  public void loadInsertedAt(DbType dbType) {
    jdbcFactory.dbType = dbType;
    Jdbc jdbc = jdbcFactory.create();

    SessionStorage sessionStorage = SessionBuilders.newStorageBuilder()
      .setJdbc(dbType, jdbc)
      .build();

    SessionIdentity identity = new SessionIdentity(RND.intStr(15), null);

    sessionStorage.insertSession(identity, null);

    setDateFieldInAllTable(jdbc, "inserted_at", nowAddHours(jdbc, -10));

    //
    //
    Date insertedAt = sessionStorage.loadInsertedAt(identity.id);
    //
    //

    assertThat(insertedAt).isNotNull();

    assertThat(insertedAt).isBefore(nowAddHours(jdbc, -5));
  }

  private static Date nowAddHours(Jdbc jdbc, int hours) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(jdbc.execute(new SelectNow()));
    calendar.add(Calendar.HOUR, hours);
    return calendar.getTime();
  }

  @Test(dataProvider = "dbTypeDataProvider")
  public void loadLastTouchedAt(DbType dbType) {
    jdbcFactory.dbType = dbType;
    Jdbc jdbc = jdbcFactory.create();

    SessionStorage sessionStorage = SessionBuilders.newStorageBuilder()
      .setJdbc(dbType, jdbc)
      .build();

    SessionIdentity identity = new SessionIdentity(RND.intStr(15), null);

    sessionStorage.insertSession(identity, null);

    setDateFieldInAllTable(jdbc, "last_touched_at", nowAddHours(jdbc, -5));

    //
    //
    Date lastTouchedAt = sessionStorage.loadLastTouchedAt(identity.id);
    //
    //

    assertThat(lastTouchedAt).isNotNull();

    assertThat(lastTouchedAt).isBefore(nowAddHours(jdbc, -4));

  }

  @Test(dataProvider = "dbTypeDataProvider")
  public void zeroSessionAge(DbType dbType) {
    jdbcFactory.dbType = dbType;
    Jdbc jdbc = jdbcFactory.create();

    SessionStorage sessionStorage = SessionBuilders.newStorageBuilder()
      .setJdbc(dbType, jdbc)
      .build();

    SessionIdentity identity = new SessionIdentity(RND.intStr(15), null);

    sessionStorage.insertSession(identity, null);

    setDateFieldInAllTable(jdbc, "last_touched_at", nowAddHours(jdbc, -5));

    //
    //
    boolean updated = sessionStorage.zeroSessionAge(identity.id);
    //
    //

    assertThat(updated).isTrue();

    Date actual = jdbc.execute(new SelectDateField("last_touched_at", "session_storage", identity.id));
    assertThat(actual).isAfter(nowAddHours(jdbc, -1));
  }

  private void insertSession(SessionStorage sessionStorage) {
    SessionIdentity identity = new SessionIdentity(RND.intStr(15), null);
    sessionStorage.insertSession(identity, null);
  }

  @Test(dataProvider = "dbTypeDataProvider")
  public void zeroSessionAge_leftSessionId(DbType dbType) {
    jdbcFactory.dbType = dbType;
    Jdbc jdbc = jdbcFactory.create();

    SessionStorage sessionStorage = SessionBuilders.newStorageBuilder()
      .setJdbc(dbType, jdbc)
      .build();

    //
    //
    boolean updated = sessionStorage.zeroSessionAge(RND.str(10));
    //
    //

    assertThat(updated).isFalse();
  }

  @Test(dataProvider = "dbTypeDataProvider")
  public void removeSessionsOlderThan(DbType dbType) {
    jdbcFactory.dbType = dbType;
    Jdbc jdbc = jdbcFactory.create();

    SessionStorage sessionStorage = SessionBuilders.newStorageBuilder()
      .setJdbc(dbType, jdbc)
      .build();

    jdbc.execute(new Update("delete from session_storage"));

    insertSession(sessionStorage);
    insertSession(sessionStorage);
    insertSession(sessionStorage);

    setDateFieldInAllTable(jdbc, "last_touched_at", nowAddHours(jdbc, -10));

    insertSession(sessionStorage);
    insertSession(sessionStorage);

    //
    //
    int count = sessionStorage.removeSessionsOlderThan(7);
    //
    //

    assertThat(count).isEqualTo(3);

    int leaveCount = jdbc.execute(new SelectIntOrNull("select count(1) from session_storage"));
    assertThat(leaveCount).isEqualTo(2);
  }


}