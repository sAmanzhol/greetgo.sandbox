package kz.greetgo.sandbox.db.report.my_report_big_data;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.BeanGetter;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MyBigReportRegister {

	public BeanGetter<Jdbc> jdbc;

	public void genReport(String userId, Date from, Date to, MybigReportView view) {


		MyReportHeadData myReportHeadData = new MyReportHeadData();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		myReportHeadData.title="from"+simpleDateFormat.format(from)+"to" + simpleDateFormat.format(to);
		view.start(myReportHeadData);

		jdbc.get().execute(new MyBigReportJDBC(from,to,view));

		String fio = fioByUserId(userId);

		MyReportFootData myReportFootData = new MyReportFootData();
		myReportFootData.generatedBy=fio;
		myReportFootData.generatedAt= new Date();
		view.finish(myReportFootData);

	}

	private String fioByUserId(String userId) {
		//mozhno 4erez jdbc or DAO
		return "asd";
	}
}
