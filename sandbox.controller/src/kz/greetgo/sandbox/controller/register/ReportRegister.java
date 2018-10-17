package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.register.model.ReportParam;
import kz.greetgo.sandbox.controller.register.report.ReportType;
import kz.greetgo.sandbox.controller.register.report.ReportView;

import java.io.OutputStream;
import java.util.Date;

public interface ReportRegister {

    public void generate(ReportParam params) throws Exception;

}
