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
			"created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
			"number serial)";

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


/*UPDATE tmp_client_phone SET status = 'Date is Error' FROM tmp_client
WHERE tmp_client.status = 'Date is Error' and tmp_client.id = tmp_client_phone.client_id;

UPDATE tmp_client_phone SET status ='Date is Error'
WHERE client_id =(SELECT id FROM tmp_client WHERE status = 'Date is Error' and tmp_client_phone.client_id =tmp_client.id);

update tmp_client_transaction c set
  client_id =(select t.client_id from tmp_client_transaction t where t.client_id notnull and t.account_number= c.account_number);

-- start first

update tmp_client set stat=null;

update tmp_client set stat ='Date is Error'
where (birth_date <= '1018-01-01' or birth_date >= '2014-01-01' or birth_date isnull) and stat isnull;

update tmp_client set stat ='Names are incorrect'
where (firstname isnull  or firstname ='' or lastname ISNULL or lastname ='' ) and stat isnull;


update tmp_client_phone c set stat =(select t.stat
                                     from tmp_client t where c.client_id =t.id and (t.stat like 'Date is Error' or t.stat like 'Names are incorrect'));
update tmp_client_addr c set stat =(select t.stat
                                    from tmp_client t where c.client_id =t.id and (t.stat like 'Date is Error' or t.stat like 'Names are incorrect'));


update tmp_client t set stat ='1' where number in(
  select number from(
                    select number, row_number() over (partition by id order by created_at desc) from tmp_client
                  ) x where ROW_NUMBER=1 and stat isnull );

update tmp_client t set stat ='2' where created_at =(
  select distinct on(id) created_at from tmp_client
  where id = t.id and t.stat isnull
  order by id, created_at desc);

-- finish first

-- start second

-- нужно создать еще статус для charm в tmp_client  чтоб взять только один имя без дупликата через row_number

SELECT setval('charm_id_seq', 34);

insert into charm (name, id, actually)
    SELECT distinct on (t.charm) t.charm ,nextval('charm_id_seq'),true
  from tmp_client t where t.stat ='1' order by t.charm, t.number desc ;

insert into client (firstname, lastname, patronymic, gender, birth_date, cia_id, charm)
  SELECT t.firstname, t.lastname ,t.patronymic, t.gender , t.birth_date::TIMESTAMP, t.id, c.id
  from tmp_client t left join (select id, name from charm group by id, name) c on t.charm =c.name  where t.stat like '1';


-- finish second



update tmp_client_addr t set stat ='3' where client_id in(
  select id from tmp_client where t.client_id =id
);



select id, CTID, row_number() over
  (partition by id order by created_at desc ) as solo from tmp_client t where t.solo = 1;


delete from client;
delete from charm where id>=35;
select  row_number() over (partition by id),* from tmp_client
order by  id, stat

select id, stat, row_number() over (partition by id) from tmp_client
order by id

select distinct on (c.id, t.id) c.id, t.id, row_number() over (partition by t.id) from tmp_client t, charm c
where t.charm =c.name and t.stat like '1' order by c.id
select t.id, c.id, row_number() over (partition by t.id)  from tmp_client t, charm c
where t.stat like '1' and t.charm = c.name


select  distinct on (name) *, row_number() over (partition by name) from charm;

select charm, firstname from client  order by firstname;
select id, name from charm where id =434;
select * from tmp_client where charm ='БОШХы5UЪМЗ'
*/