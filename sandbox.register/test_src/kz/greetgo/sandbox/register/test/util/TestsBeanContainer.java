package kz.greetgo.sandbox.register.test.util;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.sandbox.register.test.beans._develop_.DbLoader;
import kz.greetgo.sandbox.register.test.beans._develop_.DbWorker;

@Include(BeanConfigTests.class)
public interface TestsBeanContainer extends BeanContainer {
  DbWorker dbWorker();

  DbLoader dbLoader();
}
