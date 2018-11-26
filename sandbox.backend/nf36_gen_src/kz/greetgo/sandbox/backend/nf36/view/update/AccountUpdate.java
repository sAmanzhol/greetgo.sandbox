package kz.greetgo.sandbox.backend.nf36.view.update;

import java.lang.String;

public interface AccountUpdate {
  AccountUpdate setName(String name);

  AccountUpdate setPatronymic(String patronymic);

  AccountUpdate setSurname(String surname);


  AccountUpdate whereIdIsEqualTo(String id);

  AccountUpdate whereNameIsEqualTo(String name);

  AccountUpdate wherePatronymicIsEqualTo(String patronymic);

  AccountUpdate whereSurnameIsEqualTo(String surname);

  void commit();
}
