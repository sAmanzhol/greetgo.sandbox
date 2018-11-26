package kz.greetgo.sandbox.backend.nf36.impl;

import java.lang.String;
import kz.greetgo.db.nf36.core.Nf36Upserter;
import kz.greetgo.db.nf36.core.SequenceNext;
import kz.greetgo.sandbox.backend.nf36.impl.upsert.AccountUpsertImpl;
import kz.greetgo.sandbox.backend.nf36.view.DbUpserter;
import kz.greetgo.sandbox.backend.nf36.view.upsert.AccountUpsert;

public abstract class AbstractDbUpserter implements DbUpserter {
  protected abstract Nf36Upserter createUpserter();

  protected abstract SequenceNext getSequenceNext();

  @Override
  public AccountUpsert account(String id) {
    return new AccountUpsertImpl(createUpserter(), id);
  }

}
