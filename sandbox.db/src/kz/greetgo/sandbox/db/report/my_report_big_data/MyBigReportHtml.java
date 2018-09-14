package kz.greetgo.sandbox.db.report.my_report_big_data;

import java.io.OutputStream;

public class MyBigReportHtml implements MybigReport {

	private final OutputStream outputStream;

	public MyBigReportHtml(OutputStream outputStream) {

		this.outputStream = outputStream;
	}

	@Override
	public void start(MyReportHeadData headData) {

	}

	@Override
	public void addRow(MyReportRow row) {

	}

	@Override
	public void finish(MyReportFootData footData) {

	}
}
