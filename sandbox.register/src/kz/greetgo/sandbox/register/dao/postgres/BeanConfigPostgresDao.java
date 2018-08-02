package kz.greetgo.sandbox.register.dao.postgres;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.sandbox.register.beans.all.DaoImplFactory;

@BeanScanner
@BeanConfig(defaultFactoryClass = DaoImplFactory.class)
public class BeanConfigPostgresDao {}
