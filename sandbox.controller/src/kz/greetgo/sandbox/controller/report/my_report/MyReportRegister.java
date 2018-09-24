package kz.greetgo.sandbox.controller.report.my_report;

import kz.greetgo.depinject.core.Bean;

import java.util.Date;

@Bean
public class MyReportRegister {

	public void genReport(String userId, String contactId, MyReportView view) throws Exception {

		MyReportInData inData = getInDataFormDb(userId, contactId);
		view.generate(inData);

	}

	private MyReportInData getInDataFormDb(String userId, String contactId) {

		MyReportInData ret = new MyReportInData();
		ret.generatedAt = new Date();
		ret.generatedBy = "user " + userId;
		ret.param1 = "param1 for " + contactId;
		ret.param2 = "param2 for " + contactId;
		ret.param3 = "param3 for " + contactId;
		return ret;
	}
}
