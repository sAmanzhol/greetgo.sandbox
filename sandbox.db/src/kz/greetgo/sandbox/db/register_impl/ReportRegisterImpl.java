package kz.greetgo.sandbox.db.register_impl;

import com.itextpdf.text.DocumentException;
import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;
import kz.greetgo.sandbox.controller.report.report.ReportView;
import kz.greetgo.sandbox.db.dao.AuthDao;
import kz.greetgo.sandbox.db.register_impl.jdbc.ClientReportSql;

import java.util.Date;

@Bean
public class ReportRegisterImpl implements ReportRegister {

    public BeanGetter<Jdbc> jdbc;
public BeanGetter<AuthDao> authDao;

    @Override
    public void genReport(ReportView view, ClientFilter clientFilter) throws DocumentException {

        MyReportHeadData myReportHeadData = new MyReportHeadData();
        myReportHeadData.title = "Report";

        view.start(myReportHeadData);

        jdbc.get().execute(new ClientReportSql(view, clientFilter));

        MyReportFootData myReportFootData = new MyReportFootData();
        myReportFootData.generatedBy = authDao.get().accountNameByPersonId("p1");
        myReportFootData.generatedAt = new Date();
        view.finish(myReportFootData);
    }

}