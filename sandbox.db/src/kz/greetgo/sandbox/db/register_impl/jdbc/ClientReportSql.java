package kz.greetgo.sandbox.db.register_impl.jdbc;

import com.itextpdf.text.DocumentException;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;
import kz.greetgo.sandbox.controller.report.report.ReportView;

import java.util.List;

public class ClientReportSql extends AbstractClientListSql {

	private final ReportView view;

	public ClientReportSql(ReportView view, ClientFilter clientFilter) {

		super(clientFilter);
		this.view = view;
	}

	@Override
	protected void addRow(ClientRecord clientRecord) throws DocumentException {

		view.addRow(clientRecord);
	}
	@Override
	protected List<ClientRecord> getResult() {
		return null;
	}

}
