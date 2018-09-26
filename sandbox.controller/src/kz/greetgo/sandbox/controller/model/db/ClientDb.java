package kz.greetgo.sandbox.controller.model.db;

import kz.greetgo.sandbox.controller.model.ClientToSave;
import kz.greetgo.sandbox.controller.model.GenderType;

import java.util.Date;

public class ClientDb {

  public int id;
  public String surname;
  public String name;
  public String patronymic;
  public GenderType gender;
  public Date birthDate;
  public int charm;
  public boolean actual = true;

  public ClientDb convertToSaveToClient(ClientToSave toSave, int charmId) {
    this.id = toSave.clientID;
    this.name = toSave.name;
    this.patronymic = toSave.patronymic;
    this.surname = toSave.surname;
    this.birthDate = toSave.birthDay;
    this.charm = charmId;
    this.gender = toSave.gender.type;
    return this;
  }
}
