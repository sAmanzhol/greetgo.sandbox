package kz.greetgo.sandbox.db.register_impl.migration;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.greetgo.sandbox.controller.register.model.FrsSystem;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JsonParse {

	private PreparedStatement ps;

	private Integer batchSize;

	private String strCurrentLine;

	private int count = 0;

	private FrsSystem frsSystem;

	public JsonParse(String strCurrentLine, PreparedStatement ps, Integer batchSize) {

		this.ps = ps;
		this.batchSize = batchSize;
		this.strCurrentLine = strCurrentLine;
	}

	protected void parse() throws IOException, SQLException {

		ObjectMapper mapper = new ObjectMapper();
		frsSystem = mapper.readValue(strCurrentLine, FrsSystem.class);
		System.err.println("NEW LINE: " + count);
		System.err.println("ID: " + frsSystem.client_id);
		System.err.println("REGISTERED_AT: " + frsSystem.registered_at);
		System.err.println("FINISHED_AT: " + frsSystem.finished_at);
		System.err.println("ACCOUNT_NUMBER: " + frsSystem.account_number);
		System.err.println("MONEY: " + frsSystem.money);
		System.err.println("TRANSACTION_TYPE: " + frsSystem.transaction_type);
		System.err.println("TYPE: " + frsSystem.type);
		addBatchs();

		count++;
		if (count == batchSize) {
			ps.executeBatch();
		}
	}

	private void addBatchs() throws SQLException {

		ps.setString(1, frsSystem.client_id);
		ps.setString(2, frsSystem.registered_at);
		ps.setString(3, frsSystem.finished_at);
		ps.setString(4, frsSystem.transaction_type);
		ps.setString(5, frsSystem.type);
		ps.setString(6, frsSystem.money);
		ps.setString(7, frsSystem.account_number);
		ps.addBatch();
	}

}
