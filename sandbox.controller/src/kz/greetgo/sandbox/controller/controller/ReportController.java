package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Json;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ParPath;
import kz.greetgo.mvc.interfaces.RequestTunnel;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.register.ReportRegister;
import kz.greetgo.sandbox.controller.report.report.ReportView;
import kz.greetgo.sandbox.controller.report.report.ReportViewPdf;
import kz.greetgo.sandbox.controller.report.report.ReportViewXlsx;
import kz.greetgo.sandbox.controller.security.NoSecurity;
import kz.greetgo.sandbox.controller.util.Controller;

import java.io.OutputStream;
import java.io.PrintStream;

@Bean
@Mapping("/report")
@NoSecurity
public class ReportController implements Controller {

    public BeanGetter<ReportRegister> myBigReportRegister;

    @Mapping("/report-{ContentType}")
    public void myBigReport(@ParPath("ContentType") String contentType, @Par("clientFilter") @Json ClientFilter clientFilter, RequestTunnel tunnel) throws Exception {


        tunnel.setResponseContentType(contentType);

        OutputStream out = tunnel.getResponseOutputStream();

        try(PrintStream printStream = new PrintStream(out, false, "UTF-8")) {


            ReportView view = null;

            if (contentType.equals("pdf")) {
                tunnel.setResponseHeader("content-disposition", "attachment; filename=result.pdf");
                view = new ReportViewPdf(printStream);
            }
            if (contentType.equals("xlsx")) {
                tunnel.setResponseHeader("content-disposition", "attachment; filename=result.xlsx");
                view = new ReportViewXlsx(printStream);
            }

            myBigReportRegister.get().genReport(view, clientFilter);


            printStream.flush();
            tunnel.flushBuffer();
        }
    }

}
