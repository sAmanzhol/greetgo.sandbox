package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.security.PublicAccess;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.List;

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
    public List<ClientRecord> list() {

        return clientRegisterBeanGetter.get().getClientList();
    }


}
