package kz.greetgo.sandbox.db.register_impl.migration;


import kz.greetgo.sandbox.controller.register.model.CiaAddress;
import kz.greetgo.sandbox.controller.register.model.CiaPhone;
import kz.greetgo.sandbox.controller.register.model.CiaSystem;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MyHandler extends DefaultHandler {

	private CiaPhone ciaPhone = new CiaPhone();

	private List<CiaSystem> ciaSystemList = new ArrayList<>();
	private List<CiaPhone> ciaPhoneArrayList = new ArrayList<>();
	private List<CiaAddress> ciaAddressArrayList = new ArrayList<>();

	public CiaSystem ciaSystem = new CiaSystem();

	public CiaAddress ciaAddress = new CiaAddress();

	private String element;


	private CiaJdbc ciaJdbc;

	private int size=0;

	public MyHandler(CiaJdbc ciaJdbc) {

		this.ciaJdbc = ciaJdbc;
	}


	private void addBatchs() throws SQLException {

		ciaJdbc.insertParseXml(ciaSystem, ciaPhoneArrayList, ciaAddressArrayList);
	}

	@Override
	public void startDocument() throws SAXException {

		super.startDocument();

	}

	@Override
	public void endDocument() throws SAXException {

		try {
			ciaJdbc.executeBatchs();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		System.err.println(ciaSystemList);
		super.endDocument();

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		super.startElement(uri, localName, qName, attributes);
		element = qName;
		try {
			addCiaSystemAttributes(qName, ciaSystem, attributes);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		super.endElement(uri, localName, qName);


		if (qName.equals("client")) {
			try {
				addBatchs();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			/*ciaSystemList.add(ciaSystem);*/
			System.err.println("size:" + size);
			System.err.println(ciaSystem);
			ciaSystem = new CiaSystem();
			ciaAddressArrayList = new ArrayList<>();
			ciaPhoneArrayList = new ArrayList<>();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		if (element.equals("workPhone") || element.equals("homePhone") || element.equals("mobilePhone")) {
			String type = element.replace("Phone", "").toUpperCase();
			ciaPhone = new CiaPhone();
			ciaPhone.type = type;
			ciaPhone.number = new String(ch, start, length);
			ciaPhoneArrayList.add(ciaPhone);
			size++;
			element = "";
		}
	}

	private void addCiaSystemAttributes(String qName, CiaSystem ciaSystem, Attributes attributes) throws ParseException {

		switch (qName) {
			case "client": {
				ciaSystem.id = attributes.getValue(0);
				break;
			}
			case "name": {
				ciaSystem.firstname = attributes.getValue(0);
				break;
			}
			case "surname": {

				ciaSystem.lastname = attributes.getValue(0);
				break;
			}
			case "patronymic": {
				ciaSystem.patronymic = attributes.getValue(0);
				break;
			}
			case "gender": {
				ciaSystem.gender = attributes.getValue(0);
				break;
			}
			case "birth": {
				ciaSystem.birth_date =attributes.getValue(0);
				break;
			}
			case "fact": {
				ciaAddress = new CiaAddress();
				ciaAddress.type = "FACT";
				ciaAddress.street = attributes.getValue(0);
				ciaAddress.house = attributes.getValue(1);
				ciaAddress.flat = attributes.getValue(2);
				ciaAddressArrayList.add(ciaAddress);
				break;
			}
			case "register": {
				ciaAddress = new CiaAddress();
				ciaAddress.type = "REG";
				ciaAddress.street = attributes.getValue(0);
				ciaAddress.house = attributes.getValue(1);
				ciaAddress.flat = attributes.getValue(2);
				ciaAddressArrayList.add(ciaAddress);
				break;
			}
			case "charm": {
				ciaSystem.charm = attributes.getValue(0);
				break;
			}

		}

	}


}