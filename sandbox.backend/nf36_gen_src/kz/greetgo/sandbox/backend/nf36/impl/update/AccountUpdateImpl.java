package kz.greetgo.sandbox.backend.nf36.impl.update;

import java.lang.String;
import kz.greetgo.db.nf36.core.Nf36Updater;
import kz.greetgo.db.nf36.errors.CannotBeNull;
import kz.greetgo.sandbox.backend.nf36.view.update.AccountUpdate;

public class AccountUpdateImpl implements AccountUpdate {
  private final Nf36Updater updater;

  public AccountUpdateImpl(Nf36Updater updater) {
    this.updater = updater;
    updater.setNf3TableName("account");
    updater.setAuthorFieldNames("modified_by", "inserted_by");
    updater.updateFieldToNow("modified_at");
    updater.setIdFieldNames("id");
  }

  @Override
  public AccountUpdate setSurname(String surname) {
    this.updater.setField("m.account_surname", "surname", surname);
    return this;
  }

  @Override
  public AccountUpdate setName(String name) {
    this.updater.setField("m.account_name", "name", name);
    return this;
  }

  @Override
  public AccountUpdate setPatronymic(String patronymic) {
    this.updater.setField("m.account_patronymic", "patronymic", patronymic);
    return this;
  }



  @Override
  public AccountUpdate whereIdIsEqualTo(String id) {
    if (id == null) {
      throw new CannotBeNull("Field Account.id cannot be null");
    }
    this.updater.where("id", id);
    return this;
  }

  @Override
  public AccountUpdate whereNameIsEqualTo(String name) {
    this.updater.where("name", name);
    return this;
  }

  @Override
  public AccountUpdate wherePatronymicIsEqualTo(String patronymic) {
    this.updater.where("patronymic", patronymic);
    return this;
  }

  @Override
  public AccountUpdate whereSurnameIsEqualTo(String surname) {
    this.updater.where("surname", surname);
    return this;
  }

  @Override
  public void commit() {
    this.updater.commit();
  }
}
