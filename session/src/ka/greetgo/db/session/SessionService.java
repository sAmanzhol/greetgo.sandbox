package ka.greetgo.db.session;

public interface SessionService {

  SessionIdentity newSession(Object sessionData);

  Object getSessionData(String sessionId);

  boolean verifyId(String sessionId);

  void zeroSessionAge(String sessionId);

  void removeSession(String sessionId);

  void removeOldSessions();

  void syncCache();
}
