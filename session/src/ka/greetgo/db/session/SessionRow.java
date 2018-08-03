package ka.greetgo.db.session;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class SessionRow {
  public final String token;
  public final Object sessionData;
  public final Date insertedAt;
  public final Date lastTouchedAt;

  public SessionRow(String token, Object sessionData, Date insertedAt, Date lastTouchedAt) {
    this.token = token;
    this.sessionData = sessionData;
    this.insertedAt = insertedAt;
    this.lastTouchedAt = lastTouchedAt;
  }

  public SessionServiceImpl.SessionCache toCacheRecord() {
    return new SessionServiceImpl.SessionCache(sessionData, token, new AtomicReference<>(lastTouchedAt));
  }
}
