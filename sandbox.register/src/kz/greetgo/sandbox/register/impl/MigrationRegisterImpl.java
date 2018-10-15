package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.register.MigrationRegister;
import kz.greetgo.sandbox.register.impl.jdbc.migration.CiaMigrationCallbackImpl;
import kz.greetgo.sandbox.register.impl.jdbc.migration.FrsMigrationCallbackImpl;
import kz.greetgo.sandbox.register.impl.jdbc.migration.model.*;
import kz.greetgo.sandbox.register.util.JdbcSandbox;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Bean
public class MigrationRegisterImpl implements MigrationRegister {

  public BeanGetter<JdbcSandbox> jdbc;

  @Override
  public void migrate(String fileType, String file) throws Exception {
    if ("cia".equals(fileType)) {
      Cia ciaData = parseCiaFile(file);
      jdbc.get().execute(new CiaMigrationCallbackImpl(getConnection(), ciaData));
    } else if ("frs".equals(fileType)) {
      Frs frsData = parseFrsFile(file);
      jdbc.get().execute(new FrsMigrationCallbackImpl(getConnection(), frsData));
    }
  }

  private Cia parseCiaFile(String file) throws Exception {
    Cia ciaData = new Cia();

    File ciaFile = new File(file);
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(ciaFile);

    NodeList clientNodes = doc.getElementsByTagName("client");

    for (int clientIterator = 0; clientIterator < clientNodes.getLength(); clientIterator++) {
      Node curClientNode = clientNodes.item(clientIterator);
      Element curClientElement = (Element) curClientNode;

      CiaClient ciaClient = new CiaClient();
      ciaClient.id = curClientElement.getAttribute("id");
      ciaClient.surname = curClientElement.getElementsByTagName("surname").item(0).getTextContent();
      ciaClient.name = curClientElement.getElementsByTagName("name").item(0).getTextContent();
      ciaClient.patronymic = curClientElement.getElementsByTagName("patronymic").item(0).getTextContent();
      ciaClient.gender = curClientElement.getElementsByTagName("gender").item(0).getTextContent();
      ciaClient.charm = curClientElement.getElementsByTagName("charm").item(0).getTextContent();
      ciaClient.birth = curClientElement.getElementsByTagName("birth").item(0).getTextContent();

      ciaClient.addresses = new ArrayList<>();

      NodeList curClientElementAddresses = curClientElement.getElementsByTagName("address");

      for (int addressIterator = 0; addressIterator < curClientElementAddresses.getLength(); addressIterator++) {
        Node curAddressNode = curClientElementAddresses.item(addressIterator);
        Element curAddressElement = (Element) curAddressNode;

        CiaAddress ciaAddress = new CiaAddress();
        ciaAddress.type = curAddressElement.getNodeName();
        ciaAddress.street = curAddressElement.getAttribute("street");
        ciaAddress.house = curAddressElement.getAttribute("house");
        ciaAddress.flat = curAddressElement.getAttribute("flat");

        ciaClient.addresses.add(ciaAddress);
      }

      ciaClient.phones = new ArrayList<>();

      parseCiaClientPhones(ciaClient.phones, curClientElement, "homePhone");
      parseCiaClientPhones(ciaClient.phones, curClientElement, "mobilePhone");
      parseCiaClientPhones(ciaClient.phones, curClientElement, "workPhone");

      ciaData.ciaClients.add(ciaClient);
    }

    return ciaData;
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

  private Frs parseFrsFile(String file) throws Exception {
    Frs frsData = new Frs();
    frsData.accounts = new ArrayList<>();
    frsData.transactions = new ArrayList<>();

    FileInputStream inputStream = new FileInputStream(file);
    Scanner scanner = new Scanner(inputStream, "UTF-8");

    while (scanner.hasNextLine()) {
      String rowString = scanner.nextLine();

      JSONObject rowJson = new JSONObject(rowString);

      if (rowJson.get("type").equals("transaction")) {
        FrsTransaction frsTransaction = new FrsTransaction();
        frsTransaction.money = (String) rowJson.get("money");
        frsTransaction.finished_at = (String) rowJson.get("finished_at");
        frsTransaction.transaction_type = (String) rowJson.get("transaction_type");
        frsTransaction.account_number = (String) rowJson.get("account_number");

        frsData.transactions.add(frsTransaction);
      } else if (rowJson.get("type").equals("new_account")) {
        FrsAccount frsAccount = new FrsAccount();
        frsAccount.client_id = (String) rowJson.get("client_id");
        frsAccount.account_number = (String) rowJson.get("account_number");
        frsAccount.registered_at = (String) rowJson.get("registered_at");

        frsData.accounts.add(frsAccount);
      }
    }

    inputStream.close();
    scanner.close();

    return frsData;
  }

  private Connection getConnection() throws Exception {
    return DriverManager.getConnection("jdbc:postgresql://localhost/ssailaubayev_sandbox", "ssailaubayev_sandbox", "111");
  }
}
