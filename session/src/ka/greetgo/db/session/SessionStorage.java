package ka.greetgo.db.session;


import java.util.Date;

/**
 * Stores sessions in some storage
 */
public interface SessionStorage {
  /**
   * Insert new session
   *
   * @param identity    session identity. Unique must be identity.id - it defines session
   * @param sessionData additional session data (to store userId, role or something else)
   */
  void insertSession(SessionIdentity identity, Object sessionData);

  /**
   * Loads additional session data
   *
   * @param sessionId session id
   * @return additional session data, or null if session is absent
   */
  Object loadSessionData(String sessionId);

  /**
   * Loads token from session
   *
   * @param sessionId session id
   * @return token or null, if session is absent
   */
  String loadToken(String sessionId);

  /**
   * Loads session insertion time
   *
   * @param sessionId session id
   * @return session insertion time or null, if session is absent
   */
  Date loadInsertedAt(String sessionId);

  /**
   * Loads session last touched time
   *
   * @param sessionId session id
   * @return session last touched time or null, if session is absent
   */
  Date loadLastTouchedAt(String sessionId);

  /**
   * Makes session young
   *
   * @param sessionId session id
   * @return indicates session exists:
   * <code>true</code> - session exists and its age made zero,
   * <code>false</code> - session is absent
   */
  boolean zeroSessionAge(String sessionId);

  /**
   * Removes all session which age is more then ageInHours in hours
   *
   * @param ageInHours session age in hours
   * @return count of removed sessions
   */
  int removeSessionsOlderThan(int ageInHours);
}
