package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.report.my_big_report.MybigReportView;

import java.util.Date;

public interface MyBigReportRegister {

	 void genReport(String userId, Date from, Date to, MybigReportView view);

	String fioByUserId(String userId);
}
