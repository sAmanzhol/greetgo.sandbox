package kz.greetgo.sandbox.db.register_impl.jdbc;

import kz.greetgo.sandbox.controller.model.model.ClientFilter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientCountSql extends AbstractBasicSql<Integer, Integer> {

	protected Integer count;

	public ClientCountSql(ClientFilter clientFilter) {

		super(clientFilter);

	}

	@Override
	protected void innerJoin() {

	}

	@Override
	protected void offSet() {

	}

	@Override
	protected void select() {

		sb.append("select count(c.id) ");

	}


	@Override
	protected void leftJoin() {

	}

	@Override
	protected void orderBy() {

	}

	@Override
	protected void groupBy() {

	}

	@Override
	protected void limit() {

	}

	@Override
	protected void addRow(Integer integer) {

		count = integer;
	}

	@Override
	protected Integer getResult() {

		return count;
	}

	@Override
	protected Integer doInSelection(ResultSet rs) throws SQLException {

			return rs.getInt("count");

	}

}
