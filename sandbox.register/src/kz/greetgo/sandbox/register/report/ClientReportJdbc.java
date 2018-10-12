package kz.greetgo.sandbox.register.report;

import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportRow;
import kz.greetgo.sandbox.register.report.view.ReportView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class ClientReportJdbc implements ConnectionCallback<Void> {
    private final ReportView reportView;
    private final String SELECT_STATEMENT = "select c.id,c.surname,c.name,c.patronymic, ch.name as charm_name,c.birth_date " +
            "  ,(SELECT max(acc.money) as maxbal " +
            "    from client_account acc where acc.client = c.id) " +
            "  ,(SELECT min(acc.money) as minbal " +
            "    from client_account acc where acc.client = c.id) " +
            "  ,(SELECT sum(acc.money) as sumbal " +
            "   from client_account acc where acc.client = c.id) " +
            " " +
            "from clients c,charms ch " +
            "where c.charm = ch.id and c.actual = true";

    public ClientReportJdbc(ReportView reportView) {
        this.reportView = reportView;
    }

    @Override
    public Void doInConnection(Connection con) throws Exception {
                try(PreparedStatement preparedStatement = con.prepareStatement(SELECT_STATEMENT))
                {
                    try(ResultSet rs = preparedStatement.executeQuery())
                    {
                        while (rs.next())
                        {
                            reportView.addRow(resultToRow(rs));
                        }
                    }
                }
                return null;
        }

    private ClientReportRow resultToRow(ResultSet rs) throws Exception {
        return new ClientReportRow(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                calculateAge(rs.getTimestamp(6)),
                rs.getDouble(7),
                rs.getDouble(8),
                rs.getDouble(9)
        );
    }

    public static Long calculateAge(Date date) {
        LocalDate birthDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate =  LocalDate.now();
        if ((birthDate != null)) {
            return (long) Period.between(birthDate, currentDate).getYears();
        } else {
            return 0l;
        }

    }

}
