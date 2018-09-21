package kz.greetgo.sandbox.db.report.my_report_big_data;

import kz.greetgo.db.ConnectionCallback;

import java.sql.*;
import java.util.Date;

public class MyBigReportJDBC implements ConnectionCallback<Void> {

	private final Date from;

	private final Date to;

	private final MybigReportView view;

	public MyBigReportJDBC(Date from, Date to, MybigReportView view) {
		this.from = from;
		this.to = to;
		this.view = view;
	}

	@Override
	public Void doInConnection(Connection connection) throws Exception {

		try(PreparedStatement ps = connection.prepareStatement(generateSql().toString())){
			ps.setTimestamp(1,new Timestamp(from.getTime()));
			ps.setTimestamp(2,new Timestamp(to.getTime()));
			try(ResultSet rs =ps.executeQuery()){

				while(rs.next()){
					view.addRow(rsToRow(rs));
				}
				return null;
			}
		}

	}

	private StringBuilder generateSql() {
	StringBuilder sb = new StringBuilder();
	appendSelect(sb);
	sb.append("from ....");
	sb.append("where ...");
	return sb;
	}

	private void appendSelect(StringBuilder sb) {
		sb.append("select	... as  col1 ... as col2 ... as col3");

	}

	private MyReportRow rsToRow(ResultSet rs)  throws SQLException {
		MyReportRow ret = new MyReportRow();
		ret.col1 = rs.getString("col1");
		ret.col2 = rs.getString("col2");
		ret.col3 = rs.getString("col3");

		return  null;
	}
}
