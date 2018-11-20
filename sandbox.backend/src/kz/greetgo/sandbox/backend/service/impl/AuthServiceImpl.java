package kz.greetgo.sandbox.backend.service.impl;

import kz.greetgo.sandbox.backend.service.AuthService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthServiceImpl implements AuthService {

  @Override
  public String probe() {
    return "Hello from PROBE " + new Date();
  }
}
