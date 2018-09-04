package kz.greetgo.sandbox.controller.register.model;

import sun.misc.Cleaner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SessionInfo implements Serializable {
  public final String personId;
  public final Date createdAt = new Date();

  public SessionInfo(String personId) {
    if (personId == null) throw new NullPointerException("personId == null");
    if (personId.length() == 0) throw new IllegalArgumentException("personId is empty");
    this.personId = personId;
  }

  @Override
  public String toString() {
    return "SessionInfo{" +
      "personId='" + personId + '\'' +
      ", createdAt=" + createdAt +
      '}';
  }
}
