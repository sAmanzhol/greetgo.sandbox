package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.sandbox.controller.model.CharacterRecord;
import kz.greetgo.sandbox.controller.register.CharacterRegister;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.List;

@Bean
@ControllerPrefix("/character")
public class CharacterController implements Controller {

  public BeanGetter<CharacterRegister> characterRegister;

  @ToJson
  @OnGet("/list")
  public List<CharacterRecord> list() {
    return characterRegister.get().list();
  }
}
