package kz.greetgo.sandbox.controller.report.report;

import com.itextpdf.text.DocumentException;
import kz.greetgo.msoffice.xlsx.gen.Sheet;
import kz.greetgo.msoffice.xlsx.gen.Xlsx;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;
import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class ReportViewXlsx implements ReportView {

	private PrintStream printStream;

	private Xlsx f = new Xlsx();

	private Sheet sheet;

	public ReportViewXlsx(PrintStream printStream) {

		this.printStream = printStream;
	}

	private void initating() throws DocumentException {

		f = new Xlsx();
		sheet = f.newSheet(true);
		sheet.skipRows(3);
	}

	@Override
	public void start(MyReportHeadData headData) throws DocumentException {

		initating();
		sheet.row().start();
		sheet.style().alignment().horizontalCenter();
		sheet.cellStr(3, "ID");
		sheet.cellStr(4, "firstname");
		sheet.cellStr(5, "lastname");
		sheet.cellStr(6, "patronymic");
		sheet.cellStr(7, "character");
		sheet.cellStr(8, "date of birth");
		sheet.cellStr(9, "maximum balance");
		sheet.cellStr(10, "total account balance");
		sheet.cellStr(11, "minimum balance");
		sheet.row().finish();

	}


	@Override
	public void addRow(ClientRecord row) throws DocumentException {

		sheet.row().start();
		sheet.style().alignment().horizontalCenter();
		sheet.cellInt(3, row.id);
		sheet.cellStr(4, row.firstname);
		sheet.cellStr(5, row.lastname);
		sheet.cellStr(6, row.patronymic);
		sheet.cellStr(7, row.characterName);
		if (row.dateOfBirth != null)
			sheet.cellDMY(8, row.dateOfBirth);
		else {
			sheet.cellStr(8, "");
		}
		sheet.cellInt(9, row.maximumBalance);
		sheet.cellInt(10, row.totalAccountBalance);
		sheet.cellInt(11, row.minimumBalance);
		sheet.row().finish();
	}


	@Override
	public void finish(MyReportFootData footData) throws DocumentException {

		sheet.row().start();
		sheet.style().alignment().horizontalCenter();
		sheet.cellStr(3, "Author: " + footData.generatedBy);
		sheet.cellStr(4, "Date: " + footData.generatedAt);
		sheet.row().finish();

	}

	@Override
	public void close() throws Exception {

		sheet.row().finish();
		f.complete(printStream);
		printStream.flush();
		printStream.close();
	}


	public static void main(String[] args) throws Exception {

		String workDir = "build/my_report/result";
		File file = new File(workDir + ".xlsx");
		file.getParentFile().mkdir();
		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			try (PrintStream printStream = new PrintStream(fileOutputStream, false, "UTF-8")) {


				ReportViewXlsx reportViewXlsx = new ReportViewXlsx(printStream);


				MyReportHeadData myReportHeadData = new MyReportHeadData();
				myReportHeadData.title = "cool";
				reportViewXlsx.start(myReportHeadData);

				for (int i = 0; i < 100; i++) {
					ClientRecord clientRecord = new ClientRecord();
					clientRecord.id = i + 1;
					clientRecord.firstname = "col1" + i + 2;
					clientRecord.lastname = "col2" + i + 2;
					clientRecord.patronymic = "col3" + i + 2;
					clientRecord.characterName = "col3" + i + 2;
					clientRecord.dateOfBirth = new Date(i);
					clientRecord.maximumBalance = i;
					clientRecord.totalAccountBalance = i;
					clientRecord.minimumBalance = i;
					reportViewXlsx.addRow(clientRecord);
				}

				MyReportFootData myReportFootData = new MyReportFootData();
				myReportFootData.generatedBy = "Nazar";
				myReportFootData.generatedAt = new Date();
				reportViewXlsx.finish(myReportFootData);

			}
		}
	}
}
