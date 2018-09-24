package kz.greetgo.sandbox.controller.report.my_big_report;

import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;
import kz.greetgo.sandbox.controller.report.model.MyReportRow;

public interface MybigReportView {

	void start(MyReportHeadData headData);

	void addRow(MyReportRow row);

	void finish(MyReportFootData footData);
}
