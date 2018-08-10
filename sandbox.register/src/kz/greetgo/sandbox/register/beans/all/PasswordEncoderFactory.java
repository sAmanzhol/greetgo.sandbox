package kz.greetgo.sandbox.register.beans.all;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.security.password.PasswordEncoder;

import static kz.greetgo.security.SecurityBuilders.newPasswordEncoderBuilder;

@Bean
public class PasswordEncoderFactory {
  @Bean
  public PasswordEncoder createPasswordEncoder() {
    return newPasswordEncoderBuilder().setSalt("h2h3j4j5hn6b6h5j43k3").build();
  }
}
