package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.AsIs;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ParSession;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.sandbox.controller.errors.RestError;
import kz.greetgo.sandbox.controller.model.AuthInfo;
import kz.greetgo.sandbox.controller.model.UserInfo;
import kz.greetgo.sandbox.controller.register.AuthRegister;
import kz.greetgo.sandbox.controller.security.PublicAccess;
import kz.greetgo.sandbox.controller.util.Controller;

/**
 * как составлять контроллеры написано
 * <a href="https://github.com/greetgo/greetgo.mvc/blob/master/greetgo.mvc.parent/doc/controller_spec.md">здесь</a>
 */
@Bean
@ControllerPrefix("/auth")
public class AuthController implements Controller {

  public BeanGetter<AuthRegister> authRegister;

  @AsIs
  @PublicAccess
  @OnGet("/probe")
  public String probe(@Par("param") String param) {
    if ("err".equals(param)) {
      throw new RestError(476, "Oops");
    }
    return "Probe OK, param = " + param;
  }

  @AsIs
  @PublicAccess
  @OnGet("/login")
  public String login(@Par("accountName") String accountName, @Par("password") String password) {
    return authRegister.get().login(accountName, password);
  }

  @ToJson
  @OnGet("/info")
  public AuthInfo info(@ParSession("personId") String personId) {
    return authRegister.get().getAuthInfo(personId);
  }

  @ToJson
  @OnGet("/userInfo")
  public UserInfo userInfo(@ParSession("personId") String personId) {
    return authRegister.get().getUserInfo(personId);
  }
}
