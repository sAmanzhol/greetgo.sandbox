package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.ClientDisplay;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Bean
public class ClientRegisterImpl implements ClientRegister {

  private List<ClientRecord> list = new ArrayList<>(Arrays.asList(
    new ClientRecord("1", "Колобова Розалия Наумовна", "Самовлюблённый", "37", "332", "234324234", "345"),
    new ClientRecord("2", "Панова Алира Иосифовна", "Замкнутый", "29", "4324", "32423424", "4"),
    new ClientRecord("3", "Крюков Игнатий Улебович", "Великодушный", "30", "234", "234234324", "467"),
    new ClientRecord("4", "Киселёв Юлиан Романович", "Бессердечный", "23", "32", "32435", "3"),
    new ClientRecord("5", "Исаева Ирина Сергеевна", "Грубый", "18", "4324", "6878768", "234"),
    new ClientRecord("6", "Большаков Мечеслав Куприянович", "Целеустремлённый", "56", "100", "786786", "73"),
    new ClientRecord("7", "Корнилов Захар Федосеевич", "Мизантроп", "47", "8344", "6546546", "45"),
    new ClientRecord("8", "Лихачёв Исак Кириллович", "Строгий", "27", "34534", "5464564", "87"),
    new ClientRecord("9", "Фёдорова Эмбер Руслановна", "Гениальный", "21", "4357", "546456546", "12"),
    new ClientRecord("10", "Баранова Габриэлла Романовна", "Харизматичный", "41", "3454", "7686543", "43"),
    new ClientRecord("11", "Никонов Лев Викторович", "Безответственный", "22", "434", "546758", "54")
  ));

  private List<ClientDisplay> listDetails = new ArrayList<>(Arrays.asList(
    new ClientDisplay("1", "Колобова", "Розалия", "Наумовна", "2000-08-30", "female", "Самовлюблённый", "Ломоносова", "11", "9", "", "", "", "+7(747)-341-54-28", "", "", "", ""),
    new ClientDisplay("2", "Панова", "Алира", "Иосифовна", "2000-08-30", "female", "Замкнутый", "Пр.абылай Хана", "74", "140", "", "", "", "+7(727)-273-76-55", "", "", "", ""),
    new ClientDisplay("3", "Крюков", "Игнатий", "Улебович", "2000-08-30", "male", "Великодушный", "Ломоносова", "11", "9", "", "", "", "+7(747)-341-54-28", "", "", "", ""),
    new ClientDisplay("4", "Киселёв", "Юлиан", "Романович", "2000-08-30", "male", "Бессердечный", "Ломоносова", "11", "9", "", "", "", "+7(747)-341-54-28", "", "", "", ""),
    new ClientDisplay("5", "Исаева", "Ирина", "Сергеевна", "2000-08-30", "female", "Грубый", "Ломоносова", "11", "9", "", "", "", "+7(747)-341-54-28", "", "", "", ""),
    new ClientDisplay("6", "Большаков", "Мечеслав", "Куприянович", "2000-08-30", "male", "Целеустремлённый", "Ломоносова", "11", "9", "", "", "", "+7(747)-341-54-28", "", "", "", ""),
    new ClientDisplay("7", "Корнилов", "Захар", "Федосеевич", "2000-08-30", "male", "Мизантроп", "Ломоносова", "11", "9", "", "", "", "+7(747)-341-54-28", "", "", "", ""),
    new ClientDisplay("8", "Лихачёв", "Исак", "Кириллович", "2000-08-30", "male", "Строгий", "Ломоносова", "11", "9", "", "", "", "+7(747)-341-54-28", "", "", "", ""),
    new ClientDisplay("9", "Фёдорова", "Эмбер", "Руслановна", "2000-08-30", "female", "Гениальный", "Ломоносова", "11", "9", "", "", "", "+7(747)-341-54-28", "", "", "", ""),
    new ClientDisplay("10", "Баранова", "Габриэлла", "Романовна", "2000-08-30", "female", "Харизматичный", "Ломоносова", "11", "9", "", "", "", "+7(747)-341-54-28", "", "", "", ""),
    new ClientDisplay("11", "Никонов", "Лев", "Викторович", "2000-08-30", "male", "Безответственный", "Ломоносова", "11", "9", "", "", "", "+7(747)-341-54-28", "", "", "", "")
  ));


