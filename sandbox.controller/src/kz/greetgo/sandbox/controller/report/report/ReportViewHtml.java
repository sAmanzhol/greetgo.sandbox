package kz.greetgo.sandbox.controller.report.report;

import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;
import kz.greetgo.sandbox.controller.report.model.MyReportRow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ReportViewHtml implements ReportView {

	private final PrintStream printStream;

	public ReportViewHtml(PrintStream printStream) {

		this.printStream = printStream;
	}

	@Override
	public void start(MyReportHeadData headData) {

		printStream.println("<html>");
		printStream.println("<head>");
		printStream.println("<meta charset=\"UTF-8\" />");
		printStream.println("</head>");
		printStream.println("<body>");
		printStream.println("<h1> MY big report" + headData.title + "</h1>");
		printStream.println("<table>");
		printStream.println("<tr><th>Col1</th><th>Col2</th><th>Col2</th></tr>");
	}

	@Override
	public void addRow(MyReportRow row) {

		printStream.println("<tr><td>" + row.firstname + "</td><td>" + row.lastname+ "</td><td>" + row.patronymic+ "</td></tr>");

	}

	@Override
	public void finish(MyReportFootData footData) {

		printStream.println("</table>");
		printStream.println("Author " + footData.generatedBy + "<br>");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		printStream.println("Сгенерирован " + sdf.format(footData.generatedAt) + "<br>");
		printStream.println("</body>");
		printStream.println("</html>"); ;

	}

	public static void main(String[] args) throws Exception {

		File file = new File("build/report/My_Big_Report.html");
		file.getParentFile().mkdirs();

		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			try (PrintStream printStream = new PrintStream(fileOutputStream, false, "UTF-8")) {

				ReportViewHtml viewHtml = new ReportViewHtml(printStream);

				MyReportHeadData myReportHeadData = new MyReportHeadData();
				myReportHeadData.title = "Cool";
				viewHtml.start(myReportHeadData);

				for (int i = 0; i < 1000; i++) {
					MyReportRow myReportRow = new MyReportRow();
					myReportRow.firstname = "col1" + i;
					myReportRow.lastname = "col2" + i;
					myReportRow.patronymic = "col3" + i;
					viewHtml.addRow(myReportRow);
				}
				MyReportFootData myReportFootData = new MyReportFootData();
				myReportFootData.generatedBy = "Nazar";
				myReportFootData.generatedAt = new Date();
				viewHtml.finish(myReportFootData);
			}
		}
	}


}
