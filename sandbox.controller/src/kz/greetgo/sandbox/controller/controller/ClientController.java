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
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.security.PublicAccess;
import kz.greetgo.sandbox.controller.util.Controller;

/**
 * Created by msultanova on 9/4/18.
 */
@Bean
@ControllerPrefix("/client")
public class ClientController implements Controller {

  public BeanGetter<ClientRegister> clientRegisterBeanGetter;

  @ToJson
  @PublicAccess
  @OnGet("/list")
  public ClientRecordListWrapper list(@Json @Par("clientFilter") ClientFilter clientFilter){
    return clientRegisterBeanGetter.get().filterClients(clientFilter);
  }

  @ToJson
  @PublicAccess
  @OnGet("/detail")
  public ClientDetail getDetail(@Par("id") Long id){
    return clientRegisterBeanGetter.get().getClientDetailById(id);
  }


  @ToJson
  @PublicAccess
  @OnPost("/save")
  public ClientRecord save(@Json @Par("toSave") ClientToSave toSave){
    return clientRegisterBeanGetter.get().saveClient(toSave);
  }

  @ToJson
  @PublicAccess
  @OnDelete("/delete")
  public void delete(@Par("id") Long id){
    clientRegisterBeanGetter.get().deleteClient(id);
  }

}

