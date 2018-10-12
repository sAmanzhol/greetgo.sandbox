package kz.greetgo.sandbox.register.impl.reportRegisters;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportFootData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportHeadData;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.register.report.ClientReportJdbc;
import kz.greetgo.sandbox.register.report.view.ClientReportViewXLSX;
import kz.greetgo.sandbox.register.report.view.ReportView;

import java.text.SimpleDateFormat;
import java.util.Date;
@Bean
public class ClientReportRegister implements ReportRegister {
    private final String reportHeader = "Отчет по клиентам";
    public BeanGetter<Jdbc> jdbcBean;

    @Override
    public void generate(String userName, Date generateTime, ReportView reportView) throws Exception {

        ClientReportHeadData headData = new ClientReportHeadData(reportHeader);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        reportView.start(headData);

        jdbcBean.get().execute(new ClientReportJdbc(reportView));

        reportView.finish(new ClientReportFootData(userName,generateTime));
    }

}
