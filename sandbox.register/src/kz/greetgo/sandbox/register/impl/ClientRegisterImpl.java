package kz.greetgo.sandbox.register.impl;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.Character;
import kz.greetgo.sandbox.controller.model.db.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dao.ClientDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Bean
public class ClientRegisterImpl implements ClientRegister {

  List<Character> characters = null;
  List<Gender> genders = null;
  List<PhoneDetail> phoneDetails = null;
  public BeanGetter<ClientDao> clientDao;
  int count = 0;


  public BeanGetter<Jdbc> jdbc;

  @Override
  public ClientDetail getClientDetailById(int id) {

    this.genders = new ArrayList<>();
    for (GenderType type : GenderType.values()) {
      this.genders.add(new Gender(type, type.name));
    }
    this.characters = new ArrayList<>();
    for (CharacterType t : CharacterType.values()) {
      int charmId = clientDao.get().getCharmByName(t.toString());
      characters.add(new Character(charmId, t.name));
    }

    this.phoneDetails = new ArrayList<>();
    for (PhoneTypeDb ph : PhoneTypeDb.values()) {
      this.phoneDetails.add(new PhoneDetail(ph, ph.name));
    }

    if (id == 0 || id == -1) {
      return ClientDetail.forSave(this.genders, this.characters, this.phoneDetails);
    } else {
      ClientDb client = clientDao.get().getClientDb(id);
      Address regAddress = clientDao.get().getAddress(client.id, "REG");
      Address factAddress = clientDao.get().getAddress(client.id, "FACT");
      List<ClientPhoneDb> phoneList = clientDao.get().getPhoneList(client.id);
      CharmDb charmDb = clientDao.get().getCharm(client.id);

      Character cType;
      if (CharacterType.parseOrNull(charmDb.name) != null) {
        cType = new Character(charmDb.id, CharacterType.parseOrNull(charmDb.name).name);
      } else {
        cType = new Character(charmDb.id, "");
      }

      List<Phone> phones = new ArrayList<>();
      for (ClientPhoneDb phone : phoneList) {
        if (PhoneTypeDb.parseOrNull(phone.type) != null) {
          Phone ph = new Phone(new PhoneDetail(PhoneTypeDb.parseOrNull(phone.type),
            PhoneTypeDb.parseOrNull(phone.type).name), phone.number, phone.number);
          phones.add(ph);
        }
      }


      ClientDetail clientDetail = new ClientDetail(client.surname, client.name, client.patronymic,
        new Gender(client.gender, client.gender.toString()), this.genders, client.birthDate,
        cType, this.characters, factAddress, regAddress, phones, this.phoneDetails, client.id);

      return clientDetail;
    }
  }


  @Override
  public ClientRecord saveClient(ClientToSave toSave) {
    ClientRecord clientRecord = new ClientRecord();
    ClientDb client = new ClientDb();

    client = client.convertToSaveToClient(toSave);

    if (toSave.clientID == -1 || toSave.clientID == 0) {
      toSave.clientID = clientDao.get().insertClient(client);
      client.id = toSave.clientID;
    }
    String characterName = clientDao.get().getCharmName(toSave.character.id);
    if (CharacterType.parseOrNull(characterName) != null) {
      toSave.character.type = CharacterType.parseOrNull(characterName).name;
    }
    clientDao.get().saveOrUpdateClient(client);

    ClientAddrDb address = new ClientAddrDb();
    address = address.getAddressFromToSave(toSave, "REG");
    if (!"".equals(address.street) && !"".equals(address.house)) {
      clientDao.get().saveOrUpdateAddress(address);
    }
    address = address.getAddressFromToSave(toSave, "FACT");
    if (!"".equals(address.street) && !"".equals(address.house)) {
      clientDao.get().saveOrUpdateAddress(address);
    }


    List<Phone> phoneList = Phone.getPhoneListFromToSave(toSave);
    for (Phone phone : phoneList) {

      if (phone.oldNumber == null) {
        phone.oldNumber = phone.number;
      }
      clientDao.get().deactualPhone(toSave.clientID, phone.oldNumber);
      if ("-1".equals(phone.oldNumber)) {
        clientDao.get().deactualPhone(toSave.clientID, phone.number);
      } else {//!Objects.equals(phone.oldNumber, phone.number)) {
        clientDao.get().saveOrUpdatePhone(toSave.clientID, phone.number, phone.detail.type.toString());
      }
    }

    List<ClientPhoneDb> phoneDb = clientDao.get().getPhoneList(toSave.clientID);

    toSave.phones = Phone.getPhoneListFromDb(phoneDb);

    return clientRecord.convertToSaveToClientRecord(toSave);
  }

  @Override
  public void deleteClient(int id) {

    List<ClientPhoneDb> phoneDb = this.clientDao.get().getPhoneList(id);
    this.clientDao.get().deactualClient(id);
    List<Integer> accounts = this.clientDao.get().deactualAccounts(id);
    for (Integer acc : accounts) {
      this.clientDao.get().deactualTransactions(acc);
    }
    this.clientDao.get().deactualAddress(id, "REG");
    this.clientDao.get().deactualAddress(id, "FACT");
    for (ClientPhoneDb phone : phoneDb) {
      this.clientDao.get().deactualPhone(phone.client, phone.number);
    }
  }


