///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.security;


///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.errors.RestError;

public class SecurityError extends RestError {
  public SecurityError() {
    this("Security error");
  }

  public SecurityError(String message) {
    super(401, message);
  }
}
