package kz.greetgo.sandbox.db.register_impl.migration;

import kz.greetgo.sandbox.controller.register.model.CiaAddress;
import kz.greetgo.sandbox.controller.register.model.CiaPhone;
import kz.greetgo.sandbox.controller.register.model.CiaSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CiaJdbc {

	private Connection connection;

	private Integer batchSize;

	private PreparedStatement preparedStatement, preparedStatement1, preparedStatement2;

	private int count = 0;

	private int count1 = 0;

	private int count2 = 0;
	private  boolean isPrepareStatement = true;


	public CiaJdbc(Connection connection, Integer batchSize) {

		this.connection = connection;
		this.batchSize = batchSize;
	}

	private void getPrepareStatement() throws SQLException {


		String insertTmpClient = "insert into tmp_client (id, firstname, lastname, patronymic, gender, birth_date, charm)" +
			" VALUES (?,?,?,?,?,?,?)";
		String insertTmpPhone = "insert into tmp_client_phone (client_id, type, number) values (?,?,?)";
		String insertTmpAddress = "insert into tmp_client_addr (client_id, type, street, house, flat) values (?,?,?,?,?)";

		preparedStatement = connection.prepareStatement(insertTmpClient);
		preparedStatement1 = connection.prepareStatement(insertTmpPhone);
		preparedStatement2 = connection.prepareStatement(insertTmpAddress);
		  isPrepareStatement =false;
	}

	protected void insertParseXml(CiaSystem ciaSystem, List<CiaPhone> ciaPhoneList, List<CiaAddress> ciaAddressList) throws SQLException {

		if (isPrepareStatement) getPrepareStatement();

		preparedStatement.setString(1, ciaSystem.id);
		preparedStatement.setString(2, ciaSystem.firstname);
		preparedStatement.setString(3, ciaSystem.lastname);
		preparedStatement.setString(4, ciaSystem.patronymic);
		preparedStatement.setString(5, ciaSystem.gender);
		preparedStatement.setString(6, ciaSystem.birth_date);
		preparedStatement.setString(7, ciaSystem.charm);
		for (CiaPhone ciaPhone : ciaPhoneList) {
			preparedStatement1.setString(1, ciaSystem.id);
			preparedStatement1.setString(2, ciaPhone.type);
			preparedStatement1.setString(3, ciaPhone.number);
			preparedStatement1.addBatch();
			count1++;
			if (count1 == batchSize) {
				preparedStatement1.executeBatch();
			}
		}
		for (CiaAddress ciaAddress : ciaAddressList) {
			preparedStatement2.setString(1, ciaSystem.id);
			preparedStatement2.setString(2, ciaAddress.type);
			preparedStatement2.setString(3, ciaAddress.street);
			preparedStatement2.setString(4, ciaAddress.house);
			preparedStatement2.setString(5, ciaAddress.flat);
			preparedStatement2.addBatch();
			count2++;
			if (count2 == batchSize) {
				preparedStatement2.executeBatch();
			}
		}

		preparedStatement.addBatch();
		count++;
		if (count == batchSize) {
			preparedStatement.executeBatch();
		}
	}

	protected void executeBatchs() throws SQLException {

		preparedStatement.executeBatch();
		preparedStatement1.executeBatch();
		preparedStatement2.executeBatch();
	}


}
