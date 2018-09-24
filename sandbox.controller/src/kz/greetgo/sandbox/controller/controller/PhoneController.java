package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.sandbox.controller.model.PhoneRecord;
import kz.greetgo.sandbox.controller.register.PhoneRegister;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.List;

@Bean
@ControllerPrefix("/phone")
public class PhoneController implements Controller {

  public BeanGetter<PhoneRegister> phoneRegister;

  @ToJson
  @OnGet("/list")
  public List<PhoneRecord> list() {
    return phoneRegister.get().list();
  }
}
