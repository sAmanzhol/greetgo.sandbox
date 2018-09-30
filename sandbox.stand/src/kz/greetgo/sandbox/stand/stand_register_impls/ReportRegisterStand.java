package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.controller.report.report.ReportView;

@Bean
public class ReportRegisterStand implements ReportRegister {

	@Override
	public void genReport(ReportView view, ClientFilter clientFilter) {

	}
}
