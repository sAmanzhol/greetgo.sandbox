package kz.greetgo.sandbox.war.app;

import kz.greetgo.sandbox.backend.util.ScannerBackend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

@SpringBootApplication
@Import(ScannerBackend.class)
public class SandboxServerApplication extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(SandboxServerApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(SandboxServerApplication.class);
  }

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    super.onStartup(servletContext);
  }

  private static final Pattern ORIGIN_PATTERN1 = Pattern.compile("http://localhost:\\d+");
  private static final Pattern ORIGIN_PATTERN2 = Pattern.compile("http://127\\.\\d+\\.\\d+\\.\\d+:\\d+");
  private static final Pattern ORIGIN_PATTERN3 = Pattern.compile("http://192\\.168\\.\\d+\\.\\d+:\\d+");

  @Bean
  public Filter createCrossOriginFilter() {
    return (servletRequest, servletResponse, filterChain) -> {
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
    };
  }

}
