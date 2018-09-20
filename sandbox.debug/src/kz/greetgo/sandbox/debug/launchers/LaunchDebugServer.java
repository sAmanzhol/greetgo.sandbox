package kz.greetgo.sandbox.debug.launchers;

import kz.greetgo.depinject.Depinject;
import kz.greetgo.depinject.gen.DepinjectUtil;
import kz.greetgo.sandbox.controller.util.Modules;
import kz.greetgo.sandbox.debug.bean_containers.DebugBeanContainer;

public class LaunchDebugServer {
  public static void main(String[] args) throws Exception {
    new LaunchDebugServer().run();
  }

  private void run() throws Exception {
    DepinjectUtil.implementAndUseBeanContainers(
      "kz.greetgo.sandbox.debug",
      Modules.standDir() + "/build/src_bean_containers");

    DebugBeanContainer container = Depinject.newInstance(DebugBeanContainer.class);

    container.server().start().join();
  }
}
