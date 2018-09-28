package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Json;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnDelete;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.annotations.on_methods.OnPost;
import kz.greetgo.sandbox.controller.model.ClientDisplay;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.ClientToFilter;
import kz.greetgo.sandbox.controller.model.ClientToSave;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.List;

@Bean
@ControllerPrefix("/client")
public class ClientController implements Controller {

  public BeanGetter<ClientRegister> clientRegister;

  @ToJson
  @OnGet("/list")
  public List<ClientRecord> list(@Json @Par("filter") ClientToFilter filter) {
    return clientRegister.get().list(filter);
  }

  @ToJson
  @OnGet("/count")
  public int count(@Json @Par("filter") ClientToFilter filter) {
    return clientRegister.get().count(filter);
  }

  @ToJson
  @OnPost("/")
  public ClientRecord save(@Json @Par("clientToSave") ClientToSave clientToSave) {
    return clientRegister.get().save(clientToSave);
  }

  @ToJson
  @OnGet("/")
  public ClientDisplay details(@Par("id") int id) {
    return clientRegister.get().details(id);
  }

  @ToJson
  @OnDelete("/")
  public void delete(@Par("id") int id) {
    clientRegister.get().delete(id);
  }
}
