package kz.greetgo.sandbox.controller.report.report;

import com.itextpdf.text.DocumentException;
import kz.greetgo.msoffice.xlsx.gen.Sheet;
import kz.greetgo.msoffice.xlsx.gen.Xlsx;
import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;
import kz.greetgo.sandbox.controller.report.model.MyReportRow;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class ReportViewXlsx implements ReportView {
	private PrintStream printStream;

	public ReportViewXlsx(PrintStream printStream) {
		this.printStream=printStream;
	}

	private void initating() throws DocumentException {

	}

	@Override
	public void start(MyReportHeadData headData) throws DocumentException {
				initating();

	}

	@Override
	public void addRow(MyReportRow row) throws DocumentException {

	}

	@Override
	public void finish(MyReportFootData footData) throws DocumentException {

	}

	public static void main(String[] args) throws Exception {
		int rowCount = 9000;

		String tmpDir = System.getProperty("user.name") + "/trans/tmp";
//		String tmpDir = System.getProperty("build/my_report/");
		String workDir = tmpDir + "/generated_xlsx_" + rowCount;
//		String workDir = tmpDir + rowCount;


		long time1 = System.currentTimeMillis();

		Xlsx f = new Xlsx();
		f.setWorkDir(workDir);

		Sheet sheet = f.newSheet(true);
		sheet.skipRows(3);

		for (int row = 0; row < rowCount; row++) {
			sheet.row().start();
			sheet.cellInt(1, 123);

			sheet.style().alignment().horizontalCenter();
			for (int i = 0; i < 20; i++) {
				if (i == 10) sheet.style().alignment().clean();
				sheet.cellStr(2 + i, "Ячейка Ы" + i + "_" + row + " of " + rowCount);
			}

			sheet.row().finish();
		}



		long time2 = System.currentTimeMillis();

		System.out.println("Time to form of " + rowCount + " is " + (time2 - time1) / 1000.0 + " c");

		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(tmpDir + "/generated_" + rowCount + ".xlsx");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		f.complete(fOut);

		long time3 = System.currentTimeMillis();

		System.out.println("Time to print of " + rowCount + " is " + (time3 - time2) / 1000.0 + " c");
		System.out.println("Total time of " + rowCount + " is " + (time3 - time1) / 1000.0 + " c");
		System.out.println("COMPLETE");
	}
}
