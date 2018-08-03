package ka.greetgo.db.session;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

class SessionServiceImpl implements SessionService {
  private final SessionServiceBuilder builder;

  @SuppressWarnings("SpellCheckingInspection")
  private static final String ENG = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String DEG = "0123456789";
  private static final char[] ALL = (
    ENG.toLowerCase() + ENG.toUpperCase() + DEG
  ).toCharArray();
  private static final int ALL_LEN = ALL.length;
  private final Random random = new SecureRandom();

  private String generateId(int length) {

    int[] randomIndexes = random.ints()
      .limit(length)
      .map(i -> i < 0 ? -i : i)
      .map(i -> i % ALL_LEN)
      .toArray();

    char[] chars = new char[length];

    for (int i = 0; i < length; i++) {
      chars[i] = ALL[randomIndexes[i]];
    }

    return new String(chars);
  }

  public SessionServiceImpl(SessionServiceBuilder builder) {
    this.builder = builder;
  }

  static class SessionCache {
    final Object sessionData;
    final String token;
    final AtomicReference<Date> lastTouchedData;

    public SessionCache(Object sessionData, String token, AtomicReference<Date> lastTouchedData) {
      this.sessionData = sessionData;
      this.token = token;
      this.lastTouchedData = lastTouchedData;
    }
  }

  final ConcurrentMap<String, SessionCache> sessionCacheMap = new ConcurrentHashMap<>();
  final ConcurrentMap<String, String> removedSessionIds = new ConcurrentHashMap<>();

  @Override
  public Map<String, String> statisticsInfo() {
    HashMap<String, String> ret = new HashMap<>();
    ret.put("Sessions cache size", "" + sessionCacheMap.size());
    ret.put("Removed sessions map size", "" + removedSessionIds.size());
    return ret;
  }

  @Override
  public SessionIdentity createSession(Object sessionData) {
    String sessionIdPart = generateId(builder.sessionIdLength);
    String sessionSalt = builder.saltGenerator.generateSalt(sessionIdPart);
    String sessionId = new SessionId(sessionSalt, sessionIdPart).toString();
    String token = generateId(builder.tokenLength);
    SessionIdentity identity = new SessionIdentity(sessionId, token);

    builder.storage.insertSession(identity, sessionData);

    Date lastTouchedAt = builder.storage.loadLastTouchedAt(identity.id);

    SessionCache sessionCache = new SessionCache(sessionData, token, new AtomicReference<>(lastTouchedAt));

    sessionCacheMap.put(sessionId, sessionCache);

    return identity;
  }

  @Override
  public Object getSessionData(String sessionId) {

    if (removedSessionIds.containsKey(sessionId)) {
      return null;
    }


    {
      SessionCache sessionCache = sessionCacheMap.get(sessionId);
      if (sessionCache != null) {
        return sessionCache.sessionData;
      }
    }

    return loadSession(sessionId).map(row -> row.sessionData).orElse(null);
  }

  private Optional<SessionRow> loadSession(String sessionId) {
    SessionRow sessionRow = builder.storage.loadSession(sessionId);
    if (sessionRow == null) {
      return Optional.empty();
    }

    sessionCacheMap.put(sessionId, sessionRow.toCacheRecord());
    return Optional.of(sessionRow);
  }

  @Override
  public boolean verifyId(String sessionId) {
    if (sessionId == null) {
      return false;
    }

    SessionId s = SessionId.parse(sessionId);

    if (s == null) {
      return false;
    }

    if (s.part == null || s.part.isEmpty()) {
      return false;
    }

    if (s.salt == null || s.salt.isEmpty()) {
      return false;
    }

    String saltExpected = builder.saltGenerator.generateSalt(s.part);

    return saltExpected.equals(s.salt);

  }

  @Override
  public boolean verifyToken(String sessionId, String token) {
    SessionCache cache = sessionCacheMap.get(sessionId);
    if (cache != null) {
      return Objects.equals(cache.token, token);
    }

    return loadSession(sessionId)
      .filter(row -> row.token != null && row.token.equals(token))
      .isPresent();
  }

  @Override
  public void zeroSessionAge(String sessionId) {
    throw new NotImplementedException();
  }

  @Override
  public void removeSession(String sessionId) {
    throw new NotImplementedException();
  }

  @Override
  public void removeOldSessions() {
    throw new NotImplementedException();
  }

  @Override
  public void syncCache() {
    throw new NotImplementedException();
  }
}
