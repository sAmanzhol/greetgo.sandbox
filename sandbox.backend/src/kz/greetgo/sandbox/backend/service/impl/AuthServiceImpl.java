package kz.greetgo.sandbox.backend.service.impl;

import kz.greetgo.sandbox.backend.configuration.security.roles.Role;
import kz.greetgo.sandbox.backend.service.AuthService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthServiceImpl implements AuthService {

  @Override
  public String probe() {
    return "Hello from PROBE " + new Date();
  }

  @Override
  public void prepareSessionInThreadLocal(String sessionId, String token) {
    System.out.println("prepareSessionInThreadLocal: sessionId = " + sessionId + ", token = " + token);
  }

  @Override
  public void checkAccessTo(Role[] value) {

  }
}
