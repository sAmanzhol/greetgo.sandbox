package ka.greetgo.db.session;

import kz.greetgo.util.RND;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class SessionServiceTest {

  TestSessionStorage sessionStorage;
  SessionService sessionService;
  SessionServiceImpl impl;
  final SaltGenerator saltGenerator = str -> "S" + str.substring(0, 5) + "S";

  @BeforeMethod
  public void createSessionService() {
    sessionStorage = new TestSessionStorage();


    sessionService = SessionBuilders.newServiceBuilder()
      .setOldSessionAgeInHours(4)
      .setSessionIdLength(17)
      .setTokenLength(17)
      .setStorage(sessionStorage)
      .setSaltGenerator(saltGenerator)
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
    sessionStorage.loadSessionCount = 0;

    String sessionData = "SESSION DATA " + RND.str(10);
    SessionIdentity identity = sessionService.createSession(sessionData);

    impl.sessionCacheMap.clear();

    //
    //
    Object actual = sessionService.getSessionData(identity.id);
    //
    //

    assertThat(actual).isEqualTo(sessionData);

    assertThat(sessionStorage.loadSessionCount).isEqualTo(1);
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

    impl.sessionCacheMap.clear();

    sessionService.getSessionData(identity.id);

    sessionStorage.loadSessionCount = 0;

    //
    //
    Object actual = sessionService.getSessionData(identity.id);
    //
    //

    assertThat(actual).isEqualTo(sessionData);

    assertThat(sessionStorage.loadSessionCount).isZero();
  }

  @Test
  public void getSessionData_absent() {
    sessionStorage.loadSessionCount = 0;

    //
    //
    Object actual = sessionService.getSessionData(RND.str(10));
    //
    //

    assertThat(actual).isNull();

    assertThat(sessionStorage.loadSessionCount).isEqualTo(1);
    assertThat(impl.sessionCacheMap).isEmpty();
  }

  @Test
  public void verifyId() {
    String id = RND.str(10);
    String salt = saltGenerator.generateSalt(id);

    String sessionId = new SessionId(salt, id).toString();

    //
    //
    boolean verificationResult = sessionService.verifyId(sessionId);
    //
    //

    assertThat(verificationResult).isTrue();
  }

  @Test
  public void verifyId_leftId1() {
    //
    //
    boolean verificationResult = sessionService.verifyId(RND.str(10));
    //
    //

    assertThat(verificationResult).isFalse();
  }

  @Test
  public void verifyId_leftId2() {
    //
    //
    boolean verificationResult = sessionService.verifyId(RND.str(10) + "-");
    //
    //

    assertThat(verificationResult).isFalse();
  }

  @Test
  public void verifyId_leftId3() {
    //
    //
    boolean verificationResult = sessionService.verifyId("-" + RND.str(10));
    //
    //

    assertThat(verificationResult).isFalse();
  }


  @Test
  public void verifyId_null() {
    //
    //
    boolean verificationResult = sessionService.verifyId(null);
    //
    //

    assertThat(verificationResult).isFalse();
  }

  @Test
  public void statisticsInfo() {
    Map<String, String> statisticsInfo = sessionService.statisticsInfo();
    assertThat(statisticsInfo).isNotNull();
  }

  @Test
  public void verifyToken() {
    SessionIdentity identity = sessionService.createSession(null);

    impl.sessionCacheMap.clear();
    sessionStorage.loadSessionCount = 0;

    //
    //
    boolean verificationResult = sessionService.verifyToken(identity.id, identity.token);
    //
    //

    assertThat(verificationResult).isTrue();
    assertThat(sessionStorage.loadSessionCount).isEqualTo(1);

    assertThat(impl.sessionCacheMap).containsKey(identity.id);
  }

  @Test
  public void verifyToken_fromCache() {
    SessionIdentity identity = sessionService.createSession(null);

    assertThat(impl.sessionCacheMap).containsKey(identity.id);
    sessionStorage.loadSessionCount = 0;

    //
    //
    boolean verificationResult = sessionService.verifyToken(identity.id, identity.token);
    //
    //

    assertThat(verificationResult).isTrue();
    assertThat(sessionStorage.loadSessionCount).isZero();

    assertThat(impl.sessionCacheMap).containsKey(identity.id);
  }

  @Test
  public void verifyToken_noSession() {
    SessionIdentity identity = sessionService.createSession(null);

    assertThat(impl.sessionCacheMap).containsKey(identity.id);
    impl.sessionCacheMap.clear();
    sessionStorage.loadSessionCount = 0;

    sessionStorage.sessionMap.clear();

    //
    //
    boolean verificationResult = sessionService.verifyToken(identity.id, identity.token);
    //
    //

    assertThat(verificationResult).isFalse();
    assertThat(sessionStorage.loadSessionCount).isEqualTo(1);

    assertThat(impl.sessionCacheMap).doesNotContainKey(identity.id);
  }

  @Test
  public void verifyToken_anotherToken() {
    SessionIdentity identity1 = sessionService.createSession(null);
    SessionIdentity identity2 = sessionService.createSession(null);

    impl.sessionCacheMap.clear();
    sessionStorage.loadSessionCount = 0;

    //
    //
    boolean verificationResult = sessionService.verifyToken(identity1.id, identity2.token);
    //
    //

    assertThat(verificationResult).isFalse();
    assertThat(sessionStorage.loadSessionCount).isEqualTo(1);

    assertThat(impl.sessionCacheMap).containsKey(identity1.id);
    assertThat(impl.sessionCacheMap).doesNotContainKey(identity2.id);
  }

  @Test
  public void verifyToken_leftToken_noCache() {
    SessionIdentity identity = sessionService.createSession(null);

    impl.sessionCacheMap.clear();
    sessionStorage.loadSessionCount = 0;

    //
    //
    boolean verificationResult = sessionService.verifyToken(identity.id, RND.str(10));
    //
    //

    assertThat(verificationResult).isFalse();
    assertThat(sessionStorage.loadSessionCount).isEqualTo(1);

    assertThat(impl.sessionCacheMap).containsKey(identity.id);
  }

  @Test
  public void verifyToken_leftToken() {
    SessionIdentity identity = sessionService.createSession(null);

    assertThat(impl.sessionCacheMap).containsKey(identity.id);
    sessionStorage.loadSessionCount = 0;

    //
    //
    boolean verificationResult = sessionService.verifyToken(identity.id, RND.str(10));
    //
    //

    assertThat(verificationResult).isFalse();
    assertThat(sessionStorage.loadSessionCount).isZero();

    assertThat(impl.sessionCacheMap).containsKey(identity.id);
  }
}
