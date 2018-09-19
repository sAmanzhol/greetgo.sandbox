package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Bean
public class ClientRegisterImpl implements ClientRegister {

  private List<ClientRecord> list = Arrays.asList(
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
  );

  @Override
  public List<ClientRecord> list(String target, String type, String query) {
    List<ClientRecord> collect;

    if(target.equalsIgnoreCase("fio")){
      if (type.equalsIgnoreCase("asc")) {
        collect = list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(query.toLowerCase())).sorted(Comparator.comparing((ClientRecord clientRecord) -> clientRecord.fio)).collect(Collectors.toList());
      } else {
        collect = list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(query.toLowerCase())).sorted(Comparator.comparing((ClientRecord clientRecord)-> clientRecord.fio).reversed()).collect(Collectors.toList());
      }
    } else if (target.equalsIgnoreCase("age")) {
      if (type.equalsIgnoreCase("asc")) {
        collect = list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(query.toLowerCase())).sorted(Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.age))).collect(Collectors.toList());
      } else {
        collect = list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(query.toLowerCase())).sorted(Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.age)).reversed()).collect(Collectors.toList());
      }
    } else if (target.equalsIgnoreCase("balance")) {
      if (type.equalsIgnoreCase("asc")) {
        collect = list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(query.toLowerCase())).sorted(Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.balance))).collect(Collectors.toList());
      } else {
        collect = list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(query.toLowerCase())).sorted(Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.balance)).reversed()).collect(Collectors.toList());
      }
    } else if (target.equalsIgnoreCase("balanceMax")) {
      if (type.equalsIgnoreCase("asc")) {
        collect = list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(query.toLowerCase())).sorted(Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.balanceMax))).collect(Collectors.toList());
      } else {
        collect = list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(query.toLowerCase())).sorted(Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.balanceMax)).reversed()).collect(Collectors.toList());
      }
    } else if (target.equalsIgnoreCase("balanceMin")) {
      if (type.equalsIgnoreCase("asc")) {
        collect = list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(query.toLowerCase())).sorted(Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.balanceMin))).collect(Collectors.toList());
      } else {
        collect = list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(query.toLowerCase())).sorted(Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.balanceMin)).reversed()).collect(Collectors.toList());
      }
    } else {
      collect = list.stream().filter(clientRecord -> clientRecord.fio.toLowerCase().contains(query.toLowerCase())).sorted(Comparator.comparing((ClientRecord clientRecord) -> Integer.parseInt(clientRecord.id))).collect(Collectors.toList());
    }

    return collect;
  }
}
