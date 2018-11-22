package kz.greetgo.sandbox.backend.service;

import kz.greetgo.sandbox.backend.security.roles.Role;

public interface AuthService {
  String probe();

  void prepareSessionInThreadLocal(String sessionId, String token);

  void checkAccessTo(Role[] value);
}
