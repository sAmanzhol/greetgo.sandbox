package kz.greetgo.sandbox.register.impl.reportRegisters;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportFootData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportHeadData;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.controller.register.model.ReportParam;
import kz.greetgo.sandbox.controller.register.report.ReportType;
import kz.greetgo.sandbox.controller.register.report.ReportView;
import kz.greetgo.sandbox.register.report.ClientReportJdbc;
import kz.greetgo.sandbox.register.report.view.ClientReportViewPDF;
import kz.greetgo.sandbox.register.report.view.ClientReportViewXLSX;
import org.apache.log4j.Logger;

import java.io.OutputStream;
import java.util.Date;
@Bean
public class ClientReportRegister implements ReportRegister {
    private final String REPORT_HEADER = "Отчет по клиентам";

    final Logger logger = Logger.getLogger(getClass());
    ReportView reportView = null;
    public BeanGetter<Jdbc> jdbcBean;

    @Override
    public void generate(ReportParam param) throws Exception {
        if(logger.isInfoEnabled())
        {
            logger.info("STARTING REPORT GENERATING BY " + param.username );
        }

        switch (param.type){
            case PDF:{reportView = new ClientReportViewPDF(param.out);break;}
            case XLSX:{reportView = new ClientReportViewXLSX(param.out);break;}
        }

        ClientReportHeadData headData = new ClientReportHeadData(REPORT_HEADER);

        reportView.start(headData);

        jdbcBean.get().execute(new ClientReportJdbc(reportView));

        reportView.finish(new ClientReportFootData(param.username,param.date));

        if(logger.isInfoEnabled())
        {
            logger.info("FINISHED REPORT GENERATING");
        }
    }

}
