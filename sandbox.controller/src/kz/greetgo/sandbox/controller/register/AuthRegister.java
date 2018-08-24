///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.register;

///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.PersonDisplay;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.SessionHolder;
import kz.greetgo.security.session.SessionIdentity;

public interface AuthRegister {
  SessionIdentity login(String username, String password);

  void resetThreadLocalAndVerifySession(String sessionId, String token);

  SessionHolder getSession();

  PersonDisplay displayPerson(String personId);

  void deleteSession(String sessionId);
}
