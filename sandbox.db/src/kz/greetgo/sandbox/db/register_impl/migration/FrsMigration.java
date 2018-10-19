package kz.greetgo.sandbox.db.register_impl.migration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FrsMigration extends AbstractParse {

	private Connection connection;

	private File inFile;

	private Integer batchSize;


	public FrsMigration(Connection connection, File inFile, Integer batchSize) {

		this.connection = connection;
		this.inFile = inFile;
		this.batchSize = batchSize;
	}

	@Override
	protected void executed(String query) throws SQLException {
		try(PreparedStatement ps = connection.prepareStatement(query)){
			ps.executeUpdate();
		}
	}

	@Override
	protected void dropTable() throws SQLException {

		String dropTmp = "drop table if exists tmp_client_transaction";
		try (PreparedStatement ps = connection.prepareStatement(dropTmp)) {
			ps.execute();
		}
	}

	@Override
	protected void createTable() throws SQLException {

		String createTmp = "create table tmp_client_transaction\n" +
			"( client_id varchar(255),\n" +
			"  registered_at varchar(255),\n" +
			"  finished_at varchar(255),\n" +
			"  transaction_type varchar(255),\n" +
			"  type varchar(255),\n" +
			"  money varchar(255),\n" +
			"  account_number varchar(255)," +
			" number serial)";
		try (PreparedStatement ps = connection.prepareStatement(createTmp)) {
			ps.executeUpdate();
		}

	}


	@Override
	protected void insertTable() throws SQLException, IOException {


		String insertTmp = "insert into tmp_client_transaction (client_id, registered_at, finished_at, transaction_type, type, money, account_number)\n" +
			" VALUES (?,?,?,?,?,?,?)";
		String strCurrentLine;
		try (PreparedStatement ps = connection.prepareStatement(insertTmp)) {
			try (BufferedReader objReader = new BufferedReader(new FileReader(inFile))) {
				while ((strCurrentLine = objReader.readLine()) != null) {
					JsonParse jsonParse = new JsonParse(strCurrentLine, ps, batchSize);
					jsonParse.parse();
				}
				ps.executeBatch();

			}
		}

	}

	@Override
	protected void addIntoCurrentTable() {

	}

	@Override
	protected void updateTable() throws SQLException {
		String updateTmp = "update tmp_client_transaction c\n" +
			"set client_id =(select t.client_id from tmp_client_transaction t where t.client_id notnull and t.account_number= c.account_number)";

		executed(updateTmp);

		/*insert into transaction_type (code, name) SELECT type, transaction_type from tmp_client_transaction;

insert into client_account (client, money, number, registered_at, frs_client_id, frs_account_number)*/

	}

}





