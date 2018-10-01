package kz.greetgo.sandbox.controller.report.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import kz.greetgo.sandbox.controller.model.model.ClientRecord;
import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class ReportViewPdf implements ReportView {

	private PrintStream printStream;

	private Document document = new Document();

	public ReportViewPdf(PrintStream printStream) {

		this.printStream = printStream;
	}

	private void initating() throws DocumentException {
		// TODO: asset 9/30/18 Nado obsudit
		document = new Document();
		document.setPageSize(PageSize.A4);
		PdfWriter.getInstance(document, printStream);
		document.open();
	}

	@Override
	public void start(MyReportHeadData headData) throws DocumentException {

		initating();
		PdfPTable table = new PdfPTable(9);
		table.addCell("id");
		table.addCell("firstname");
		table.addCell("lastname");
		table.addCell("patronymic");
		table.addCell("character");
		table.addCell("date of birth");
		table.addCell("maximum balance");
		table.addCell("total account balance");
		table.addCell("minimum balance");
		document.add(table);
	}

	@Override
	public void addRow(ClientRecord row) throws DocumentException {

		PdfPTable table = new PdfPTable(9);
		table.addCell(String.valueOf(row.id));
		table.addCell(row.firstname);
		table.addCell(row.lastname);
		table.addCell(row.patronymic);
		table.addCell(row.characterName);

		if (row.dateOfBirth == null)
			table.addCell("");
		else
			table.addCell(String.valueOf(row.dateOfBirth));
		table.addCell(String.valueOf(row.maximumBalance));
		table.addCell(String.valueOf(row.totalAccountBalance));
		table.addCell(String.valueOf(row.minimumBalance));
		document.add(table);
	}

	@Override
	public void finish(MyReportFootData footData) throws DocumentException {

		Paragraph paragraph = new Paragraph();
		paragraph.add("Author: " + footData.generatedBy + "\n");
		paragraph.add("Date: " + footData.generatedAt + "\n");
		document.add(paragraph);
	}

	@Override
	public void close() throws Exception {

		document.close();
		printStream.flush();
		printStream.close();
	}


	public static void main(String[] args) throws Exception {


		File file = new File("build/my_report/result.pdf");
		file.getParentFile().mkdir();
		try (FileOutputStream fileOutputStream = new FileOutputStream(file);) {

			try (PrintStream printStream = new PrintStream(fileOutputStream, false, "UTF-8")) {


				ReportViewPdf reportViewPdf = new ReportViewPdf(printStream);

				MyReportHeadData myReportHeadData = new MyReportHeadData();
				myReportHeadData.title = "cool";
				reportViewPdf.start(myReportHeadData);

				for (int i = 0; i < 100; i++) {
					ClientRecord clientRecord = new ClientRecord();
					clientRecord.id = i + 1;
					clientRecord.firstname = "col1" + i + 1;
					clientRecord.lastname = "col2" + i + 1;
					clientRecord.patronymic = "col3" + i + 1;
					clientRecord.characterName = "col3" + i + 1;
					clientRecord.dateOfBirth = new Date(i);
					clientRecord.maximumBalance = i;
					clientRecord.totalAccountBalance = i;
					clientRecord.minimumBalance = i;
					reportViewPdf.addRow(clientRecord);
				}
				MyReportFootData myReportFootData = new MyReportFootData();
				myReportFootData.generatedBy = "Nazar";
				myReportFootData.generatedAt = new Date();
				reportViewPdf.finish(myReportFootData);

			}
		}

	}


}
