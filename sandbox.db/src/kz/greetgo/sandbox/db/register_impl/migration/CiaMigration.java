package kz.greetgo.sandbox.db.register_impl.migration;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.BeanGetter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class CiaMigration  {

	private Connection connection;
	public BeanGetter<Jdbc> jdbc;


	public CiaMigration(Connection connection) {
		this.connection = connection;
	}


	private final String url = "jdbc:postgresql://localhost:5432/nazar_sandbox";
	private final String user = "nazar_sandbox";
	private final String password = "111";


	public Object doInConnection(Connection connection) throws Exception {

		String sb = "insert into tmp_client (id, firstname, lastname, patronymic, gender, birth_date, charm, " +
			" phone_type, phone_number, addr_type, addr_street, addr_house, addr_flat) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

		String sql = "" +
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
		String drops = "drop table tmp_client";
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();

	/*	try (PreparedStatement ps = connection.prepareStatement(drops)) {
			ps.executeUpdate();
		}*/

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.executeUpdate();
		}


		try (PreparedStatement ps = connection.prepareStatement(sb)) {
			MyHandler myHandler = new MyHandler(ps);
			saxParser.parse(new File("/home/nazar/IdeaProjects/greetgo.sandbox-1/sandbox.db/src_resources/out_source_file/from_cia_2018-02-21-154532-1-300.xml"), myHandler);
		}

		return null;
	}


}


