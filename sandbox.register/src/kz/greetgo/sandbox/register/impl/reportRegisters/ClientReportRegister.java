package kz.greetgo.sandbox.register.impl.reportRegisters;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportFootData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportHeadData;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.register.report.ClientReportJdbc;
import kz.greetgo.sandbox.register.report.view.ReportView;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
@Bean
public class ClientReportRegister implements ReportRegister {
    private final String REPORT_HEADER = "Отчет по клиентам";

    final Logger logger = Logger.getLogger(getClass());

    public BeanGetter<Jdbc> jdbcBean;

    @Override
    public void generate(String userName, Date generateTime, ReportView reportView) throws Exception {
        if(logger.isInfoEnabled())
        {
            logger.info("STARTING REPORT GENERATING BY " + userName );
        }
        ClientReportHeadData headData = new ClientReportHeadData(REPORT_HEADER);

        reportView.start(headData);

        jdbcBean.get().execute(new ClientReportJdbc(reportView));

        reportView.finish(new ClientReportFootData(userName,generateTime));

        if(logger.isInfoEnabled())
        {
            logger.info("FINISHED REPORT GENERATING");
        }
    }

}
