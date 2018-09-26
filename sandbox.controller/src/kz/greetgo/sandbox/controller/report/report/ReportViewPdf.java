package kz.greetgo.sandbox.controller.report.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import kz.greetgo.sandbox.controller.report.model.MyReportFootData;
import kz.greetgo.sandbox.controller.report.model.MyReportHeadData;
import kz.greetgo.sandbox.controller.report.model.MyReportRow;

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

		document = new Document();
		document.setPageSize(PageSize.A4);
		PdfWriter.getInstance(document, printStream);
		document.open();
	}

	@Override
	public void start(MyReportHeadData headData) throws DocumentException {

		initating();
		Paragraph paragraph = new Paragraph("");
		PdfPTable table = new PdfPTable(3);
		/*	table.getDefaultCell().setBorder(Rectangle.NO_BORDER);*/
		paragraph.add("TITLE: " + headData.title + "\n");
		table.addCell("COL1");
		table.addCell("COL1");
		table.addCell("COL1");
		document.add(paragraph);
		document.add(table);

	}

	@Override
	public void addRow(MyReportRow row) throws DocumentException {
		PdfPTable table = new PdfPTable(9);
		table.addCell(String.valueOf(row.id));
		table.addCell(row.firstname);
		table.addCell(row.lastname);
		table.addCell(row.patronymic);
		table.addCell(row.characterName);
		table.addCell(String.valueOf(row.dateOfBirth));
		table.addCell(String.valueOf(row.maximumBalance));
		table.addCell(String.valueOf(row.minimumBalance));
		table.addCell(String.valueOf(row.totalAccountBalance));
		document.add(table);
	}

	@Override
	public void finish(MyReportFootData footData) throws DocumentException {

		Paragraph paragraph = new Paragraph();
		paragraph.add("AUTHOR: " + footData.generatedBy + "\n");
		paragraph.add("DATE: " + footData.generatedAt + "\n");
		document.add(paragraph);
		document.close();
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
					MyReportRow myReportRow = new MyReportRow();
					myReportRow.firstname = "col1" + i + 1;
					myReportRow.lastname= "col2" + i + 1;
					myReportRow.patronymic = "col3" + i + 1;
					reportViewPdf.addRow(myReportRow);
				}

				MyReportFootData myReportFootData = new MyReportFootData();
				myReportFootData.generatedBy = "Nazar";
				myReportFootData.generatedAt = new Date();
				reportViewPdf.finish(myReportFootData);

			}
		}

	}


}
