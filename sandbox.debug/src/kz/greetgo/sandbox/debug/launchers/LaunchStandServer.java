///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.launchers;

import kz.greetgo.depinject.Depinject;
import kz.greetgo.depinject.gen.DepinjectUtil;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.util.Modules;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.debug.bean_containers.StandBeanContainer;

public class LaunchStandServer {
  public static void main(String[] args) throws Exception {
    new LaunchStandServer().run();
  }

  private void run() throws Exception {
    DepinjectUtil.implementAndUseBeanContainers(
///MODIFY replace sandbox {PROJECT_NAME}
      "kz.greetgo.sandbox.debug",
      Modules.standDir() + "/build/src_bean_containers");

    StandBeanContainer container = Depinject.newInstance(StandBeanContainer.class);

    container.server().start().join();
  }
}
