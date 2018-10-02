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

    @Mapping("/{content_type}")
    public void myBigReport(@ParPath("content_type") String contentType, @Par("clientFilter") @Json ClientFilter clientFilter, RequestTunnel tunnel) throws Exception {

        tunnel.setResponseContentType(contentType);
        tunnel.setResponseHeader("Content-Disposition", "attachment; filename=result." + contentType);
        OutputStream out = tunnel.getResponseOutputStream();


        try (ReportView view = getReportView(contentType, new PrintStream(out, false, "UTF-8"))) {


            myBigReportRegister.get().genReport(view, clientFilter);


            tunnel.flushBuffer();
        }


    }

    private ReportView getReportView(String contentType, PrintStream printStream) {

        switch (contentType) {
            case "pdf":
                return new ReportViewPdf(printStream);
            case "xlsx":
                return new ReportViewXlsx(printStream);
        }
        throw new RuntimeException("Unknown type = " + contentType);
    }

}
