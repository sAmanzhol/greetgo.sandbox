package kz.greetgo.sandbox.controller.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.report.model.ClientReportFootData;
import kz.greetgo.sandbox.controller.report.model.ClientReportHeadData;
import kz.greetgo.util.RND;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ClientReportViewPdf implements ClientReportView {

  private Document document;

  private static final String FONT = "kz/greetgo/sandbox/controller/report/fonts/FreeSans.ttf";
  private Font cyrillicFont;

  public ClientReportViewPdf(OutputStream outputStream) throws Exception {

    BaseFont baseFont = BaseFont.createFont(FONT, "cp1251", BaseFont.EMBEDDED);
    cyrillicFont = new Font(baseFont, 12, Font.NORMAL);

    this.document = new Document();

    PdfWriter.getInstance(this.document, outputStream);
    this.document.open();
  }

  @Override
  public void start(ClientReportHeadData headData) throws Exception {
    PdfPTable table = new PdfPTable(7);
    table.addCell(new Paragraph("Id", cyrillicFont));
    table.addCell(new Paragraph("Full name", cyrillicFont));
    table.addCell(new Paragraph("Character", cyrillicFont));
    table.addCell(new Paragraph("Age", cyrillicFont));
    table.addCell(new Paragraph("Total balance", cyrillicFont));
    table.addCell(new Paragraph("Maximum balance", cyrillicFont));
    table.addCell(new Paragraph("Minimum balance", cyrillicFont));
    this.document.add(table);
  }

  @Override
  public void addRow(ClientRecord clientRecord) throws Exception {
    PdfPTable table = new PdfPTable(7);
    table.addCell(new Paragraph(String.valueOf(clientRecord.id), cyrillicFont));
    table.addCell(new Paragraph(String.valueOf(clientRecord.fio), cyrillicFont));
    table.addCell(new Paragraph(String.valueOf(clientRecord.character), cyrillicFont));
    table.addCell(new Paragraph(String.valueOf(clientRecord.age), cyrillicFont));
    table.addCell(new Paragraph(String.valueOf(clientRecord.balance), cyrillicFont));
    table.addCell(new Paragraph(String.valueOf(clientRecord.balanceMax), cyrillicFont));
    table.addCell(new Paragraph(String.valueOf(clientRecord.balanceMin), cyrillicFont));
    document.add(table);
  }

  @Override
  public void finish(ClientReportFootData footData) throws Exception {
    Paragraph paragraph = new Paragraph(String.format("Author: %s \n Created at: %s", String.valueOf(footData.generatedBy), String.valueOf(footData.generatedAt)), cyrillicFont);
    this.document.add(paragraph);

    this.document.close();
  }


  public static void main(String[] args) throws Exception {
    File file = new File("build/report/test_report.pdf");
    file.getParentFile().mkdirs();

    try (OutputStream outputStream = new PrintStream(new FileOutputStream(file), false, "UTF-8")) {
      ClientReportViewPdf viewPdf = new ClientReportViewPdf(outputStream);

      ClientReportHeadData head = new ClientReportHeadData();
      head.title = "Отчет:";

      viewPdf.start(head);

      for (int i = 0; i < 1_000_000; i++) {
        ClientRecord row = new ClientRecord();
        row.id = i;
        row.fio = RND.str(5) + " " + RND.str(5) + RND.str(5);
        row.character = RND.str(7);
        row.age = RND.plusInt(40);
        row.balance = RND.plusInt(100000);
        row.balanceMax = RND.plusInt(10000000);
        row.balanceMin = RND.plusInt(10000);

        viewPdf.addRow(row);
      }

      ClientReportFootData foot = new ClientReportFootData();
      foot.generatedBy = "De Sali";
      foot.generatedAt = new Date();

      viewPdf.finish(foot);
    }
  }
}
