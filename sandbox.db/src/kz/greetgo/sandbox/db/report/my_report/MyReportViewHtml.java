package kz.greetgo.sandbox.db.report.my_report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyReportViewHtml implements MyReportView {

	private final OutputStream outputStream;

	public MyReportViewHtml(OutputStream outputStream) {this.outputStream = outputStream;}

	@Override
	public void generate(MyReportInData inData) throws Exception {

		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<h1> MY REPORT</h1>");
		sb.append("<b> inData Param1 = ").append(inData.param1).append("<br>");
		sb.append("<b> inData Param2 = ").append(inData.param2).append("<br>");
		sb.append("<b> inData Param3 = ").append(inData.param3).append("<br>");
		if(inData.generatedBy != null){
			sb.append("<b> inData Param4 = ").append(inData.generatedBy).append("<br>");
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm");
			sb.append("<b> inData Param5 = ").append(sdf.format(inData.generatedAt)).append("<br>");


		}

		sb.append("</body>");
		sb.append("</html>");
		outputStream.write(sb.toString().getBytes(StandardCharsets.UTF_8));

	}

	public static void main(String[] args) throws Exception {

		File file = new File("build/my_report/result.html");
		file.getParentFile().mkdir();
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			MyReportViewHtml myReportViewHtml = new MyReportViewHtml(outputStream);
			MyReportInData inData = new MyReportInData();
			inData.param1="Asd";
			inData.param2="DSA";
			inData.param3="WOW";
			inData.generatedBy="Abu Nazar";
			inData.generatedAt = new Date();
			myReportViewHtml.generate(inData);

		}

	}
}
