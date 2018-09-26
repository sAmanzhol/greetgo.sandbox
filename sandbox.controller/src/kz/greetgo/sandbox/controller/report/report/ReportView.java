package kz.greetgo.sandbox.controller.report.report;

import com.itextpdf.text.DocumentException;
import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;
import kz.greetgo.sandbox.controller.report.model.MyReportRow;

public interface ReportView {

	void start(MyReportHeadData headData) throws DocumentException;

	void addRow(MyReportRow row) throws DocumentException;

	void finish(MyReportFootData footData) throws DocumentException;
}
