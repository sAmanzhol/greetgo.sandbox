package kz.greetgo.sandbox.controller.model;



public class ClientFilter {
  public String firstname;
  public String lastname;
  public String patronymic;
  public String orderBy;
  public boolean sort;

  @Override
  public String toString() {
    return "ClientFilter{" +
      "firstname='" + firstname + '\'' +
      ", lastname='" + lastname + '\'' +
      ", patronymic='" + patronymic + '\'' +
      ", orderBy='" + orderBy + '\'' +
      ", sort=" + sort +
      ", offSet=" + offSet +
      '}';
  }

  public int offSet;
}
