package kz.greetgo.sandbox.backend.security.beans;

import kz.greetgo.sandbox.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.greetgo.sandbox.backend.security.core.SessionConst.SESSION_ID;
import static kz.greetgo.sandbox.backend.util.MvcUtil.extractCookieValue;

@Component
@Order(1000)
public class SessionPreparationFilter implements Filter {

  @Autowired
  private AuthService authService;

  @Override
  public void doFilter(ServletRequest servletRequest,
                       ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    String sessionId = extractCookieValue(request, SESSION_ID).orElse(null);

    String token = request.getHeader("token");

    authService.prepareSessionInThreadLocal(sessionId, token);

    filterChain.doFilter(request, response);
  }
}
