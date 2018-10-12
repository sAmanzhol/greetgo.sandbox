package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.annotations.on_methods.OnPost;
import kz.greetgo.mvc.interfaces.RequestTunnel;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.controller.security.PublicAccess;
import kz.greetgo.sandbox.register.report.view.ClientReportViewXLSX;
import kz.greetgo.sandbox.register.report.view.ReportView;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

@Bean
@ControllerPrefix("/report")
public class ReportController {
    public BeanGetter<ReportRegister> reportRegister;

    @PublicAccess
    @OnGet("/clients/xlsx")
    public void getClientsReport(RequestTunnel tunnel) throws Exception
    {
        tunnel.setResponseHeader("Content-Disposition", "attachment; filename=MyBigReport.xlsx");
        OutputStream out = tunnel.getResponseOutputStream();


        ReportView viewXLSX = new ClientReportViewXLSX(out);

        reportRegister.get().generate("Test",new Date(),viewXLSX);

    }
}
