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
      ciaPhone.type = curHomePhoneElement.getNodeName();
      ciaPhone.number = curHomePhoneElement.getTextContent();

      phones.add(ciaPhone);
    }
  }

  private void parseCiaClientAddresses(List<CiaAddress> addresses, Element curClientElement, String addressType) {
    Element curAddressElement = (Element) curClientElement.getElementsByTagName(addressType).item(0);

    CiaAddress ciaAddress = new CiaAddress();
    ciaAddress.type = addressType;
    ciaAddress.street = curAddressElement.getAttribute("street");
    ciaAddress.house = curAddressElement.getAttribute("house");
    ciaAddress.flat = curAddressElement.getAttribute("flat");

    addresses.add(ciaAddress);
  }

  @Override
  public void validateAndMigrateData() {

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
}