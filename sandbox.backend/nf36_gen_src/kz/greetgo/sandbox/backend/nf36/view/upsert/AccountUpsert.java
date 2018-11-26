package kz.greetgo.sandbox.backend.nf36.view.upsert;

import java.lang.String;
import kz.greetgo.sandbox.backend.nf36.view.upsert.AccountUpsert;

public interface AccountUpsert {
  AccountUpsert surname(String surname);

  AccountUpsert name(String name);

  AccountUpsert patronymic(String patronymic);

  AccountUpsert more(String id);

  void commit();
}
