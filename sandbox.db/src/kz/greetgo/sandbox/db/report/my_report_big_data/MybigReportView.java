package kz.greetgo.sandbox.db.report.my_report_big_data;

public interface MybigReportView {

	void start( MyReportHeadData headData);

	void addRow(MyReportRow row);

	void finish(MyReportFootData footData);
}
