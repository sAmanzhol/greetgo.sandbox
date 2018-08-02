package kz.greetgo.sandbox.register.beans.all;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.sandbox.controller.controller.BeanConfigControllers;
import kz.greetgo.sandbox.register.dao.postgres.BeanConfigPostgresDao;
import kz.greetgo.sandbox.register.impl.BeanConfigRegisterImpl;

@BeanConfig
@BeanScanner
@Include({BeanConfigRegisterImpl.class, BeanConfigPostgresDao.class, BeanConfigControllers.class})
public class BeanConfigAll {}
