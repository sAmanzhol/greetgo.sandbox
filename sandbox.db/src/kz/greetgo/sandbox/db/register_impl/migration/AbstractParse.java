package kz.greetgo.sandbox.db.register_impl.migration;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

public abstract class AbstractParse {

	public void migrate() throws SQLException, ParserConfigurationException, SAXException, IOException {
		dropClient();
		createClient();
		insertClient();
	}

	protected abstract void insertClient() throws SQLException, IOException, SAXException, ParserConfigurationException;

	protected abstract void createClient() throws SQLException;

	protected abstract void dropClient() throws SQLException;
}
