package kz.greetgo.learn.migration.core;

import kz.greetgo.learn.migration.util.SaxHandler;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ClientRecord extends SaxHandler {
  public long number;
  public String id;
  public String surname, name, patronymic;
  public Charm charm = new Charm();
  public java.sql.Date birthDate;
  public List<AddressRecord> addressRecordList;
  public Gender gender;
  public List<Phone> phoneList = new ArrayList<>();

  public void parseRecordData(String recordData) throws SAXException, IOException {
    System.out.println(recordData);
    if (recordData == null) return;
    XMLReader reader = XMLReaderFactory.createXMLReader();
    reader.setContentHandler(this);
    reader.parse(new InputSource(new StringReader(recordData)));
  }


  @Override
  protected void startingTag(Attributes attributes) {
    String path = path();

    if ("/client".equals(path)) {
      id = attributes.getValue("id");
      return;
    }
    if ("/client/surname".equals(path)) {
      surname = attributes.getValue("value");
    }
    if ("/client/name".equals(path)) {
      name = attributes.getValue("value");
    }
    if ("/client/patronymic".equals(path)) {
      patronymic = attributes.getValue("value");
      return;
    }
    if ("/client/charm".equals(path)) {
      charm.name = attributes.getValue("value");
      return;
    }
    if ("/client/birth".equals(path)) {
      try {
        String date = attributes.getValue("value");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        birthDate = new java.sql.Date(sdf.parse(date).getTime());
        return;
      }
      catch (Exception e){
        birthDate = null;
        e.printStackTrace();
      }
    }
    if("/client/gender".equals(path)){
       gender = Gender.valueOf(attributes.getValue("value"));
    }
    if ("/client/address".equals(path)){
      addressRecordList = new ArrayList<>();
    }
    if("/client/address/fact".equals(path)){
      AddressRecord addressRecord = new AddressRecord();

      addressRecord.type = AddressType.FACT;
      addressRecord.street = attributes.getValue("street");
      addressRecord.house = attributes.getValue("house");
      addressRecord.flat = attributes.getValue("flat");

      addressRecordList.add(addressRecord);
    }

    if("/client/address/register".equals(path)){
      AddressRecord addressRecord = new AddressRecord();

      addressRecord.type = AddressType.REG;
      addressRecord.street = attributes.getValue("street");
      addressRecord.house = attributes.getValue("house");
      addressRecord.flat = attributes.getValue("flat");

      addressRecordList.add(addressRecord);

    }

    if("/client/workPhone".equals(path)){
      Phone phone = new Phone();
      phone.type = PhoneType.WORK;
      phone.number = text();
    }

    if("/client/homePhone".equals(path)){
      Phone phone = new Phone();
      phone.type = PhoneType.WORK;
      phone.number = text();
    }

    if("/client/mobilePhone".equals(path)){
      Phone phone = new Phone();
      phone.type = PhoneType.WORK;
      phone.number = text();
    }
  }

  @Override
  protected void endedTag(String tagName) throws Exception {

    String path = path() + "/" + tagName;
  }
}
