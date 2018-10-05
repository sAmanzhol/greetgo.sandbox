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
	protected void executed(String sql) throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.executeUpdate();
		}

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
			" charm varchar(255)," +
			" stat varchar(255)," +
			"created_at TIMESTAMPTZ NOT NULL DEFAULT NOW())";

		String createClientPhone = "create table tmp_client_phone(\n" +
			"  client_id varchar(255),\n" +
			"  type varchar(255),\n" +
			"  number varchar(255),\n" +
			" stat varchar(255));";

		String createClientAddr = "create table tmp_client_addr(\n" +
			"  client_id varchar(255),\n" +
			"  type varchar(255),\n" +
			"  street varchar(255),\n" +
			"  house varchar(255),\n" +
			"  flat varchar(255),\n" +
			" stat varchar(255));";

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
		CiaJdbc ciaJdbc = new CiaJdbc(connection, batchSize);
		MyHandler myHandler = new MyHandler(ciaJdbc);
		saxParser.parse(inFile, myHandler);

	}



	@Override
	protected void updateTable() throws SQLException {

		String updateTableClientByDateStat = "update tmp_client set stat ='Date is Error'\n" +
			"where (birth_date <= '1018-01-01' or birth_date >= '2014-01-01' or birth_date isnull) and stat isnull;";

		String updateTableClientByNamesStat = "update tmp_client set stat ='Names are incorrect'\n" +
			"where (firstname isnull  or firstname ='' or lastname ISNULL or lastname ='' ) and stat isnull;";

		String updateTablePhoneOfStat = "update tmp_client_phone c set stat =(select t.stat\n" +
			"from tmp_client t where c.client_id =t.id and (t.stat like 'Date is Error' or t.stat like 'Names are incorrect'));";

		String updateTableAddrOfStat = "update tmp_client_addr c set stat =(select t.stat\n" +
			"from tmp_client t where c.client_id =t.id and (t.stat like 'Date is Error' or t.stat like 'Names are incorrect'));";


		executed(updateTableClientByDateStat);
		executed(updateTableClientByNamesStat);
		executed(updateTablePhoneOfStat);
		executed(updateTableAddrOfStat);

	}
	@Override
	protected void addIntoCurrentTable() {

	}


}




/*
UPDATE tmp_client_phone SET status = 'Date is Error' FROM tmp_client
WHERE tmp_client.status = 'Date is Error' and tmp_client.id = tmp_client_phone.client_id;

UPDATE tmp_client_phone SET status ='Date is Error'
WHERE client_id =(SELECT id FROM tmp_client WHERE status = 'Date is Error' and tmp_client_phone.client_id =tmp_client.id);


update tmp_client_transaction c set
  client_id =(select t.client_id from tmp_client_transaction t where t.client_id notnull and t.account_number= c.account_number);


update tmp_client set stat ='Date is Error'
where (birth_date <= '1018-01-01' or birth_date >= '2014-01-01' or birth_date isnull) and stat isnull;

update tmp_client set stat ='Names are incorrect'
where (firstname isnull  or firstname ='' or lastname ISNULL or lastname ='' ) and stat isnull;


update tmp_client_phone c set stat =(select t.stat
from tmp_client t where c.client_id =t.id and (t.stat like 'Date is Error' or t.stat like 'Names are incorrect'));


update tmp_client set stat ='1'  where created_at =(
  select distinct t.created_at from tmp_client t where t.id =tmp_client.id  and t.created_at = tmp_client.created_at order by t.created_at desc
)*/


/*
UPDATE tmp_client_phone SET status = 'Date is Error' FROM tmp_client
WHERE tmp_client.status = 'Date is Error' and tmp_client.id = tmp_client_phone.client_id;

UPDATE tmp_client_phone SET status ='Date is Error'
WHERE client_id =(SELECT id FROM tmp_client WHERE status = 'Date is Error' and tmp_client_phone.client_id =tmp_client.id);


update tmp_client_transaction c set
  client_id =(select t.client_id from tmp_client_transaction t where t.client_id notnull and t.account_number= c.account_number);


update tmp_client set stat ='Date is Error'
where (birth_date <= '1018-01-01' or birth_date >= '2014-01-01' or birth_date isnull) and stat isnull;

update tmp_client set stat ='Names are incorrect'
where (firstname isnull  or firstname ='' or lastname ISNULL or lastname ='' ) and stat isnull;


update tmp_client_phone c set stat =(select t.stat
from tmp_client t where c.client_id =t.id and (t.stat like 'Date is Error' or t.stat like 'Names are incorrect'));


update tmp_client set stat ='1'  where created_at=(
  select t.created_at from tmp_client t where t.id =tmp_client.id  and t.created_at = tmp_client.created_at order by t.created_at desc
)


update tmp_client t set stat ='1' where created_at =(
select distinct on(id) created_at from tmp_client
where id = t.id
order by id, created_at desc);


select * from tmp_client
where id = '0-B9N-HT-PU-04wolRBPzj'
order by  created_at desc*/