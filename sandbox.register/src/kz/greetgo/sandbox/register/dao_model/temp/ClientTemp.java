package kz.greetgo.sandbox.register.dao_model.temp;

@SuppressWarnings("WeakerAccess")
public class ClientTemp {
  public String id;
  public String surname;
  public String name;
  public String patronymic;
  public String gender;
  public String charm;
  public String birthDate;
  public int status;
  public int migrationOrder;

  public ClientTemp() {
    id = "";
    surname = "";
    name = "";
    patronymic = "";
    gender = "";
    charm = "";
    birthDate = "";
    status = 1;
    migrationOrder = 0;
  }
}
