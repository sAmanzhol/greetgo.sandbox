package kz.greetgo.sandbox.db.register_impl;

import com.itextpdf.text.DocumentException;
import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;
import kz.greetgo.sandbox.controller.report.model.MyReportRow;
import kz.greetgo.sandbox.controller.report.report.ReportView;
import kz.greetgo.sandbox.db.register_impl.jdbc.ReportSql;

import java.util.Date;

@Bean
public class ReportRegisterImpl implements ReportRegister {

	public BeanGetter<Jdbc> jdbc;

	@Override
	public void genReport(ReportView view, ClientFilter clientFilter) throws DocumentException {

		MyReportHeadData myReportHeadData = new MyReportHeadData();
		myReportHeadData.title = "FROM";
		view.start(myReportHeadData);

		MyReportRow myReportRow = new MyReportRow();

		jdbc.get().execute(new ReportSql(view, clientFilter));

		MyReportFootData myReportFootData = new MyReportFootData();
		myReportFootData.generatedBy = "Nazar";
		myReportFootData.generatedAt = new Date();
		view.finish(myReportFootData);

	}

}