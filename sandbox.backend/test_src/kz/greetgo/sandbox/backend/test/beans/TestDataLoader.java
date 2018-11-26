package kz.greetgo.sandbox.backend.test.beans;

import kz.greetgo.sandbox.backend.configuration.logging.LOG;
import kz.greetgo.sandbox.backend.service.StarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataLoader {

  private final LOG log = LOG.byClass(getClass());

  @Autowired
  private StarsService starsService;

  public void load() {
    log.info(() -> "Loading test data...");



  }
}
