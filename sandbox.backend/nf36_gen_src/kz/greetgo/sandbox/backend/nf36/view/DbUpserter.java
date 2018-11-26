package kz.greetgo.sandbox.backend.nf36.view;

import java.lang.String;
import kz.greetgo.sandbox.backend.nf36.view.upsert.AccountUpsert;

public interface DbUpserter {
  AccountUpsert account(String id);

}
