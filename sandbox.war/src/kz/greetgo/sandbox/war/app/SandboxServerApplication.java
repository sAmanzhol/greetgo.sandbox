package kz.greetgo.sandbox.war.app;

import kz.greetgo.sandbox.backend.util.ScannerBackend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

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
}
