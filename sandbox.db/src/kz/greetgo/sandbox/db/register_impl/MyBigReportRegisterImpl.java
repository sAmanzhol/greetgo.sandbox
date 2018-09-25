package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.register.MyBigReportRegister;
import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;
import kz.greetgo.sandbox.controller.report.my_big_report.MybigReportView;
import kz.greetgo.sandbox.db.register_impl.jdbc.MyBigReportJDBC;

import java.text.SimpleDateFormat;
import java.util.Date;

@Bean
public class MyBigReportRegisterImpl implements MyBigReportRegister {

	public BeanGetter<Jdbc> jdbc;


	public void genReport(String userId, Date from, Date to, MybigReportView view) {


		MyReportHeadData myReportHeadData = new MyReportHeadData();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		myReportHeadData.title = "from" + simpleDateFormat.format(from) + "to" + simpleDateFormat.format(to);
		view.start(myReportHeadData);

		jdbc.get().execute(new MyBigReportJDBC(from, to, view));

		String fio = fioByUserId(userId);

		MyReportFootData myReportFootData = new MyReportFootData();
		myReportFootData.generatedBy = fio;
		myReportFootData.generatedAt = new Date();
		view.finish(myReportFootData);

	}


	public String fioByUserId(String userId) {
		//mozhno 4erez jdbc or DAO
		return "asd";
	}

}