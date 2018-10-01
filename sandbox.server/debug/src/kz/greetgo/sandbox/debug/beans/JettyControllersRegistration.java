///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.JettyWarServlet;
import kz.greetgo.mvc.builder.ExecDefinition;
import kz.greetgo.mvc.interfaces.Views;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.util.Controller;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.debug.util.WebAppContextRegistration;

import java.util.ArrayList;
import java.util.List;

@Bean
public class JettyControllersRegistration extends JettyWarServlet implements WebAppContextRegistration {

  public BeanGetter<List<Controller>> controllerList;

  @Override
  protected List<Object> getControllerList() {
    return new ArrayList<>(controllerList.get());
  }

  public BeanGetter<Views> views;

  @Override
  protected Views getViews() {
    return views.get();
  }

  @Override
  protected void afterRegistered() {
    System.err.println("[WebAppContext] --------------------------------------");
    System.err.println("[WebAppContext] -- USING CONTROLLERS:");
    for (ExecDefinition execDefinition : execDefinitionList()) {

      System.err.println("[WebAppContext] --   " + execDefinition.infoStr());
    }
    System.err.println("[WebAppContext] --------------------------------------");
    printRegistration();
  }

  @Override
  protected String mappingBase() {
    return "/api/*";
  }

  @Override
  protected String getTargetSubContext() {
    return "/api";
  }

  @Override
  public double priority() {
    return 0;
  }
}
