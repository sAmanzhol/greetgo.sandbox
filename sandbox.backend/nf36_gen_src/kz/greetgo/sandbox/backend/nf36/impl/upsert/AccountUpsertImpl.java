package kz.greetgo.sandbox.backend.nf36.impl.upsert;

import java.lang.String;
import kz.greetgo.db.nf36.core.Nf36Upserter;
import kz.greetgo.sandbox.backend.nf36.view.upsert.AccountUpsert;

public class AccountUpsertImpl implements AccountUpsert {
  private final Nf36Upserter upserter;

  public AccountUpsertImpl(Nf36Upserter upserter, String id) {
    this.upserter = upserter;
    upserter.setNf3TableName("account");
    upserter.setTimeFieldName("ts");
    upserter.setAuthorFieldNames("created_by", "modified_by", "inserted_by");
    upserter.putId("id", id);
  }

  @Override
  public AccountUpsert more(String id) {
    return new AccountUpsertImpl(this.upserter.more(), id);
  }

  @Override
  public AccountUpsert surname(String surname) {
    upserter.putField("m.account_surname", "surname", surname);
    return this;
  }

  @Override
  public AccountUpsert name(String name) {
    upserter.putField("m.account_name", "name", name);
    return this;
  }

  @Override
  public AccountUpsert patronymic(String patronymic) {
    upserter.putField("m.account_patronymic", "patronymic", patronymic);
    return this;
  }

  @Override
  public void commit() {
    upserter.putUpdateToNowWithParent("modified_at");
    upserter.commit();
  }
}
