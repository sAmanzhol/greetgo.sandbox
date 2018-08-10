package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.errors.IllegalLoginOrPassword;
import kz.greetgo.sandbox.controller.model.SessionHolder;
import kz.greetgo.sandbox.controller.register.AuthRegister;
import kz.greetgo.sandbox.register.test.dao.AuthTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.security.password.PasswordEncoder;
import kz.greetgo.security.session.SessionIdentity;
import kz.greetgo.security.session.SessionService;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class AuthRegisterImplTest extends ParentTestNg {

  public BeanGetter<AuthRegister> authRegister;

  public BeanGetter<PasswordEncoder> passwordEncoder;
  public BeanGetter<AuthTestDao> authTestDao;
  public BeanGetter<SessionService> sessionService;

  @Test
  public void login() {

    String id = RND.str(10);
    String username = RND.str(10);
    String password = RND.str(10);
    String encodedPassword = passwordEncoder.get().encode(password);

    authTestDao.get().insertPerson(id, username, encodedPassword);

    //
    //
    SessionIdentity identity = authRegister.get().login(username, password);
    //
    //

    assertThat(identity).isNotNull();
  }

  @Test(expectedExceptions = IllegalLoginOrPassword.class)
  public void login_noUsername() {
    //
    //
    authRegister.get().login(RND.str(10), RND.str(10));
    //
    //
  }

  @Test(expectedExceptions = IllegalLoginOrPassword.class)
  public void login_leftPassword() {
    String id = RND.str(10);
    String username = RND.str(10);
    String password = RND.str(10);
    String encodedPassword = passwordEncoder.get().encode(password);

    authTestDao.get().insertPerson(id, username, encodedPassword);

    //
    //
    authRegister.get().login(username, RND.str(10));
    //
    //
  }

  @Test(expectedExceptions = IllegalLoginOrPassword.class)
  public void login_nullPassword() {
    String id = RND.str(10);
    String username = RND.str(10);
    String password = RND.str(10);
    String encodedPassword = passwordEncoder.get().encode(password);

    authTestDao.get().insertPerson(id, username, encodedPassword);

    //
    //
    authRegister.get().login(username, null);
    //
    //
  }

  @Test(expectedExceptions = IllegalLoginOrPassword.class)
  public void login_emptyPassword() {
    String id = RND.str(10);
    String username = RND.str(10);
    String password = RND.str(10);
    String encodedPassword = passwordEncoder.get().encode(password);

    authTestDao.get().insertPerson(id, username, encodedPassword);

    //
    //
    authRegister.get().login(username, "");
    //
    //
  }

  @Test
  public void resetThreadLocalAndVerifySession_getSession_ok() {
    SessionHolder sessionHolder = new SessionHolder(RND.str(10), RND.str(10));
    SessionIdentity identity = sessionService.get().createSession(sessionHolder);

    //
    //
    authRegister.get().resetThreadLocalAndVerifySession(identity.id, identity.token);
    SessionHolder actual = authRegister.get().getSession();
    //
    //

    assertThat(actual).isNotNull();
    assertThat(actual.personId).isEqualTo(sessionHolder.personId);
    assertThat(actual.role).isEqualTo(sessionHolder.role);
  }

  @Test
  public void resetThreadLocalAndVerifySession_getSession_leftToken() {
    SessionHolder sessionHolder = new SessionHolder(RND.str(10), RND.str(10));
    SessionIdentity identity = sessionService.get().createSession(sessionHolder);

    //
    //
    authRegister.get().resetThreadLocalAndVerifySession(identity.id, RND.str(10));
    SessionHolder actual = authRegister.get().getSession();
    //
    //

    assertThat(actual).isNull();
  }

  @Test
  public void resetThreadLocalAndVerifySession_getSession_nullToken() {
    SessionHolder sessionHolder = new SessionHolder(RND.str(10), RND.str(10));
    SessionIdentity identity = sessionService.get().createSession(sessionHolder);

    //
    //
    authRegister.get().resetThreadLocalAndVerifySession(identity.id, null);
    SessionHolder actual = authRegister.get().getSession();
    //
    //

    assertThat(actual).isNull();
  }

  @Test
  public void resetThreadLocalAndVerifySession_getSession_emptyToken() {
    SessionHolder sessionHolder = new SessionHolder(RND.str(10), RND.str(10));
    SessionIdentity identity = sessionService.get().createSession(sessionHolder);

    //
    //
    authRegister.get().resetThreadLocalAndVerifySession(identity.id, "");
    SessionHolder actual = authRegister.get().getSession();
    //
    //

    assertThat(actual).isNull();
  }

  @Test
  public void resetThreadLocalAndVerifySession_getSession_leftSessionId() {
    //
    //
    authRegister.get().resetThreadLocalAndVerifySession(RND.str(10), null);
    SessionHolder actual = authRegister.get().getSession();
    //
    //

    assertThat(actual).isNull();
  }
}