  private ClientRecordListWrapper getRecordsFromDb(ClientFilter clientFilter) {

    List<ClientRecord> recordList = new ArrayList<>();

    String sq = "select\n" +
      "  x1.surname,\n" +
      "  x1.name,\n" +
      "  x1.patronymic,\n" +
      "  x1.birth_date ,\n" +
      "  x.sum,\n" +
      "  x.min,\n" +
      "  x.max,\n" +
      "  x1.id,\n" +
      "  x2.name,\n" +
      "  x2.id,\n" +
      "  count(x1.id)\n" +
      "  over (\n" +
      "    partition by 1 )\n" +
      "from (\n" +
      "       select\n" +
      "         max(a.money),\n" +
      "         min(a.money),\n" +
      "         sum(a.money),\n" +
      "         c.id\n" +
      "       from client as c\n" +
      "         left join client_account as a\n" +
      "           on c.id = a.client\n" +
      "       group by c.id\n" +
      "     ) x\n" +
      "  join client x1 on x1.id = x.id\n" +
      "  join charm x2 on x2.id = x1.charm\n" +
      "where";

    ArrayList<String> prepareStValue = new ArrayList<>();
    if (!"".equals(clientFilter.name)) {
      sq += " lower(x1.name) like ?\n";
      prepareStValue.add("%" + clientFilter.name.toLowerCase() + "%");
    }
    if (!"".equals(clientFilter.surname)) {
      if (prepareStValue.size() != 0) {
        sq += " and";
      }
      sq += " lower(x1.surname) like ?\n";
      prepareStValue.add("%" + clientFilter.surname.toLowerCase() + "%");
    }
    if (!"".equals(clientFilter.patronymic)) {
      if (prepareStValue.size() != 0) {
        sq += " and";
      }
      sq += " lower(x1.patronymic) like ?\n";
      prepareStValue.add("%" + clientFilter.patronymic.toLowerCase() + "%");
    }
    if (prepareStValue.size() != 0) {
      sq += " and";
    }
    sq += " x1.actual = true\n";

    if (!"".equals(clientFilter.columnName) && clientFilter.columnName != null) {
      sq += "order by \n";
      if ("fio".equals(clientFilter.columnName)) {
        sq = sq.concat("x1.surname ");
        if (clientFilter.isAsc) {
          sq = sq.concat("asc, ");
        } else {
          sq = sq.concat("desc, ");
        }
        sq = sq.concat("x1.name ");
        if (clientFilter.isAsc) {
          sq = sq.concat("asc, ");
        } else {
          sq = sq.concat("desc, ");
        }
        sq = sq.concat("x1.patronymic ");
        if (clientFilter.isAsc) {
          sq = sq.concat("asc\n");
        } else {
          sq = sq.concat("desc\n");
        }
      } else if ("age".equals(clientFilter.columnName)) {
        sq = sq.concat("x1.birth_date ");
        if (clientFilter.isAsc) {
          sq = sq.concat("asc\n");
        } else {
          sq = sq.concat("desc\n");
        }
      } else if ("tot".equals(clientFilter.columnName)) {
        sq = sq.concat("x.sum ");
        if (clientFilter.isAsc) {
          sq = sq.concat("asc\n");
        } else {
          sq = sq.concat("desc\n");
        }
      } else if ("min".equals(clientFilter.columnName)) {
        sq = sq.concat("x.min ");
        if (clientFilter.isAsc) {
          sq = sq.concat("asc\n");
        } else {
          sq = sq.concat("desc\n");
        }
      } else if ("max".equals(clientFilter.columnName)) {
        sq = sq.concat("x.max ");
        if (clientFilter.isAsc) {
          sq = sq.concat("asc\n");
        } else {
          sq = sq.concat("desc\n");
        }
      }
    }
    if (clientFilter.offset >= 0) {
      sq += "offset ";
      sq = sq.concat(Integer.toString(clientFilter.offset * clientFilter.limit) + "\n");
    }

    if (clientFilter.limit >= 0) {
      sq += "limit ";
      sq = sq.concat(Integer.toString(clientFilter.limit) + ";");
    }
    final String newSQL = sq;

//    System.out.println("newSQL: " + newSQL);
    jdbc.get().execute(con -> {
      try (PreparedStatement ps = con.prepareStatement(newSQL)) {
        for (int i = 0; i < prepareStValue.size(); i++) {
          ps.setObject(i + 1, prepareStValue.get(i));
        }
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            ClientRecord clientRecord = new ClientRecord();
            clientRecord.fio = rs.getString(1) + " "
              + rs.getString(2);
            if(rs.getString(3) != null)
              clientRecord.fio += " " + rs.getString(3);

            Date d = rs.getDate(4);
            LocalDate currentDate = LocalDate.now();

            LocalDate birthDate = Instant.ofEpochMilli(d.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if ((birthDate != null) && (currentDate != null)) {
              clientRecord.age = Period.between(birthDate, currentDate).getYears();
            } else {
              clientRecord.age = 0;
            }

            clientRecord.totalBalance = (int) rs.getDouble(5);
            clientRecord.minBalance = (int) rs.getFloat(6);
            clientRecord.maxBalance = (int) rs.getFloat(7);
            clientRecord.clientId = rs.getInt(8);

            if (CharacterType.parseOrNull(rs.getString(9)) != null) {
              CharacterType ch = CharacterType.parseOrNull(rs.getString(9));
              clientRecord.character = new Character(rs.getInt(10), ch.name);
            } else {
              clientRecord.character = new Character(11, "невротичный");
            }
            count = rs.getInt(11);
            recordList.add(clientRecord);
          }
        }
      }
      return null;
    });


    return new ClientRecordListWrapper(recordList, count);
  }


  @Override
  public ClientRecordListWrapper filterClients(ClientFilter clientFilter) {
    return this.getRecordsFromDb(clientFilter);
  }
}
