package kz.greetgo.sandbox.controller.report;

import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.report.model.ClientReportFootData;
import kz.greetgo.sandbox.controller.report.model.ClientReportHeadData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ClientReportViewXlsx implements ClientReportView {

  private OutputStream outputStream;

  private Workbook workbook;
  private Sheet sheet;
  private int rowNum = 0;

  public ClientReportViewXlsx(OutputStream outputStream) {
    this.outputStream = outputStream;
    this.workbook = new SXSSFWorkbook();
  }

  @Override
  public void start(ClientReportHeadData headData) {
    this.sheet = this.workbook.createSheet("Client Report");

    Row row = this.sheet.createRow(rowNum++);
    row.createCell(0).setCellValue("Id");
    row.createCell(1).setCellValue("Full name");
    row.createCell(2).setCellValue("Character");
    row.createCell(3).setCellValue("Age");
    row.createCell(4).setCellValue("Total balance");
    row.createCell(5).setCellValue("Maximum balance");
    row.createCell(6).setCellValue("Minimum balance");
  }

  @Override
  public void addRow(ClientRecord clientRecord) {
    Row row = this.sheet.createRow(rowNum++);
    row.createCell(0).setCellValue(String.valueOf(clientRecord.id));
    row.createCell(1).setCellValue(String.valueOf(clientRecord.fio));
    row.createCell(2).setCellValue(String.valueOf(clientRecord.character));
    row.createCell(3).setCellValue(String.valueOf(clientRecord.age));
    row.createCell(4).setCellValue(String.valueOf(clientRecord.balance));
    row.createCell(5).setCellValue(String.valueOf(clientRecord.balanceMax));
    row.createCell(6).setCellValue(String.valueOf(clientRecord.balanceMin));
  }

  @Override
  public void finish(ClientReportFootData footData) throws Exception {
    Row rowAuthor = this.sheet.createRow(rowNum++);
    rowAuthor.createCell(0).setCellValue("Author: ");
    rowAuthor.createCell(1).setCellValue(String.valueOf(footData.generatedBy));

    Row rowDate = this.sheet.createRow(rowNum++);
    rowDate.createCell(0).setCellValue("Generated at: ");
    rowDate.createCell(1).setCellValue(String.valueOf(footData.generatedAt));

    this.workbook.write(this.outputStream);
  }

  public static void main(String[] args) throws Exception {
    File file = new File("build/report/test_report.xlsx");
    file.getParentFile().mkdirs();

    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      try (OutputStream outputStream = new PrintStream(fileOutputStream, false, "UTF-8")) {
        ClientReportViewXlsx viewXlsx = new ClientReportViewXlsx(outputStream);

        ClientReportHeadData head = new ClientReportHeadData();
        head.title = "Отчет:";

        viewXlsx.start(head);

        for (int i = 0; i < 1_000_000; i++) {
          ClientRecord row = new ClientRecord();
          row.id = i;
          row.fio = "fio of " + i;
          row.character = "character of " + i;
          row.age = i * 2;
          row.balance = i * 2;
          row.balanceMax = i * 2;
          row.balanceMin = i * 2;

          viewXlsx.addRow(row);
        }

        ClientReportFootData foot = new ClientReportFootData();
        foot.generatedBy = "Desali";
        foot.generatedAt = new Date();

        viewXlsx.finish(foot);
      }
    }
  }
}
