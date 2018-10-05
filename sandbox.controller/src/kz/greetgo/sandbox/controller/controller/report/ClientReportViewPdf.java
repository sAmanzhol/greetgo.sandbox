package kz.greetgo.sandbox.controller.controller.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import kz.greetgo.sandbox.controller.controller.report.model.ClientReportFootData;
import kz.greetgo.sandbox.controller.controller.report.model.ClientReportHeadData;
import kz.greetgo.sandbox.controller.model.ClientRecord;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class ClientReportViewPdf implements ClientReportView {

  private PrintStream printStream;

  private Document document = new Document();

  public ClientReportViewPdf(PrintStream printStream) {
    this.printStream = printStream;
  }

  @Override
  public void start(ClientReportHeadData headData) throws Exception {
    document = new Document();
    PdfWriter.getInstance(document, printStream);
    document.open();

    PdfPTable table = new PdfPTable(7);
    table.addCell("Id");
    table.addCell("Full name");
    table.addCell("Character");
    table.addCell("Age");
    table.addCell("Total balance");
    table.addCell("Maximum balance");
    table.addCell("Minimum balance");
    document.add(table);
  }

  @Override
  public void addRow(ClientRecord row) throws Exception {
    PdfPTable table = new PdfPTable(7);
    table.addCell(String.valueOf(row.id));
    table.addCell(row.fio);
    table.addCell(row.character);
    table.addCell(String.valueOf(row.age));
    table.addCell(String.valueOf(row.balance));
    table.addCell(String.valueOf(row.balanceMax));
    table.addCell(String.valueOf(row.balanceMin));
    document.add(table);
  }

  @Override
  public void finish(ClientReportFootData footData) throws Exception {
    Paragraph paragraph = new Paragraph();
    paragraph.add(String.format("Author: %s \n Created at: %s", footData.generatedBy, String.valueOf(footData.generatedAt)));
    document.add(paragraph);

    document.close();
  }


  public static void main(String[] args) throws Exception {
    File file = new File("build/report/test_report.pdf");
    file.getParentFile().mkdir();

    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      try (PrintStream printStream = new PrintStream(fileOutputStream, false, "UTF-8")) {
        ClientReportViewPdf viewPdf = new ClientReportViewPdf(printStream);

        ClientReportHeadData head = new ClientReportHeadData();
        head.title = "Отчет:";

        viewPdf.start(head);

        for (int i = 0; i < 10; i++) {
          ClientRecord row = new ClientRecord();
          row.id = i;
          row.fio = "fio of " + i;
          row.character = "character of " + i;
          row.age = i * 2;
          row.balance = i * 2;
          row.balanceMax = i * 2;
          row.balanceMin = i * 2;

          viewPdf.addRow(row);
        }

        ClientReportFootData foot = new ClientReportFootData();
        foot.generatedBy = "Desali";
        foot.generatedAt = new Date();

        viewPdf.finish(foot);
      }
    }
  }
}
