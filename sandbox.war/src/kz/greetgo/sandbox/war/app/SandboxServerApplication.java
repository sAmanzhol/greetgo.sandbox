package kz.greetgo.sandbox.war.app;

import kz.greetgo.sandbox.backend.configuration.security.beans.BeanScannerSecurity;
import kz.greetgo.sandbox.backend.configuration.util.BeanScannerBackend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Import({
    BeanScannerBackend.class,
    BeanScannerSecurity.class,
})
@SpringBootApplication
@EnableTransactionManagement
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
