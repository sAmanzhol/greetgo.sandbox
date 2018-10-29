package kz.greetgo.sandbox.register.impl.jdbc.migration.handler;

import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaAddress;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaClient;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaPhone;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class CiaHandler extends DefaultHandler {

  public Connection connection;
  public CiaClient client = null;
  public List<CiaPhone> phones = new ArrayList<>();
  public List<CiaAddress> addresses = new ArrayList<>();

  boolean bHomePhone = false;
  boolean bMobilePhone = false;
  boolean bWorkPhone = false;

  public Integer clientCount = 0;
  public Integer addressCount = 0;
  public Integer phoneCount = 0;

  public CiaHandler(Connection con) {
    this.connection = con;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    if (qName.equalsIgnoreCase("client")) {
      client = new CiaClient();
      client.setId(attributes.getValue("id"));
    } else if (qName.equalsIgnoreCase("surname")) {
      client.setSurname(attributes.getValue("value"));
    } else if (qName.equalsIgnoreCase("name")) {
      client.setName(attributes.getValue("value"));
    } else if (qName.equalsIgnoreCase("patronymic")) {
      client.setPatronymic(attributes.getValue("value"));
    } else if (qName.equalsIgnoreCase("gender")) {
      client.setGender(attributes.getValue("value"));
    } else if (qName.equalsIgnoreCase("charm")) {
      client.setCharm(attributes.getValue("value"));
    } else if (qName.equalsIgnoreCase("birth")) {
      client.setBirthDate(attributes.getValue("value"));
    } else if (qName.equalsIgnoreCase("homePhone")) {
      bHomePhone = true;
    } else if (qName.equalsIgnoreCase("mobilePhone")) {
      bMobilePhone = true;
    } else if (qName.equalsIgnoreCase("workPhone")) {
      bWorkPhone = true;
    } else if (qName.equalsIgnoreCase("fact")) {
      CiaAddress address = new CiaAddress();
      address.client = client.getId();
      address.type = "FACT";
      address.street = attributes.getValue("street");
      address.house = attributes.getValue("house");
      address.flat = attributes.getValue("flat");

      addresses.add(address);
    } else if (qName.equalsIgnoreCase("register")) {
      CiaAddress address = new CiaAddress();
      address.client = client.getId();
      address.type = "REG";
      address.street = attributes.getValue("street");
      address.house = attributes.getValue("house");
      address.flat = attributes.getValue("flat");

      addresses.add(address);
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) {
    if (qName.equalsIgnoreCase("client")) {
      try {
        insertClient(client);

        for (CiaPhone phone : phones) {
          insertClientPhone(phone);
        }

        phones = new ArrayList<>();

        for (CiaAddress address : addresses) {
          insertClientAddress(address);
        }

        addresses = new ArrayList<>();
        client = new CiaClient();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) {
    if (bHomePhone) {
      CiaPhone phone = new CiaPhone();
      phone.client = client.getId();
      phone.type = "HOME";
      phone.number = new String(ch, start, length);

      phones.add(phone);
      bHomePhone = false;
    } else if (bMobilePhone) {
      CiaPhone phone = new CiaPhone();
      phone.client = client.getId();
      phone.type = "MOBILE";
      phone.number = new String(ch, start, length);

      phones.add(phone);
      bMobilePhone = false;
    } else if (bWorkPhone) {
      CiaPhone phone = new CiaPhone();
      phone.client = client.getId();
      phone.type = "WORK";
      phone.number = new String(ch, start, length);

      phones.add(phone);
      bWorkPhone = false;
    }
  }

  private void insertClient(CiaClient client) throws Exception {
    String clientTempTableInsert =
      "insert into client_temp (id, surname, name, patronymic, gender, birth_date, charm, migration_order) " +
        " values (?, ?, ?, ?, ?, ?, ?, nextval('migration_order'))";

    try (PreparedStatement ps = connection.prepareStatement(clientTempTableInsert)) {
      ps.setObject(1, client.id);
      ps.setObject(2, client.surname);
      ps.setObject(3, client.name);
      ps.setObject(4, client.patronymic);
      ps.setObject(5, client.gender);
      ps.setObject(6, client.birthDate);
      ps.setObject(7, client.charm);

      ps.executeUpdate();
    }

    clientCount++;
  }

  private void insertClientAddress(CiaAddress address) throws Exception {
    String clientAddressTempTableInsert =
      "insert into client_addr_temp (client, type, street, house, flat, migration_order) " +
        " values (?, ?, ?, ?, ?, currval('migration_order'))";

    try (PreparedStatement ps = connection.prepareStatement(clientAddressTempTableInsert)) {
      ps.setObject(1, address.client);
      ps.setObject(2, address.type);
      ps.setObject(3, address.street);
      ps.setObject(4, address.house);
      ps.setObject(5, address.flat);

      ps.executeUpdate();
    }

    addressCount++;
  }

  private void insertClientPhone(CiaPhone phone) throws Exception {
    String clientPhoneTempTableInsert =
      "insert into client_phone_temp (client, type, number, migration_order) " +
        " values (?, ?, ?, currval('migration_order'))";

    try (PreparedStatement ps = connection.prepareStatement(clientPhoneTempTableInsert)) {
      ps.setObject(1, phone.client);
      ps.setObject(2, phone.type);
      ps.setObject(3, phone.number);

      ps.executeUpdate();
    }

    phoneCount++;
  }
}
