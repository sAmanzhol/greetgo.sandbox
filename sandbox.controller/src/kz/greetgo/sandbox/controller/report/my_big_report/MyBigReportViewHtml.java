package kz.greetgo.sandbox.controller.report.my_big_report;

import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;
import kz.greetgo.sandbox.controller.report.model.MyReportRow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyBigReportViewHtml implements MybigReportView {

	private final PrintStream printStream;

	public MyBigReportViewHtml(PrintStream printStream) {

		this.printStream = printStream;
	}

	@Override
	public void start(MyReportHeadData headData) {
		printStream.println("<html>");
		printStream.println("<head>");
		printStream.println("<meta charset=\"UTF-8\" />");
		printStream.println("</head>");
		printStream.println("<body>");
		printStream.println("<h1> MY big report" + headData.title +"</h1>");
		printStream.println("<table>");
		printStream.println("<tr><th>Col1</th><th>Col2</th><th>Col2</th></tr>");
		}

	@Override
	public void addRow(MyReportRow row) {
		printStream.println("<tr><td>"+row.col1+"</td><td>"+row.col2+"</td><td>"+row.col2+"</td></tr>");


	}

	@Override
	public void finish(MyReportFootData footData) {

		printStream.println("</table>");
		printStream.println("Author " + footData.generatedBy +"<br>");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		printStream.println("Сгенерирован " + sdf.format(footData.generatedAt) +"<br>");
		printStream.println("</body>");
		printStream.println("</html>");;

	}

	public static void main(String[] args) throws Exception {
		File file = new File("build/my_big_report/result.html");
		file.getParentFile().mkdir();
		try (
			FileOutputStream fileOutputStream=new FileOutputStream(file);
		){
			try(PrintStream printStream=new PrintStream(fileOutputStream,false,"UTF-8")){

				MyBigReportViewHtml viewHtml = new MyBigReportViewHtml(printStream);

				MyReportHeadData myReportHeadData = new MyReportHeadData();
				myReportHeadData.title="Cool";
				viewHtml.start(myReportHeadData);

				for (int i=0;i<1000;i++){
					MyReportRow myReportRow = new MyReportRow();
					myReportRow.col1="col1"+i;
					myReportRow.col2="col2"+i;
					myReportRow.col3="col3"+i;
					viewHtml.addRow(myReportRow);
				}
				MyReportFootData myReportFootData = new MyReportFootData();
				myReportFootData.generatedBy="Nazar";
				myReportFootData.generatedAt = new Date();
				viewHtml.finish(myReportFootData);
 			}
		}
	}


}
