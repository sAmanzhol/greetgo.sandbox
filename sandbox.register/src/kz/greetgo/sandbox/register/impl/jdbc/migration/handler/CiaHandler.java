package kz.greetgo.sandbox.register.impl.jdbc.migration.handler;

import kz.greetgo.sandbox.register.impl.jdbc.migration.model.CiaClient;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@SuppressWarnings("WeakerAccess")
public class CiaHandler extends DefaultHandler {

  private CiaClient client = null;

  boolean bSurname = false;
  boolean bName = false;
  boolean bPatronymic = false;
  boolean bGender = false;
  boolean bCharm = false;
  boolean bBirth = false;

  boolean bHomePhone = false;
  boolean bMobilePhone = false;
  boolean bWorkPhone = false;

  boolean bAddress = false;
  boolean bAddressFact = false;
  boolean bAddressRegister = false;

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    if (qName.equalsIgnoreCase("client")) {
      String id = attributes.getValue("id");
      client = new CiaClient();
      client.setId(id);
    } else if (qName.equalsIgnoreCase("surname")) {
      bSurname = true;
    } else if (qName.equalsIgnoreCase("name")) {
      bName = true;
    } else if (qName.equalsIgnoreCase("patronymic")) {
      bPatronymic = true;
    } else if (qName.equalsIgnoreCase("gender")) {
      bGender = true;
    } else if (qName.equalsIgnoreCase("charm")) {
      bCharm = true;
    } else if (qName.equalsIgnoreCase("birth")) {
      bBirth = true;
    } else if (qName.equalsIgnoreCase("homePhone")) {
      bHomePhone = true;
    } else if (qName.equalsIgnoreCase("mobilePhone")) {
      bMobilePhone = true;
    } else if (qName.equalsIgnoreCase("workPhone")) {
      bWorkPhone = true;
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    super.endElement(uri, localName, qName);
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    super.characters(ch, start, length);
  }
}
