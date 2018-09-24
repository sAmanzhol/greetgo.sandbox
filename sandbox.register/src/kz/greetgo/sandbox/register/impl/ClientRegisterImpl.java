package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// FIXME: 9/24/18 Избавься от варнингов в коде

@Bean
public class ClientRegisterImpl implements ClientRegister {

  private static List<CharacterRecord> listCharacters = new ArrayList<>(Arrays.asList(
    new CharacterRecord("1", "Самовлюблённый"),
    new CharacterRecord("2", "Замкнутый"),
    new CharacterRecord("3", "Великодушный"),
    new CharacterRecord("4", "Бессердечный"),
    new CharacterRecord("5", "Грубый"),
    new CharacterRecord("6", "Целеустремлённый"),
    new CharacterRecord("7", "Мизантроп"),
    new CharacterRecord("8", "Строгий"),
    new CharacterRecord("9", "Гениальный"),
    new CharacterRecord("10", "Харизматичный"),
    new CharacterRecord("11", "Безответственный")
  ));

  private static List<ClientRecord> list = new ArrayList<>(Arrays.asList(
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

  private static List<ClientDisplay> listDetails = new ArrayList<ClientDisplay>(Arrays.asList(
    new ClientDisplay("1", "Колобова", "Розалия", "Наумовна", "2000-08-30", "female", "1", "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Arrays.asList(new PhoneDisplay("1", "Home", "77077077777"), new PhoneDisplay("2", "Work", "77877077777"), new PhoneDisplay("3", "Mobile", "77007077777")))),
    new ClientDisplay("2", "Панова", "Алира", "Иосифовна", "2000-08-30", "female", "2", "Пр.абылай Хана", "74", "140", "", "", "", new ArrayList<>(Arrays.asList(new PhoneDisplay("Home", "1", "77077077777")))),
    new ClientDisplay("3", "Крюков", "Игнатий", "Улебович", "2000-08-30", "male", "3", "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Arrays.asList(new PhoneDisplay("1", "Home", "87077077777")))),
    new ClientDisplay("4", "Киселёв", "Юлиан", "Романович", "2000-08-30", "male", "4", "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Arrays.asList(new PhoneDisplay("1", "Home", "87077077777")))),
    new ClientDisplay("5", "Исаева", "Ирина", "Сергеевна", "2000-08-30", "female", "5", "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Arrays.asList(new PhoneDisplay("1", "Home", "87077077777")))),
    new ClientDisplay("6", "Большаков", "Мечеслав", "Куприянович", "2000-08-30", "male", "6", "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Arrays.asList(new PhoneDisplay("1", "Home", "87077077777")))),
    new ClientDisplay("7", "Корнилов", "Захар", "Федосеевич", "2000-08-30", "male", "7", "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Arrays.asList(new PhoneDisplay("1", "Home", "87077077777")))),
    new ClientDisplay("8", "Лихачёв", "Исак", "Кириллович", "2000-08-30", "male", "8", "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Arrays.asList(new PhoneDisplay("1", "Home", "87077077777")))),
    new ClientDisplay("9", "Фёдорова", "Эмбер", "Руслановна", "2000-08-30", "female", "9", "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Arrays.asList(new PhoneDisplay("1", "Home", "87077077777")))),
    new ClientDisplay("10", "Баранова", "Габриэлла", "Романовна", "2000-08-30", "female", "10", "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Arrays.asList(new PhoneDisplay("1", "Home", "87077077777")))),
    new ClientDisplay("11", "Никонов", "Лев", "Викторович", "2000-08-30", "male", "11", "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Arrays.asList(new PhoneDisplay("1", "Home", "87077077777"))))
  ));

  @Override
  public List<ClientRecord> list(ClientToFilter filter) {
    List<ClientRecord> sortedList = sort(filter);

    int listLength = sortedList.size();
    int startIndex = (Integer.parseInt(filter.page) - 1) * Integer.parseInt(filter.itemCount);
    int endIndex = startIndex + Integer.parseInt(filter.itemCount) < listLength ? startIndex + Integer.parseInt(filter.itemCount) : listLength;

    return sortedList.subList(startIndex, endIndex);
  }

  @Override
  public int count(ClientToFilter filter) {
    List<ClientRecord> sortedList = sort(filter);
    return sortedList.size();
  }

  @Override
  public ClientDisplay crupdate(String id, ClientToSave clientToSave) {

    ClientDisplay clientDisplay = new ClientDisplay(clientToSave.id, clientToSave.surname, clientToSave.name, clientToSave.patronymic, clientToSave.birthDate, clientToSave.gender, clientToSave.character, clientToSave.streetRegistration, clientToSave.houseRegistration, clientToSave.apartmentRegistration, clientToSave.streetResidence, clientToSave.houseResidence, clientToSave.apartmentResidence, clientToSave.numbers);

    if ("".equals(id)) {
      String newId = Integer.parseInt(list.get(list.size() - 1).id) + 1 + "";

      clientDisplay.id = newId;
      listDetails.add(clientDisplay);

      LocalDate birthdate = LocalDate.of(Integer.parseInt(clientDisplay.birthDate.split("-")[0]), Integer.parseInt(clientDisplay.birthDate.split("-")[1]), Integer.parseInt(clientDisplay.birthDate.split("-")[2]));
      LocalDate now = LocalDate.now();
      int age = Period.between(birthdate, now).getYears();

      CharacterRecord character = listCharacters.stream()
        .filter(ch -> ch.id.equals(clientDisplay.character))
        .findFirst()
        .get();

      list.add(new ClientRecord(
        newId, clientDisplay.surname + " " + clientDisplay.name + " " + clientDisplay.patronymic, character.value, "" + age, "0", "1000", "0"
      ));

      return clientDisplay;
    } else {
      ClientDisplay clientToUpdate = listDetails.stream()
        .filter(client -> client.id.equals(id))
        .findFirst()
        .get();

      int indexToUpdate = listDetails.indexOf(clientToUpdate);

      listDetails.set(indexToUpdate, clientDisplay);

      LocalDate birthdate = LocalDate.of(Integer.parseInt(clientDisplay.birthDate.split("-")[0]), Integer.parseInt(clientDisplay.birthDate.split("-")[1]), Integer.parseInt(clientDisplay.birthDate.split("-")[2]));
      LocalDate now = LocalDate.now();
      int age = Period.between(birthdate, now).getYears();

      ClientRecord clientRecordToUpdate = list.stream()
        .filter(client -> client.id.equals(id))
        .findFirst()
        .get();

      int indexRecordToUpdate = list.indexOf(clientRecordToUpdate);

      CharacterRecord character = listCharacters.stream()
        .filter(ch -> ch.id.equals(clientDisplay.character))
        .findFirst()
        .get();

      list.set(indexRecordToUpdate, new ClientRecord(
        clientRecordToUpdate.id, clientDisplay.surname + " " + clientDisplay.name + " " + clientDisplay.patronymic, character.value, "" + age, "0", "1000", "0"
      ));

      return clientDisplay;
    }
  }

  @Override
  public ClientDisplay get(String id) {
    return listDetails.stream()
      .filter(client -> client.id.equals(id))
      .findFirst()
      .get();
  }

  @Override
  public ClientDisplay delete(String id) {
    ClientDisplay clientToRemove = listDetails.stream()
      .filter(client -> client.id.equals(id))
      .findFirst()
      .get();

    list.remove(list.stream()
      .filter(client -> client.id.equals(id))
      .findFirst()
      .get());

    listDetails.remove(clientToRemove);
    return clientToRemove;
  }

  static List<ClientRecord> sort(ClientToFilter filter) {
    Comparator<ClientRecord> comparator = Comparator.comparing((ClientRecord clientRecord) -> clientRecord.id);

    if (filter.target.equalsIgnoreCase("fio")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> clientRecord.fio);
    } else if (filter.target.equalsIgnoreCase("age")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.age));
    } else if (filter.target.equalsIgnoreCase("balance")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.balance));
    } else if (filter.target.equalsIgnoreCase("balanceMax")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.balanceMax));
    } else if (filter.target.equalsIgnoreCase("balanceMin")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.balanceMin));
    }

    if (!filter.type.equalsIgnoreCase("asc")) {
      comparator = comparator.reversed();
    }

    return list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(filter.query.toLowerCase())).sorted(comparator).collect(Collectors.toList());
  }
}
