package kz.greetgo.sandbox.db.register_impl.jdbc;

import com.itextpdf.text.DocumentException;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;
import kz.greetgo.sandbox.controller.report.model.MyReportRow;
import kz.greetgo.sandbox.controller.report.report.ReportView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportSql extends AbstractBasicSql<MyReportRow, List<ClientRecord>> {

	List<ClientRecord> clientRecords = new ArrayList<>();


	private final ReportView view;

	public ReportSql(ReportView view, ClientFilter clientFilter) {
		super(clientFilter);
		this.view = view;
	}


	@Override
	protected void innerJoin() {

	}

	@Override
	protected void offSet() {

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
	protected void limit() {

	}

	@Override
	protected void addRow(MyReportRow myReportRow) throws DocumentException {
		view.addRow(myReportRow);
	}

	@Override
	protected List<ClientRecord> getResult() {

		return null;
	}


	@Override
	protected MyReportRow doInSelection(ResultSet rs) throws SQLException {
		MyReportRow ret = new MyReportRow();
		ret.id = rs.getInt("id");
		ret.firstname = rs.getString("firstname");
		ret.lastname = rs.getString("lastname");
		ret.patronymic = rs.getString("patronymic");
		ret.dateOfBirth = rs.getDate("dateOfBirth");
		ret.characterName = rs.getString("character");
		ret.totalAccountBalance = rs.getInt("totalAccountBalance");
		ret.maximumBalance = rs.getInt("maximumBalance");
		ret.minimumBalance = rs.getInt("minimumBalance");
		return ret;
	}
}