  @Override
  public List<ClientRecord> list(String target, String type, String query) {
    Comparator<ClientRecord> comparator = Comparator.comparing((ClientRecord clientRecord) -> clientRecord.id);

    if (target.equalsIgnoreCase("fio")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> clientRecord.fio);
    } else if (target.equalsIgnoreCase("age")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.age));
    } else if (target.equalsIgnoreCase("balance")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.balance));
    } else if (target.equalsIgnoreCase("balanceMax")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.balanceMax));
    } else if (target.equalsIgnoreCase("balanceMin")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.balanceMin));
    }

    if (!type.equalsIgnoreCase("asc")) {
      comparator = comparator.reversed();
    }

    return list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(query.toLowerCase())).sorted(comparator).collect(Collectors.toList());
  }

  @Override
  public ClientDisplay crupdate(String id, ClientDisplay clientDisplay) {
    System.out.println(clientDisplay);

    if (id.equals("")) {
      String newId = Integer.parseInt(this.list.get(this.list.size() - 1).id) + 1 + "";
      this.listDetails.add(clientDisplay);
      //Change this, add id

      LocalDate birthdate = LocalDate.of(Integer.parseInt(clientDisplay.birthDate.split("-")[0]), Integer.parseInt(clientDisplay.birthDate.split("-")[1]), Integer.parseInt(clientDisplay.birthDate.split("-")[2]));
      LocalDate now = LocalDate.now();
      int age = Period.between(birthdate, now).getYears();

      this.list.add(new ClientRecord(
        newId, clientDisplay.surname + " " + clientDisplay.name + clientDisplay.patronymic, clientDisplay.character, "" + age, "0", "1000", "0"
      ));

      return clientDisplay;
    } else {
      ClientDisplay clientToUpdate = this.listDetails.stream()
        .filter(client -> client.id.equals(id))
        .findFirst()
        .get();

      int indexToUpdate = this.listDetails.indexOf(clientToUpdate);
      System.out.println(indexToUpdate);

      this.listDetails.set(indexToUpdate, clientDisplay);

      LocalDate birthdate = LocalDate.of(Integer.parseInt(clientDisplay.birthDate.split("-")[0]), Integer.parseInt(clientDisplay.birthDate.split("-")[1]), Integer.parseInt(clientDisplay.birthDate.split("-")[2]));
      LocalDate now = LocalDate.now();
      int age = Period.between(birthdate, now).getYears();

      ClientRecord clientRecordToUpdate = this.list.stream()
        .filter(client -> client.id.equals(id))
        .findFirst()
        .get();

      int indexRecordToUpdate = this.list.indexOf(clientRecordToUpdate);
      System.out.println(indexRecordToUpdate);

      this.list.set(indexRecordToUpdate, new ClientRecord(
        clientRecordToUpdate.id, clientDisplay.surname + " " + clientDisplay.name + clientDisplay.patronymic, clientDisplay.character, "" + age, "0", "1000", "0"
      ));

      return clientDisplay;
    }
  }

  @Override
  public ClientDisplay get(String id) {
    return this.listDetails.stream()
      .filter(client -> client.id.equals(id))
      .findFirst()
      .get();
  }

  @Override
  public ClientDisplay delete(String id) {
    ClientDisplay clientToRemove = this.listDetails.stream()
      .filter(client -> client.id.equals(id))
      .findFirst()
      .get();

    this.list.remove(this.list.stream()
      .filter(client -> client.id.equals(id))
      .findFirst()
      .get());

    this.listDetails.remove(clientToRemove);
    return clientToRemove;
  }
}
