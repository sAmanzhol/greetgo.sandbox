package kz.greetgo.sandbox.register.test.util.test_utils;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.Character;
import kz.greetgo.sandbox.controller.model.db.ClientDb;
import kz.greetgo.util.RND;

import java.util.Date;

@Bean
public class RandomEntity {

  public Client client() {
    Client client = new Client();
    client.name = RND.str(10);
    client.surname = RND.str(10);
    client.patronymic = RND.str(10);
    client.birthDay = new Date();
    client.gender = new Gender(GenderType.FEMALE, "");
    client.character = new Character(CharacterType.AGREEABLENESS, "");
    return client;
  }

  public ClientDb clientDb() {
    ClientDb client = new ClientDb();
    client.name = RND.str(10);
    client.surname = RND.str(10);
    client.patronymic = RND.str(10);
    return client;
  }

  public ClientRecord record() {
    return null;
  }

  public ClientFilter filter() {
    return new ClientFilter();
  }

  public ClientFilter filter(String column) {
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.columnName = column;
    return clientFilter;
  }

  public ClientFilter filter(int limit) {
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.limit = limit;
    return clientFilter;
  }

  public ClientFilter filter(String name, String surname, String patronymic) {
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.name = name;
    clientFilter.surname = surname;
    clientFilter.patronymic = patronymic;
    return clientFilter;
  }
}
