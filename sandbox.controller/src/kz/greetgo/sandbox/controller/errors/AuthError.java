///MODIFY replace sandbox PROJECT_NAME
package kz.greetgo.sandbox.controller.errors;

public class AuthError extends RestError {
  public AuthError(String message) {
    super(470, message);
  }
}
