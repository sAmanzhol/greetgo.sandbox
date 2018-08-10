package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.AsIs;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.annotations.on_methods.OnPost;
import kz.greetgo.mvc.interfaces.TunnelCookies;
import kz.greetgo.sandbox.controller.model.UserInfo;
import kz.greetgo.sandbox.controller.register.AuthRegister;
import kz.greetgo.sandbox.controller.security.PublicAccess;
import kz.greetgo.sandbox.controller.util.Controller;
import kz.greetgo.security.session.SessionIdentity;
import kz.greetgo.util.RND;

/**
 * как составлять контроллеры написано
 * <a href="https://github.com/greetgo/greetgo.mvc/blob/master/doc/controller_spec.md">здесь</a>
 */
@Bean
@ControllerPrefix("/auth")
public class AuthController implements Controller {

  public BeanGetter<AuthRegister> authRegister;


  @AsIs
  @PublicAccess
  @OnPost("/login")
  public String login(@Par("username") String username,
                      @Par("password") String password,
                      TunnelCookies cookies) {

    System.out.println("username = " + username);
    System.out.println("password = " + password);

    SessionIdentity identity = authRegister.get().login(username, password);

    cookies.forName("g-session")
      .path("/")
      .httpOnly(true)
      .maxAge(-1)
      .saveValue(identity.id);

    return identity.token;
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
