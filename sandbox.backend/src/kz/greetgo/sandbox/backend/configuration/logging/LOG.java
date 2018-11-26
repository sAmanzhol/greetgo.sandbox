package kz.greetgo.sandbox.backend.configuration.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class LOG {
  private final Logger destination;

  private LOG(Logger destination) {
    this.destination = destination;
  }

  public static LOG byClass(Class<?> aClass) {
    return new LOG(LoggerFactory.getLogger(aClass));
  }

  public void info(Supplier<String> message) {
    if (destination.isInfoEnabled()) {
      destination.info(message.get());
    }
  }

  public void errorMessage(String message) {
    if (destination.isErrorEnabled()) {
      destination.error(message);
    }
  }

  public boolean isTraceEnabled() {
    return destination.isTraceEnabled();
  }

  public void trace(Supplier<String> message) {
    if (destination.isTraceEnabled()) {
      destination.trace(message.get());
    }
  }
}
