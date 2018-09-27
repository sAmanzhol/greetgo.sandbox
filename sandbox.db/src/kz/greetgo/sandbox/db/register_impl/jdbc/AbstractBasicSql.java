package kz.greetgo.sandbox.db.register_impl.jdbc;

import kz.greetgo.sandbox.controller.model.model.ClientFilter;

public abstract class AbstractBasicSql<T, R> extends AbstractConnectionCallBack<T, R> {

	protected ClientFilter clientFilter;

	public AbstractBasicSql(ClientFilter clientFilter) {

		this.clientFilter = clientFilter;
	}

	@Override
	protected void from() {
		sb.append(" from client c ");
	}

	@Override
	public void where() {

		sb.append(" where 1=1 ");

		if (!clientFilter.firstname.equals("")) {
			sb.append(" and firstname like ? ");
			param.add(clientFilter.firstname + "%");
		}
		if (!clientFilter.lastname.equals("")) {
			sb.append(" and lastname like ? ");
			param.add(clientFilter.lastname + "%");
		}
		if (!clientFilter.patronymic.equals("")) {
			sb.append(" and patronymic like ? ");
			param.add(clientFilter.patronymic + "%");
		}


	}
}
