package kz.greetgo.sandbox.register.impl.jdbc.migration.handler;

import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaAddress;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaClient;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaPhone;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class CiaHandler extends DefaultHandler {

  private CiaClient client = null;
  public List<CiaClient> clients = new ArrayList<>();
  public List<CiaPhone> phones = new ArrayList<>();
  public List<CiaAddress> addresses = new ArrayList<>();

  boolean bHomePhone = false;
  boolean bMobilePhone = false;
  boolean bWorkPhone = false;

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
  public void endElement(String uri, String localName, String qName) throws SAXException {
    // End tag
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
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
}
