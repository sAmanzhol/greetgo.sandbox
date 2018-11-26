package kz.greetgo.sandbox.backend.nf36.impl;

import kz.greetgo.db.nf36.core.Nf36Updater;
import kz.greetgo.sandbox.backend.nf36.impl.update.AccountUpdateImpl;
import kz.greetgo.sandbox.backend.nf36.view.DbUpdater;
import kz.greetgo.sandbox.backend.nf36.view.update.AccountUpdate;

public abstract class AbstractDbUpdater implements DbUpdater {
  protected abstract Nf36Updater createUpdater();

  @Override
  public AccountUpdate account() {
    return new AccountUpdateImpl(createUpdater());
  }

}
