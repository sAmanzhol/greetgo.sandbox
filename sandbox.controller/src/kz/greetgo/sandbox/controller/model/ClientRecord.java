package kz.greetgo.sandbox.controller.model;


import java.util.Comparator;

public class ClientRecord {
  public int id;
  public String firstname;
  public String lastname;
  public String patronymic;
  public String character;
  public String dateOfBirth;
  public int totalAccountBalance;
  public int maximumBalance;
  public int minimumBalance;

  // теперь собственно реализуем интерфейс Comparator, для сортировки по названию
  public static class SortedByFirstname implements Comparator<ClientRecord> {

    public int compare(ClientRecord obj1, ClientRecord obj2) {

      String str1 = obj1.firstname + obj1.lastname;
      String str2 = obj2.firstname + obj2.lastname;
      return str1.compareTo(str2);
    }
  }

  @Override
  public String toString() {
    return "ClientRecord{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", patronymic='" + patronymic + '\'' +
            ", character='" + character + '\'' +
            ", dateOfBirth='" + dateOfBirth + '\'' +
            ", totalAccountBalance=" + totalAccountBalance +
            ", maximumBalance=" + maximumBalance +
            ", minimumBalance=" + minimumBalance +
            '}';
  }
}
