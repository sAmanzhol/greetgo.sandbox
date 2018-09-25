package kz.greetgo.sandbox.db.register_impl.jdbc;

import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientListSql extends AbstractBasicSql<ClientRecord, List<ClientRecord>> {

	List<ClientRecord> clientRecords = new ArrayList<>();

	public ClientListSql(ClientFilter clientFilter) {

		super(clientFilter);
	}


	@Override
	protected void select() {

		sb.append("select c.id as id, c.firstname as firstname, c.lastname as lastname, c.patronymic as patronymic, ch.name as character, " +
			"c.birth_date as dateOfBirth, ca.maximumBalance, ca.minimumBalance, ca.totalAccountBalance");
	}

	@Override
	protected void from() {

		sb.append(" from client c ");
	}

	@Override
	protected void leftJoin() {

		sb.append("left join charm ch on c.charm =ch.id  left join ( select client, avg(money) as totalAccountBalance, max(money) as maximumBalance, min(money) as minimumBalance " +
			"from client_account ca group by ca.client) ca on c.id = ca.client");
	}


	@Override
	protected void innerJoin() {

	}

	@Override
	protected void orderBy() {

		if (!clientFilter.orderBy.equals("")) {
			sb.append(" order by " + clientFilter.orderBy);
			if (clientFilter.sort)
//				param.add(" asc");
				sb.append(" asc");

			if (!clientFilter.sort)
//				param.add(" desc");
				sb.append(" desc");
		}


	}

	@Override
	protected void groupBy() {

	}

	@Override
	protected void offSet() {


		sb.append(" offset ? ");
		param.add((clientFilter.recordSize * clientFilter.page));

	}

	@Override
	protected void limit() {

		sb.append(" limit ? ");
		param.add(clientFilter.recordSize);

	}

	@Override
	protected void addRow(ClientRecord clientRecord) {

		clientRecords.add(clientRecord);
	}

	@Override
	protected List<ClientRecord> getResult() {

		return clientRecords;
	}


	@Override
	protected ClientRecord doInSelection(ResultSet rs) throws SQLException {

		ClientRecord clientRecord = new ClientRecord();

		clientRecord.id = rs.getInt("id");
		clientRecord.firstname = rs.getString("firstname");
		clientRecord.lastname = rs.getString("lastname");
		clientRecord.patronymic = rs.getString("patronymic");
		clientRecord.dateOfBirth = rs.getDate("dateOfBirth");
		clientRecord.characterName = rs.getString("character");
		clientRecord.totalAccountBalance = rs.getInt("totalAccountBalance");
		clientRecord.maximumBalance = rs.getInt("maximumBalance");
		clientRecord.minimumBalance = rs.getInt("minimumBalance");

		return clientRecord;

	}
}
