package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.register.report.view.ReportView;

import java.util.Date;

public interface ReportRegister {

    public void generate(String userName,Date generateTime, ReportView reportView) throws Exception;

}
