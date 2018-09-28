package kz.greetgo.sandbox.db.migration;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();
		MyHandler myHandler = new MyHandler();
		saxParser.parse(new File("/home/nazar/IdeaProjects/greetgo.sandbox-1/sandbox.db/src_resources/out_source_file/from_cia_2018-02-21-154532-1-300.xml"), myHandler);



		/*XMLReader xmlReader = saxParser.getXMLReader();

		xmlReader.setContentHandler(myHandler);
		xmlReader.parse("out_source_file/example.xml");

		Report report = myHandler.getReport();
*/
	}

	private static class MyHandler extends DefaultHandler {

		private int id;

		private String element;

		List<String> Names = new ArrayList<>();

		private String idStr;
		private  String getTYPES;

		private Map<Integer, String> data = new HashMap<Integer, String>();

		@Override
		public void startDocument() throws SAXException {

			super.startDocument();

		}

		@Override
		public void endDocument() throws SAXException {

			super.endDocument();

		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {


			super.startElement(uri, localName, qName, attributes);
			if(!Names.contains(qName)){
				Names.add(qName);
			}
			System.err.println(Names);


// 	System.err.println("StartQName: " + qName);
//			System.err.println("AttributesName : " + attributes.getQName(0) + ";\nAttributesValue: " + attributes.getValue(0));
//			if(attributes.getType(0)!=null){
//			getTYPES = attributes.getType(0);
//			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {


			super.endElement(uri, localName, qName);
//			System.err.println("EndQName: /" + qName);
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
		}

		List<String> getNames(){
			return Names;
		}
	}
}

class Report {

	Integer number;

	List<Employee> employeeList;
}

class Employee {

	String depatemnt;

	Integer number;

	String name;

	Integer age;

	Salary salary;

	@Override
	public String toString() {

		return "Employee{" +
			"depatemnt='" + depatemnt + '\'' +
			", number=" + number +
			", name='" + name + '\'' +
			", age=" + age +
			", salary=" + salary +
			'}';
	}

	static class Salary {

		Double value;

		String currency;
	}


}
/*static final String REPORT_TAG = "report";

		static final String EMPLOYERS_TAG = "employers";

		static final String EMPLOYEE_TAG = "employee";

		static final String NAME_TAG = "name";

		static final String AGE_TAG = "age";

		static final String SALARY_TAG = "salary";


		static final String REPORT_NUMBER_ATTRIBUTE = "number";

		static final String EMPLOYERS_DEPARTMENT_ATTRIBUTE = "department";

		static final String EMPLOYEE_NUMBER_ATTRIBUTE = "number";

		static final String CURRENCU_ATTRIBUTE = "currency";


		private Report report;

		private Employee currentEmployee;

		private String employersDepartment;

		private String currentElement;


		Report getReport() {

			return report;
		}

		public void startDocuemnt() throws SAXException{

			System.err.println("Starting XML PARSING...");

		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

			super.startElement(uri, localName, qName, attributes);
		}*/