package kz.greetgo.sandbox.register.impl.jdbc.migration;

import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaAddress;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaClient;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaPhone;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class CiaMigrationCallbackImpl extends MigrationCallbackAbstract<Void> {

  private String filePath;

  public CiaMigrationCallbackImpl(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public void createTempTables() throws Exception {

    final String clientTempTableCreate =
      "create table client_temp (" +
        " id varchar(100), " +
        " surname varchar(100), " +
        " name varchar(100), " +
        " patronymic varchar(100), " +
        " gender varchar(100), " +
        " birth_date varchar(100), " +
        " charm varchar(100), " +
        " status int default 1" +
        ")";

    final String clientPhoneTempTableCreate =
      "create table client_phone_temp (" +
        " type varchar(100), " +
        " client varchar(100), " +
        " number varchar(100), " +
        " status int default 1" +
        ")";

    final String clientAddressTempTableCreate =
      "create table client_addr_temp (" +
        " type varchar(100), " +
        " client varchar(100), " +
        " street varchar(100), " +
        " house varchar(100), " +
        " flat varchar(100), " +
        " status int default 1" +
        ")";

    try (PreparedStatement ps = connection.prepareStatement(clientTempTableCreate)) {
      ps.executeUpdate();
    }

    try (PreparedStatement ps = connection.prepareStatement(clientPhoneTempTableCreate)) {
      ps.executeUpdate();
    }

    try (PreparedStatement ps = connection.prepareStatement(clientAddressTempTableCreate)) {
      ps.executeUpdate();
    }
  }

  @Override
  public void parseAndFillData() throws Exception {

    String clientTempTableInsert =
      "insert into client_temp (id, surname, name, patronymic, gender, birth_date, charm) " +
        " values (?, ?, ?, ?, ?, ?, ?)";

    String clientPhoneTempTableInsert =
      "insert into client_phone_temp (client, type, number) " +
        " values (?, ?, ?)";

    String clientAddressTempTableInsert =
      "insert into client_addr_temp (client, type, street, house, flat) " +
        " values (?, ?, ?, ?, ?)";


    File ciaFile = new File(this.filePath);
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(ciaFile);

    NodeList clientNodes = doc.getElementsByTagName("client");

    for (int clientIterator = 0; clientIterator < clientNodes.getLength(); clientIterator++) {
      Node curClientNode = clientNodes.item(clientIterator);
      Element curClientElement = (Element) curClientNode;

      CiaClient ciaClient = new CiaClient();

      ciaClient.id = curClientElement.getAttribute("id");

      if (curClientElement.getElementsByTagName("surname").item(0) != null) {
        ciaClient.surname = ((Element) curClientElement.getElementsByTagName("surname").item(0)).getAttribute("value");
      }
      if (curClientElement.getElementsByTagName("name").item(0) != null) {
        ciaClient.name = ((Element) curClientElement.getElementsByTagName("name").item(0)).getAttribute("value");
      }
      if ((curClientElement.getElementsByTagName("patronymic").item(0)) != null) {
        ciaClient.patronymic = ((Element) curClientElement.getElementsByTagName("patronymic").item(0)).getAttribute("value");
      }
      if (curClientElement.getElementsByTagName("gender").item(0) != null) {
        ciaClient.gender = ((Element) curClientElement.getElementsByTagName("gender").item(0)).getAttribute("value");
      }
      if (curClientElement.getElementsByTagName("charm").item(0) != null) {
        ciaClient.charm = ((Element) curClientElement.getElementsByTagName("charm").item(0)).getAttribute("value");
      }
      if (curClientElement.getElementsByTagName("birth").item(0) != null) {
        ciaClient.birth = ((Element) curClientElement.getElementsByTagName("birth").item(0)).getAttribute("value");
      }

      try (PreparedStatement ps = connection.prepareStatement(clientTempTableInsert)) {
        ps.setObject(1, ciaClient.id);
        ps.setObject(2, ciaClient.surname);
        ps.setObject(3, ciaClient.name);
        ps.setObject(4, ciaClient.patronymic);
        ps.setObject(5, ciaClient.gender);
        ps.setObject(6, ciaClient.birth);
        ps.setObject(7, ciaClient.charm);

        ps.executeUpdate();
      }

      ciaClient.addresses = new ArrayList<>();

      parseCiaClientAddresses(ciaClient.addresses, curClientElement, "fact");
      parseCiaClientAddresses(ciaClient.addresses, curClientElement, "register");

      for (CiaAddress ciaAddress : ciaClient.addresses) {
        try (PreparedStatement ps = connection.prepareStatement(clientAddressTempTableInsert)) {
          ps.setObject(1, ciaClient.id);
          ps.setObject(2, ciaAddress.type);
          ps.setObject(3, ciaAddress.street);
          ps.setObject(4, ciaAddress.house);
          ps.setObject(5, ciaAddress.flat);

          ps.executeUpdate();
        }
      }

      ciaClient.phones = new ArrayList<>();

      parseCiaClientPhones(ciaClient.phones, curClientElement, "homePhone");
      parseCiaClientPhones(ciaClient.phones, curClientElement, "mobilePhone");
      parseCiaClientPhones(ciaClient.phones, curClientElement, "workPhone");

      for (CiaPhone ciaPhone : ciaClient.phones) {
        try (PreparedStatement ps = connection.prepareStatement(clientPhoneTempTableInsert)) {
          ps.setObject(1, ciaClient.id);
          ps.setObject(2, ciaPhone.type);
          ps.setObject(3, ciaPhone.number);

          ps.executeUpdate();
        }
      }
    }
  }

  private void parseCiaClientPhones(List<CiaPhone> phones, Element curClientElement, String phoneType) {
    NodeList curClientElementHomePhones = curClientElement.getElementsByTagName(phoneType);

    for (int homePhoneIterator = 0; homePhoneIterator < curClientElementHomePhones.getLength(); homePhoneIterator++) {
      Node curHomePhoneNode = curClientElementHomePhones.item(homePhoneIterator);
      Element curHomePhoneElement = (Element) curHomePhoneNode;

      CiaPhone ciaPhone = new CiaPhone();

      if (curHomePhoneElement.getNodeName().equals("homePhone")) {
        ciaPhone.type = "HOME";
      } else if (curHomePhoneElement.getNodeName().equals("workPhone")) {
        ciaPhone.type = "WORK";
      } else if (curHomePhoneElement.getNodeName().equals("mobilePhone")) {
        ciaPhone.type = "MOBILE";
      }
      ciaPhone.number = curHomePhoneElement.getTextContent();

      phones.add(ciaPhone);
    }
  }

  private void parseCiaClientAddresses(List<CiaAddress> addresses, Element curClientElement, String addressType) {
    Element curAddressElement = (Element) curClientElement.getElementsByTagName(addressType).item(0);

    CiaAddress ciaAddress = new CiaAddress();
    if ("fact".equals(addressType)) {
      ciaAddress.type = "FACT";
    } else if ("register".equals(addressType)) {
      ciaAddress.type = "REG";
    }
    ciaAddress.street = curAddressElement.getAttribute("street");
    ciaAddress.house = curAddressElement.getAttribute("house");
    ciaAddress.flat = curAddressElement.getAttribute("flat");

    addresses.add(ciaAddress);
  }

  @Override
  public void validateAndMigrateData() throws Exception {

    // First part getting rid of records with error.
    // Set status = 2 (Records with errors)

    this.checkForValidness();


    // Adding new charms

    String CharmTableInsert =
      "insert into charm (name, id, description, energy) " +
        " select " +
        "   distinct charm as name, " +
        "   nextval('id') as id, " +
        "   'Description' as description, " +
        "   100 as energy " +
        " from client_temp " +
        " where charm notnull and status = 1" +
        " group by charm " +
        "on conflict (name) do nothing";

    try (PreparedStatement ps = connection.prepareStatement(CharmTableInsert)) {
      ps.executeUpdate();
    }


    // Migrate valid clients without phone and address

    // And there is just insert, because problems with update-insert
    String clientTableUpdateMigrate =
      "insert into client (id, surname, name, patronymic, gender, birth_date, charm, migration_id) " +
        "   select " +
        "     nextval('id') as id, " +
        "     surname, " +
        "     name, " +
        "     patronymic, " +
        "     gender::gender as gender, " +
        "     to_date(birth_date, 'YYYY-MM-DD') as birth_date, " +
        "     (select id from charm where name = client_temp.charm) as charm, " +
        "     id as migration_id " +
        "   from client_temp " +
        "   where status = 1 " +
        " on conflict on constraint migration_id " +
        " do nothing";
//        " do update set " +
//        "  surname = excluded.surname," +
//        "  name = excluded.name, " +
//        "  patronymic = excluded.patronymic, " +
//        "  gender = excluded.gender, " +
//        "  birth_date = excluded.birth_date, " +
//        "  charm = excluded.charm";

    try (PreparedStatement ps = connection.prepareStatement(clientTableUpdateMigrate)) {
      ps.executeUpdate();
    }


    String clientPhoneTableUpdateMigrate =
      "insert into client_phone (id, client, type, number) " +
        "   select " +
        "     nextval('id') as id, " +
        "     (select id from client where migration_id = client_phone_temp.client) as client, " +
        "     type::phone as type, " +
        "     number as number " +
        "   from client_phone_temp " +
        "   where status = 1 and client notnull " +
        "on conflict (number) do nothing";

    try (PreparedStatement ps = connection.prepareStatement(clientPhoneTableUpdateMigrate)) {
      ps.executeUpdate();
    }


    String clientAddrTableUpdateMigrate =
      "insert into client_addr (client, type, street, house, flat) " +
        "   select " +
        "     coalesce( nullif((select id from client where migration_id = client_addr_temp.client and actual = 1), null), -1), " +
        "     type::addr as type, " +
        "     street as street, " +
        "     house as house, " +
        "     flat as flat " +
        "   from client_addr_temp " +
        "   where status = 1 and client notnull " +
        "on conflict (client, type) do nothing";

    try (PreparedStatement ps = connection.prepareStatement(clientAddrTableUpdateMigrate)) {
      ps.executeUpdate();
    }
  }

  /*
    Function for checking records for validness, if there some error than changes it`s status to 2
  */
  private void checkForValidness() throws Exception {

    String clientTempTableUpdateError =
      "update client_temp set status = 2 " +
        " where surname = '' or name = '' or gender = '' or charm = '' or birth_date = '' " +
        " or birth_date not like '%-%-%' " +
        " or (extract(year from age(to_timestamp(birth_date, 'YYYY-MM-DD')))) < 3 " +
        " or (extract(year from age(to_timestamp(birth_date, 'YYYY-MM-DD')))) > 3000";

    try (PreparedStatement ps = connection.prepareStatement(clientTempTableUpdateError)) {
      ps.executeUpdate();
    }


    String clientPhoneTempTableUpdateError =
      "update client_phone_temp set status = 2 " +
        " where type = '' or client = '' or number = ''";

    try (PreparedStatement ps = connection.prepareStatement(clientPhoneTempTableUpdateError)) {
      ps.executeUpdate();
    }


    String clientAddrTempTableUpdateError =
      "update client_addr_temp set status = 2 " +
        " where type = '' or client = '' or street = '' or house = '' or flat = ''";

    try (PreparedStatement ps = connection.prepareStatement(clientAddrTempTableUpdateError)) {
      ps.executeUpdate();
    }
  }

  @Override
  public void dropTemplateTables() throws Exception {

    final String clientTempTableDrop = "drop table client_temp";

    final String clientPhoneTempTableDrop = "drop table client_phone_temp";

    final String clientAddressTempTableDrop = "drop table client_addr_temp";

    try (PreparedStatement ps = connection.prepareStatement(clientTempTableDrop)) {
      ps.executeUpdate();
    }

    try (PreparedStatement ps = connection.prepareStatement(clientPhoneTempTableDrop)) {
      ps.executeUpdate();
    }

    try (PreparedStatement ps = connection.prepareStatement(clientAddressTempTableDrop)) {
      ps.executeUpdate();
    }
  }

  /*
   Function for checking records for dependencies, if there some error than changes it`s actual to 0 until there will be valid dependency
 */
  @Override
  public void disableUnusedRecords() throws Exception {

    String clientPhoneTableUpdateDisable =
      "update client_phone " +
        "set actual = 0 " +
        "where client isnull and actual = 1";

    try (PreparedStatement ps = connection.prepareStatement(clientPhoneTableUpdateDisable)) {
      ps.executeUpdate();
    }


    String clientAddrTableUpdateDisable =
      "update client_addr " +
        "set actual = 0 " +
        "where client = -1 and actual = 1";

    try (PreparedStatement ps = connection.prepareStatement(clientAddrTableUpdateDisable)) {
      ps.executeUpdate();
    }
  }

  /*
   Function for checking new records that needed for disabled data, if there exists than we enable disabled records
 */
  @Override
  public void checkForLateUpdates() throws Exception {

  }
}