package kz.greetgo.sandbox.controller.register;

import com.itextpdf.text.DocumentException;
import kz.greetgo.sandbox.controller.model.model.ClientFilter;
import kz.greetgo.sandbox.controller.report.report.ReportView;

public interface ReportRegister {

	void genReport(ReportView view, ClientFilter clientFilter) throws DocumentException;
}
