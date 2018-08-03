package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.AsIs;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ParSession;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.annotations.on_methods.OnPost;
import kz.greetgo.mvc.interfaces.TunnelCookies;
import kz.greetgo.sandbox.controller.errors.RestError;
import kz.greetgo.sandbox.controller.model.AuthInfo;
import kz.greetgo.sandbox.controller.model.UserInfo;
import kz.greetgo.sandbox.controller.register.AuthRegisterOld;
import kz.greetgo.sandbox.controller.security.PublicAccess;
import kz.greetgo.sandbox.controller.util.Controller;
import kz.greetgo.util.RND;

/**
 * как составлять контроллеры написано
 * <a href="https://github.com/greetgo/greetgo.mvc/blob/master/greetgo.mvc.parent/doc/controller_spec.md">здесь</a>
 */
@Bean
@ControllerPrefix("/auth")
public class AuthController implements Controller {

  public BeanGetter<AuthRegisterOld> authRegister;

  @AsIs
  @PublicAccess
  @OnGet("/probe")
  public String probe(@Par("param") String param) {
    if ("err".equals(param)) {
      throw new RestError(476, "Oops");
    }
    return "Probe OK, param = " + param;
  }

  int i = 40;

  @AsIs
  @PublicAccess
  @OnPost("/login")
  public String login(@Par("username") String username,
                      @Par("password") String password,
                      TunnelCookies cookies) {

    System.out.println("ok");

    String hiCookieValue = cookies.name("hi").value();
    System.out.println("hiCookieValue = " + hiCookieValue);

    cookies.forName("hi")
      .path("/")
      .httpOnly(true)
      .maxAge(-1)
      .saveValue("WOW" + i++);

    return "{'key':'some value'}".replace('\'', '"');
  }

  @AsIs
  @PublicAccess
  @OnGet("/login1")
  public String login1(@Par("accountName") String accountName, @Par("password") String password) {
    return authRegister.get().login(accountName, password);
  }

  @ToJson
  @OnGet("/info")
  public AuthInfo info(@ParSession("personId") String personId) {
    return authRegister.get().getAuthInfo(personId);
  }

  @ToJson
  @OnGet("/userInfo2")
  public UserInfo userInfo2(@ParSession("personId") String personId) {
    return authRegister.get().getUserInfo(personId);
  }

  @ToJson
  @PublicAccess
  @OnGet("/userInfo")
  public UserInfo userInfo() {
    UserInfo ret = new UserInfo();
    ret.accountName = "pushkin";
    ret.id = "213nh43k25";
    ret.name = "Александр";
    ret.patronymic = "Сергеевич";
    ret.surname = "Пушкин";
    ret.yellow = RND.bool();
    return ret;
  }
}
