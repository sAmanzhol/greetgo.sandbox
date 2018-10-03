package kz.greetgo.sandbox.db.register_impl.migration;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.BeanGetter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CiaMigration extends AbstractParse {

	private Connection connection;

	public BeanGetter<Jdbc> jdbc;

	private File inFile;

	private Integer batchSize;

	public CiaMigration(Connection connection, File inFile, Integer batchSize) {

		this.connection = connection;
		this.inFile = inFile;
		this.batchSize = batchSize;
	}

	@Override
	protected void insertClient() throws SQLException, IOException, SAXException, ParserConfigurationException {

		String insertClient = "insert into tmp_client (id, firstname, lastname, patronymic, gender, birth_date, charm, " +
			" phone_type, phone_number, addr_type, addr_street, addr_house, addr_flat) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();

		try (PreparedStatement ps = connection.prepareStatement(insertClient)) {
			MyHandler myHandler = new MyHandler(ps, batchSize);
			saxParser.parse(inFile, myHandler);
		}

	}

	@Override
	protected void createClient() throws SQLException {

		String createClient = "" +
			"create table tmp_client (id varchar(255) not null,\n" +
			"  firstname varchar(50),\n" +
			"lastname varchar(255),\n" +
			" patronymic varchar(255),\n" +
			"gender varchar(255),\n" +
			"birth_date varchar(255),\n" +
			" charm varchar(255),\n" +
			" phone_type varchar(255),\n" +
			" phone_number varchar(255),\n" +
			"addr_type varchar(255),\n" +
			" addr_street varchar(255),\n" +
			" addr_house varchar(255),\n" +
			"addr_flat varchar(255));";
		try (PreparedStatement ps = connection.prepareStatement(createClient)) {
			ps.executeUpdate();
		}
	}

	@Override
	protected void dropClient() throws SQLException {

		String dropClient = "drop table tmp_client";

		try (PreparedStatement ps = connection.prepareStatement(dropClient)) {
			ps.execute();
		}
	}


}


