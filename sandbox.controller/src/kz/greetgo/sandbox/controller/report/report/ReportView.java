package kz.greetgo.sandbox.controller.report.report;

import com.itextpdf.text.DocumentException;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;
import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;

public interface ReportView extends AutoCloseable {

	void start(MyReportHeadData headData) throws DocumentException;

	void addRow(ClientRecord clientRecord) throws DocumentException;

	void finish(MyReportFootData footData) throws DocumentException;
}
