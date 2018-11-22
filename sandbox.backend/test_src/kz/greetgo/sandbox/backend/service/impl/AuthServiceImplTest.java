package kz.greetgo.sandbox.backend.service.impl;

import kz.greetgo.sandbox.backend.service.AuthService;
import kz.greetgo.sandbox.backend.test.util.ParentTestNg;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class AuthServiceImplTest extends ParentTestNg {

  @Autowired
  private AuthService authService;

  @Test
  public void test() {
    assertThat(authService).isNotNull();
  }
}