package ka.greetgo.db.session;

import kz.greetgo.mvc.security.SecuritySource;

public class SessionServiceBuilder {
  private SessionStorage storage;
  private SecuritySource securitySource;

  private int oldSessionAgeInHours = 24;

  SessionServiceBuilder() {}

  public SessionServiceBuilder setStorage(SessionStorage storage) {
    this.storage = storage;
    return this;
  }

  public SessionServiceBuilder setSecuritySource(SecuritySource securitySource) {
    this.securitySource = securitySource;
    return this;
  }

  public SessionServiceBuilder setOldSessionAgeInHours(int oldSessionAgeInHours) {
    this.oldSessionAgeInHours = oldSessionAgeInHours;
    return this;
  }
}
