package ka.greetgo.db.session;

import kz.greetgo.util.RND;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class SessionServiceTest {

  TestSessionStorage sessionStorage;
  SessionService sessionService;
  SessionServiceImpl impl;

  @BeforeMethod
  public void createSessionService() {
    sessionStorage = new TestSessionStorage();

    sessionService = SessionBuilders.newServiceBuilder()
      .setOldSessionAgeInHours(4)
      .setSessionIdLength(17)
      .setTokenLength(17)
      .setStorage(sessionStorage)
      .setSaltGenerator(str -> "S" + str.substring(0, 5) + "S")
      .build();

    impl = (SessionServiceImpl) sessionService;
  }

  @Test
  public void createSession() {
    String sessionData = "SESSION DATA " + RND.str(10);

    //
    //
    SessionIdentity identity = sessionService.createSession(sessionData);
    //
    //

    assertThat(identity).isNotNull();
    assertThat(identity.id).isNotNull();
    assertThat(identity.token).isNotNull();
    assertThat(identity.id).isNotEqualTo(identity.token);

    TestSessionStorage.SessionDot sessionDot = sessionStorage.sessionMap.get(identity.id);
    assertThat(sessionDot).isNotNull();
    assertThat(sessionDot.id).isEqualTo(identity.id);
    assertThat(sessionDot.token).isEqualTo(identity.token);
    assertThat(sessionDot.sessionData).isEqualTo(sessionData);
  }

  @Test
  public void getSessionData() {

    String sessionData = "SESSION DATA " + RND.str(10);
    SessionIdentity identity = sessionService.createSession(sessionData);

    //
    //
    Object actual = sessionService.getSessionData(identity.id);
    //
    //

    assertThat(actual).isEqualTo(sessionData);

    assertThat(impl.sessionCacheMap).containsKey(identity.id);

  }

  @Test
  public void getSessionData_fromStorage() {
    sessionStorage.loadSessionDataCount = 0;

    String sessionData = "SESSION DATA " + RND.str(10);
    SessionIdentity identity = sessionService.createSession(sessionData);

    impl.sessionCacheMap.clear();

    //
    //
    Object actual = sessionService.getSessionData(identity.id);
    //
    //

    assertThat(actual).isEqualTo(sessionData);

    assertThat(sessionStorage.loadSessionDataCount).isEqualTo(1);
  }

  @Test
  public void getSessionData_inRemoved() {
    String sessionData = "SESSION DATA " + RND.str(10);

    SessionIdentity identity = sessionService.createSession(sessionData);

    impl.removedSessionIds.put(identity.id, identity.id);

    //
    //
    Object actual = sessionService.getSessionData(identity.id);
    //
    //

    assertThat(actual).isNull();
  }

  @Test
  public void getSessionData_fromCache() {
    String sessionData = "SESSION DATA " + RND.str(10);
    SessionIdentity identity = sessionService.createSession(sessionData);

    sessionService.getSessionData(identity.id);

    sessionStorage.loadSessionDataCount = 0;

    //
    //
    Object actual = sessionService.getSessionData(identity.id);
    //
    //

    assertThat(actual).isEqualTo(sessionData);

    assertThat(sessionStorage.loadSessionDataCount).isZero();
  }
}
