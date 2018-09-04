package kz.greetgo.sandbox.debug.bean_containers;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.sandbox.controller.controller.BeanConfigControllers;
import kz.greetgo.sandbox.register.beans.all.BeanConfigAll;
import kz.greetgo.sandbox.debug.beans.BeanConfigStand;

@BeanConfig
@Include({BeanConfigStand.class, BeanConfigControllers.class, BeanConfigAll.class})
public class BeanConfigForDebugBeanContainer {}
