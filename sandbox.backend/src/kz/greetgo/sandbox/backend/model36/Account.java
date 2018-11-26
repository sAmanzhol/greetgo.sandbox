package kz.greetgo.sandbox.backend.model36;

import kz.greetgo.db.nf36.core.Nf3Description;
import kz.greetgo.db.nf36.core.Nf3Entity;
import kz.greetgo.db.nf36.core.Nf3ID;
import kz.greetgo.db.nf36.core.Nf3Length;

@Nf3Description("An user account")
@Nf3Entity
public class Account {

  @Nf3Description("Account identification field")
  @Nf3ID
  @Nf3Length(32)
  public String id;

  @Nf3Description("Account surname")
  public String surname;

  @Nf3Description("Account name")
  public String name;

  @Nf3Description("Account patronymic")
  public String patronymic;
}
