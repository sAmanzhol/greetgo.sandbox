package kz.greetgo.sandbox.backend.configuration.security.beans;

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
import java.util.regex.Pattern;

@Component
@Order(2000)
public class CrossOriginFilter implements Filter {
  private static final Pattern ORIGIN_PATTERN1 = Pattern.compile("http://localhost:\\d+");
  private static final Pattern ORIGIN_PATTERN2 = Pattern.compile("http://127\\.\\d+\\.\\d+\\.\\d+:\\d+");
  private static final Pattern ORIGIN_PATTERN3 = Pattern.compile("http://192\\.168\\.\\d+\\.\\d+:\\d+");

  @Override
  public void doFilter(ServletRequest servletRequest,
                       ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    String origin = request.getHeader("Origin");

    if ((origin != null) && (false

        || ORIGIN_PATTERN1.matcher(origin).matches()
        || ORIGIN_PATTERN2.matcher(origin).matches()
        || ORIGIN_PATTERN3.matcher(origin).matches()

    )) {

      response.addHeader("Access-Control-Allow-Credentials", "true");
      response.setHeader("Access-Control-Allow-Origin", origin);//"http://localhost:4200");

      response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
      response.setHeader("Access-Control-Max-Age", "3600");
      response.setHeader("Access-Control-Allow-Headers", "origin,x-requested-with,access-control-request-headers," +
          "content-type,access-control-request-method,accept,token,set-cookie");

    }

    filterChain.doFilter(request, response);

  }
}
