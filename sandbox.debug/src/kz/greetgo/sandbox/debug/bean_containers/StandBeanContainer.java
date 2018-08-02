package kz.greetgo.sandbox.debug.bean_containers;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.sandbox.debug.beans.StandServer;

@Include(BeanConfigForStandBeanContainer.class)
public interface StandBeanContainer extends BeanContainer {
  StandServer server();
}
