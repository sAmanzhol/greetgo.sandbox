package ka.greetgo.db.session;

import ka.greetgo.db.factory.JdbcFactory;
import ka.greetgo.db.session.jdbc.SelectBytesField;
import ka.greetgo.db.session.jdbc.SelectDateField;
import ka.greetgo.db.session.jdbc.SelectStrField;
import kz.greetgo.db.DbType;
import kz.greetgo.db.Jdbc;
import kz.greetgo.util.RND;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.Serializable;

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
      {DbType.Postgres}
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

    SessionStorage sessionStorage = SessionBuilder.newStorageBuilder()
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
}