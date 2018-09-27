package kz.greetgo.sandbox.db.register_impl.jdbc;

import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;

import java.util.ArrayList;
import java.util.List;

public class ClientListSql extends AbstractClientListSql {

	List<ClientRecord> clientRecords = new ArrayList<>();

	public ClientListSql(ClientFilter clientFilter) {

		super(clientFilter);
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


}
