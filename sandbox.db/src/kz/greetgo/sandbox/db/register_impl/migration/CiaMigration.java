package kz.greetgo.sandbox.db.register_impl.migration;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.BeanGetter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
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
	protected void dropTable() throws SQLException {

		String dropClient = "drop table if exists tmp_client, tmp_client_phone, tmp_client_addr";
		try (PreparedStatement ps = connection.prepareStatement(dropClient)) {
			ps.execute();
		}
	}

	@Override
	protected void createTable() throws SQLException {

		String createClient = "" +
			"create table tmp_client (id varchar(255) not null,\n" +
			"  firstname varchar(50),\n" +
			"lastname varchar(255),\n" +
			" patronymic varchar(255),\n" +
			"gender varchar(255),\n" +
			"birth_date varchar(255),\n" +
			" charm varchar(255))";

		String createClientPhone = "create table tmp_client_phone(\n" +
			"  client_id varchar(255),\n" +
			"  type varchar(255),\n" +
			"  number varchar(255)\n" +
			");";

		String createClientAddr = "create table tmp_client_addr(\n" +
			"  client_id varchar(255),\n" +
			"  type varchar(255),\n" +
			"  street varchar(255),\n" +
			"  house varchar(255),\n" +
			"  flat varchar(255)\n" +
			");";

		try (PreparedStatement ps = connection.prepareStatement(createClient)) {
			ps.executeUpdate();
		}
		try (PreparedStatement ps = connection.prepareStatement(createClientPhone)) {
			ps.executeUpdate();
		} try (PreparedStatement ps = connection.prepareStatement(createClientAddr)) {
			ps.executeUpdate();
		}

	}


	@Override
	protected void insertTable() throws Exception {

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();
		MyWorker myWorker = new MyWorker(connection, batchSize);
		MyHandler myHandler = new MyHandler(myWorker);
		saxParser.parse(inFile, myHandler);

	}

	@Override
	protected void addIntoCurrentTable() {

	}

	@Override
	protected void updateTable() {

	}


}


