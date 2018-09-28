package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

// FIXME: 9/24/18 Избавься от варнингов в коде

@Bean
public class ClientRegisterImpl implements ClientRegister {
  private static Calendar calendar = new GregorianCalendar();

  private static List<CharacterRecord> listCharacters = new ArrayList<>(Arrays.asList(
    new CharacterRecord(1, "Самовлюблённый"),
    new CharacterRecord(2, "Замкнутый"),
    new CharacterRecord(3, "Великодушный"),
    new CharacterRecord(4, "Бессердечный"),
    new CharacterRecord(5, "Грубый"),
    new CharacterRecord(6, "Целеустремлённый"),
    new CharacterRecord(7, "Мизантроп"),
    new CharacterRecord(8, "Строгий"),
    new CharacterRecord(9, "Гениальный"),
    new CharacterRecord(10, "Харизматичный"),
    new CharacterRecord(11, "Безответственный")
  ));

  private static List<ClientRecord> list = new ArrayList<>(Arrays.asList(
    new ClientRecord(1, "Колобова Розалия Наумовна", "Самовлюблённый", 37, 332, 234324234, 345),
    new ClientRecord(2, "Панова Алира Иосифовна", "Замкнутый", 29, 4324, 32423424, 4),
    new ClientRecord(3, "Крюков Игнатий Улебович", "Великодушный", 30, 234, 234234324, 467),
    new ClientRecord(4, "Киселёв Юлиан Романович", "Бессердечный", 23, 32, 32435, 3),
    new ClientRecord(5, "Исаева Ирина Сергеевна", "Грубый", 18, 4324, 6878768, 234),
    new ClientRecord(6, "Большаков Мечеслав Куприянович", "Целеустремлённый", 56, 100, 786786, 73),
    new ClientRecord(7, "Корнилов Захар Федосеевич", "Мизантроп", 47, 8344, 6546546, 45),
    new ClientRecord(8, "Лихачёв Исак Кириллович", "Строгий", 27, 34534, 5464564, 87),
    new ClientRecord(9, "Фёдорова Эмбер Руслановна", "Гениальный", 21, 4357, 546456546, 12),
    new ClientRecord(10, "Баранова Габриэлла Романовна", "Харизматичный", 41, 3454, 7686543, 43),
    new ClientRecord(11, "Никонов Лев Викторович", "Безответственный", 22, 434, 546758, 54)
  ));

