package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.interfaces.RequestTunnel;
import kz.greetgo.sandbox.controller.register.MyBigReportRegister;
import kz.greetgo.sandbox.controller.report.my_big_report.MyBigReportViewHtml;
import kz.greetgo.sandbox.controller.util.Controller;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

@Bean
@Mapping("/report")
public class ReportController implements Controller {

	public BeanGetter<MyBigReportRegister> myBigReportRegister;


	@Mapping("/my_big_report")
	public void myBigReport(@Par("from") Date from, @Par("to") Date to, RequestTunnel tunnel) throws Exception {
		String userId = "asd";//got from session

		tunnel.setResponseHeader("Content-Disposition", "attachment; filename=result.html");
		OutputStream out = tunnel.getResponseOutputStream();

		PrintStream printStream = new PrintStream(out, false, "UTF-8");

		MyBigReportViewHtml viewHtml = new MyBigReportViewHtml(printStream);

		myBigReportRegister.get().genReport(userId, from, to, viewHtml);

		printStream.flush();
		tunnel.flushBuffer();
	}
}
