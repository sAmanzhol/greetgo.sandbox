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
	protected void dropClient() throws SQLException {

		String dropClient = "drop table tmp_client_transaction";
		try (PreparedStatement ps = connection.prepareStatement(dropClient)) {
			ps.execute();
		}
	}

	@Override
	protected void createClient() throws SQLException {

		String createClient = "create table tmp_client_transaction\n" +
			"( client_id varchar(255),\n" +
			"  registered_at varchar(255),\n" +
			"  finished_at varchar(255),\n" +
			"  transaction_type varchar(255),\n" +
			"  type varchar(255),\n" +
			"  money varchar(255),\n" +
			"  account_number varchar(255))";
		try (PreparedStatement ps = connection.prepareStatement(createClient)) {
			ps.executeUpdate();
		}

	}

	@Override
	protected void insertClient() throws SQLException, IOException {


		String insertClient = "insert into tmp_client_transaction (client_id, registered_at, finished_at, transaction_type, type, money, account_number)\n" +
			" VALUES (?,?,?,?,?,?,?)";
		String strCurrentLine;
		try (PreparedStatement ps = connection.prepareStatement(insertClient)) {
			try (BufferedReader objReader = new BufferedReader(new FileReader(inFile))) {
				while ((strCurrentLine = objReader.readLine()) != null) {
					JsonParse jsonParse = new JsonParse(strCurrentLine, ps, batchSize);
					jsonParse.parse();
				}
				ps.executeBatch();

			}
		}


	}

}





