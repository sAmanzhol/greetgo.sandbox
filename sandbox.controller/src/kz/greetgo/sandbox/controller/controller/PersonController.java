///MODIFY replace sandbox PROJECT_NAME
package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
///MODIFY replace sandbox PROJECT_NAME
import kz.greetgo.sandbox.controller.model.PersonRecord;
///MODIFY replace sandbox PROJECT_NAME
import kz.greetgo.sandbox.controller.register.PersonRegister;
///MODIFY replace sandbox PROJECT_NAME
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.List;

@Bean
@ControllerPrefix("/person")
public class PersonController implements Controller {

  public BeanGetter<PersonRegister> personRegister;

  @ToJson
  @OnGet("/list")
  public List<PersonRecord> list() {
    return personRegister.get().list();
  }

}
