package kz.greetgo.sandbox.db.register_impl.migration;

import java.sql.SQLException;

public abstract class AbstractParse {

	public void migrate() throws Exception {
		dropTable();
		createTable();
		insertTable();
		addIntoCurrentTable();
		updateTable();
	}

	protected abstract void dropTable() throws SQLException;

	protected abstract void insertTable() throws Exception;

	protected abstract void createTable() throws SQLException;

	protected abstract void addIntoCurrentTable();

	protected abstract void updateTable() throws SQLException;
}
