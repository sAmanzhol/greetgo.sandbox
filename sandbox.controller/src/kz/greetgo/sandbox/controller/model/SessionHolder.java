package kz.greetgo.sandbox.controller.model;

import java.io.Serializable;

public class SessionHolder implements Serializable {
  public final String personId;
  public final String role;

  public SessionHolder(String personId, String role) {
    this.personId = personId;
    this.role = role;
  }
}
