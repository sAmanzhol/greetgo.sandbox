package kz.greetgo.sandbox.register.test.beans._develop_;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.register.impl.TokenRegister;
import kz.greetgo.sandbox.register.test.dao.AuthTestDao;
import org.apache.log4j.Logger;

@Bean
public class DbLoader {
  final Logger logger = Logger.getLogger(getClass());


  public BeanGetter<AuthTestDao> authTestDao;
  public BeanGetter<TokenRegister> tokenManager;

  public void loadTestData() {
    logger.info("Start loading test data...");

    logger.info("Finish loading test data");
  }
}
