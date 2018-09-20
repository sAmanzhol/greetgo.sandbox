package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Json;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ParPath;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.*;
import kz.greetgo.sandbox.controller.model.ClientDisplay;
import kz.greetgo.sandbox.controller.model.ClientRecord;
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
  public List<ClientRecord> list(@Par("target") String target, @Par("type") String type, @Par("query") String query) {
    return clientRegister.get().list(target, type, query);
  }

  @ToJson
  @OnPost("/{id}")
  public ClientDisplay crupdate(@ParPath("id") String id, @Json @Par("clientDisplay") ClientDisplay clientDisplay) {
    System.out.println(clientDisplay);
    return clientRegister.get().crupdate(id, clientDisplay);
  }

  @ToJson
  @OnGet("/")
  public ClientDisplay get(@Par("id") String id) {
    return clientRegister.get().get(id);
  }

  @ToJson
  @OnDelete("/")
  public ClientDisplay delete(@Par("id") String id) {
    return clientRegister.get().delete(id);
  }
}
