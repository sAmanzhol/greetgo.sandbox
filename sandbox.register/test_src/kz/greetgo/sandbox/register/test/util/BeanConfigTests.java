package kz.greetgo.sandbox.register.test.util;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.sandbox.register.beans.all.BeanConfigAll;
import kz.greetgo.sandbox.register.test.beans.BeanConfigTestBeans;
import kz.greetgo.sandbox.register.test.dao.postgres.BeanConfigTestDao;
import kz.greetgo.sandbox.db.stand.beans.BeanConfigStandDb;

@BeanConfig
@Include({BeanConfigTestDao.class, BeanConfigTestBeans.class, BeanConfigAll.class, BeanConfigStandDb.class})
public class BeanConfigTests {}