  private static List<ClientDisplay> listDetails = new ArrayList<>(Arrays.asList(
    new ClientDisplay(1, "Колобова", "Розалия", "Наумовна", new GregorianCalendar(2000, 8, 28).getTime(), "female", 1, "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Arrays.asList(new PhoneDisplay(1, "Home", "77077077777"), new PhoneDisplay(2, "Work", "77877077777"), new PhoneDisplay(3, "Mobile", "77007077777")))),
    new ClientDisplay(2, "Панова", "Алира", "Иосифовна", new GregorianCalendar(2000, 8, 28).getTime(), "female", 2, "Пр.абылай Хана", "74", "140", "", "", "", new ArrayList<>(Collections.singletonList(new PhoneDisplay(1, "Home", "77077077777")))),
    new ClientDisplay(3, "Крюков", "Игнатий", "Улебович", new GregorianCalendar(2000, 8, 28).getTime(), "male", 3, "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Collections.singletonList(new PhoneDisplay(1, "Home", "87077077777")))),
    new ClientDisplay(4, "Киселёв", "Юлиан", "Романович", new GregorianCalendar(2000, 8, 28).getTime(), "male", 4, "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Collections.singletonList(new PhoneDisplay(1, "Home", "87077077777")))),
    new ClientDisplay(5, "Исаева", "Ирина", "Сергеевна", new GregorianCalendar(2000, 8, 28).getTime(), "female", 5, "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Collections.singletonList(new PhoneDisplay(1, "Home", "87077077777")))),
    new ClientDisplay(6, "Большаков", "Мечеслав", "Куприянович", new GregorianCalendar(2000, 8, 28).getTime(), "male", 6, "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Collections.singletonList(new PhoneDisplay(1, "Home", "87077077777")))),
    new ClientDisplay(7, "Корнилов", "Захар", "Федосеевич", new GregorianCalendar(2000, 8, 28).getTime(), "male", 7, "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Collections.singletonList(new PhoneDisplay(1, "Home", "87077077777")))),
    new ClientDisplay(8, "Лихачёв", "Исак", "Кириллович", new GregorianCalendar(2000, 8, 28).getTime(), "male", 8, "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Collections.singletonList(new PhoneDisplay(1, "Home", "87077077777")))),
    new ClientDisplay(9, "Фёдорова", "Эмбер", "Руслановна", new GregorianCalendar(2000, 8, 28).getTime(), "female", 9, "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Collections.singletonList(new PhoneDisplay(1, "Home", "87077077777")))),
    new ClientDisplay(10, "Баранова", "Габриэлла", "Романовна", new GregorianCalendar(2000, 8, 28).getTime(), "female", 10, "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Collections.singletonList(new PhoneDisplay(1, "Home", "87077077777")))),
    new ClientDisplay(11, "Никонов", "Лев", "Викторович", new GregorianCalendar(2000, 8, 28).getTime(), "male", 11, "Ломоносова", "11", "9", "", "", "", new ArrayList<>(Collections.singletonList(new PhoneDisplay(1, "Home", "87077077777"))))
  ));

  @Override
  public List<ClientRecord> list(ClientToFilter filter) {
    List<ClientRecord> sortedList = sort(filter);
    System.out.println(sortedList);

    int listLength = sortedList.size();
    int startIndex = (filter.page - 1) * filter.itemCount;
    int endIndex = startIndex + filter.itemCount < listLength ? startIndex + filter.itemCount : listLength;

    return sortedList.subList(startIndex, endIndex);
  }

  @Override
  public int count(ClientToFilter filter) {
    List<ClientRecord> sortedList = sort(filter);
    return sortedList.size();
  }

  @Override
  public ClientRecord save(ClientToSave clientToSave) {

    if (clientToSave.id != null) {
      ClientDisplay clientDisplay = new ClientDisplay(clientToSave.id, clientToSave.surname, clientToSave.name, clientToSave.patronymic, clientToSave.birthDate, clientToSave.gender, clientToSave.characterId, clientToSave.streetRegistration, clientToSave.houseRegistration, clientToSave.apartmentRegistration, clientToSave.streetResidence, clientToSave.houseResidence, clientToSave.apartmentResidence, clientToSave.numbers);

      ClientDisplay clientToUpdate = listDetails.stream()
        .filter(client -> client.id == clientToSave.id)
        .findFirst()
        .orElse(new ClientDisplay());

      int indexToUpdate = listDetails.indexOf(clientToUpdate);

      listDetails.set(indexToUpdate, clientDisplay);

      calendar.setTime(clientDisplay.birthDate);
      LocalDate birthdate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
      LocalDate now = LocalDate.now();
      int age = Period.between(birthdate, now).getYears();

      ClientRecord clientRecordToUpdate = list.stream()
        .filter(client -> client.id == clientToSave.id)
        .findFirst()
        .orElse(new ClientRecord());

      int indexRecordToUpdate = list.indexOf(clientRecordToUpdate);

      CharacterRecord character = listCharacters.stream()
        .filter(ch -> ch.id == clientDisplay.characterId)
        .findFirst()
        .orElse(new CharacterRecord());

      ClientRecord clientRecord = new ClientRecord(
        clientRecordToUpdate.id, clientDisplay.surname + " " + clientDisplay.name + " " + clientDisplay.patronymic, character.name, age, 0, 1000, 0
      );

      list.set(indexRecordToUpdate, clientRecord);

      return clientRecord;
    } else {
      int newId = list.get(list.size() - 1).id + 1;
      ClientDisplay clientDisplay = new ClientDisplay(newId, clientToSave.surname, clientToSave.name, clientToSave.patronymic, clientToSave.birthDate, clientToSave.gender, clientToSave.characterId, clientToSave.streetRegistration, clientToSave.houseRegistration, clientToSave.apartmentRegistration, clientToSave.streetResidence, clientToSave.houseResidence, clientToSave.apartmentResidence, clientToSave.numbers);

      listDetails.add(clientDisplay);

      calendar.setTime(clientDisplay.birthDate);
      LocalDate birthdate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
      LocalDate now = LocalDate.now();
      int age = Period.between(birthdate, now).getYears();

      CharacterRecord character = listCharacters.stream()
        .filter(ch -> ch.id == clientDisplay.characterId)
        .findFirst()
        .orElse(new CharacterRecord());

      ClientRecord clientRecord = new ClientRecord(
        newId, clientDisplay.surname + " " + clientDisplay.name + " " + clientDisplay.patronymic, character.name, age, 0, 1000, 0
      );

      list.add(clientRecord);

      return clientRecord;
    }
  }

  @Override
  public ClientDisplay details(int id) {
    return listDetails.stream()
      .filter(client -> client.id == id)
      .findFirst()
      .orElse(new ClientDisplay());
  }

  @Override
  public void delete(int id) {
    ClientDisplay clientToRemove = listDetails.stream()
      .filter(client -> client.id == id)
      .findFirst()
      .orElse(new ClientDisplay());

    list.remove(list.stream()
      .filter(client -> client.id == id)
      .findFirst()
      .orElse(new ClientRecord()));

    listDetails.remove(clientToRemove);
  }

  private static List<ClientRecord> sort(ClientToFilter filter) {
    Comparator<ClientRecord> comparator = Comparator.comparing((ClientRecord clientRecord) -> clientRecord.id);

    if (filter.sortColumn.equalsIgnoreCase("fio")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> clientRecord.fio);
    } else if (filter.sortColumn.equalsIgnoreCase("age")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> clientRecord.age);
    } else if (filter.sortColumn.equalsIgnoreCase("balance")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> clientRecord.balance);
    } else if (filter.sortColumn.equalsIgnoreCase("balanceMax")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> clientRecord.balanceMax);
    } else if (filter.sortColumn.equalsIgnoreCase("balanceMin")) {
      comparator = Comparator.comparing((ClientRecord clientRecord) -> clientRecord.balanceMin);
    }

    if (!filter.sortDirection.equalsIgnoreCase("asc")) {
      comparator = comparator.reversed();
    }

    return list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(filter.fio.toLowerCase())).sorted(comparator).collect(Collectors.toList());
  }
}
