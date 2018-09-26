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

public class ClientReportSql extends AbstractClientListSql<MyReportRow, List<ClientRecord>> {

    private final ReportView view;

    public ClientReportSql(ReportView view, ClientFilter clientFilter) {
        super(clientFilter);
        this.view = view;
    }

    @Override
    protected void addRow(MyReportRow myReportRow) throws DocumentException {
        view.addRow(myReportRow);
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
