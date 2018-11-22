package kz.greetgo.sandbox.backend.configuration.security.beans;

import kz.greetgo.sandbox.backend.configuration.security.core.PublicAccess;
import kz.greetgo.sandbox.backend.configuration.security.roles.MustHave;
import kz.greetgo.sandbox.backend.configuration.security.roles.Role;
import kz.greetgo.sandbox.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ControllerAccessInterceptor extends HandlerInterceptorAdapter {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (handler instanceof HandlerMethod) {
      return preHandleMethod((HandlerMethod) handler);
    }
    return false;
  }

  @Autowired
  private AuthService authService;

  private boolean preHandleMethod(HandlerMethod handlerMethod) {

    {
      PublicAccess publicAccess = handlerMethod.getMethodAnnotation(PublicAccess.class);
      if (publicAccess != null) {
        return true;
      }
    }

    {
      MustHave mustHave = handlerMethod.getMethodAnnotation(MustHave.class);
      authService.checkAccessTo(mustHave == null ? new Role[]{} : mustHave.value());
    }

    return true;
  }
}
