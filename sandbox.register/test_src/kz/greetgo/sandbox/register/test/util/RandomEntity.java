package kz.greetgo.sandbox.register.test.util;

import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.Character;
import kz.greetgo.util.RND;

import java.util.Date;

public class RandomEntity {

  public Client client(){

    Client client = new Client();
    client.name = RND.str(10);
    client.surname = RND.str(10);
    client.patronymic = RND.str(10);
    client.birthDay = new Date();
    client.gender = new Gender(GenderType.FEMALE, "");
    client.character = new Character(CharacterType.AGREEABLENESS, "");

    return client;
  }
  public ClientRecord record(){
    return null;
  }

}
