package kz.greetgo.sandbox.controller.register;

import kz.greetgo.security.session.SessionIdentity;

public interface AuthRegister {
  SessionIdentity login(String username, String password);
}
