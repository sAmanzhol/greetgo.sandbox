package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.register.MyBigReportRegister;
import kz.greetgo.sandbox.controller.report.my_big_report.MybigReportView;

import java.util.Date;

@Bean
public class MyBigReportRegisterStand implements MyBigReportRegister {

	@Override
	public void genReport(String userId, Date from, Date to, MybigReportView view) {

		userId="asd";
		from = new Date();
	}

	@Override
	public String fioByUserId(String userId) {

		return null;
	}
}
