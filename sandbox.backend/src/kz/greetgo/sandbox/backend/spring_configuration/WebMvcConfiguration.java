package kz.greetgo.sandbox.backend.spring_configuration;

import kz.greetgo.sandbox.backend.security.beans.ControllerAccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

  @Autowired
  private ControllerAccessInterceptor controllerAccessInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(controllerAccessInterceptor);
  }
}
