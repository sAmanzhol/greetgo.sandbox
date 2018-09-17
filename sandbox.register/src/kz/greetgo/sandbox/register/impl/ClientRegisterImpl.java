package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.errors.IllegalLoginOrPassword;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.FilterParams;
import kz.greetgo.sandbox.controller.model.PersonDisplay;
import kz.greetgo.sandbox.controller.model.SessionHolder;
import kz.greetgo.sandbox.controller.register.AuthRegister;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dao.AuthDao;
import kz.greetgo.sandbox.register.model.PersonLogin;
import kz.greetgo.security.password.PasswordEncoder;
import kz.greetgo.security.session.SessionIdentity;
import kz.greetgo.security.session.SessionService;

import java.util.ArrayList;
import java.util.List;

import static kz.greetgo.sandbox.controller.util.FilterUtil.skipNulls;

@Bean
public class ClientRegisterImpl implements ClientRegister {

  @Override
  public List<ClientRecord> getClients(FilterParams params) {
    ClientRecord record = new ClientRecord();
    record.fio = "ASDASD";
    record.id = "WWWW";

    List test = new ArrayList();
    test.add(record);

    return test;
  }
}
