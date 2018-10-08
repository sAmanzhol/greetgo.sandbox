package kz.greetgo.sandbox.controller.report;

import kz.greetgo.sandbox.controller.report.model.ClientReportFootData;
import kz.greetgo.sandbox.controller.report.model.ClientReportHeadData;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

public class ClientReportViewXlsx implements ClientReportView {

  private OutputStream printStream;

  private Workbook workbook = new XSSFWorkbook();
  private Sheet sheet;
  private int rowNum = 0;

  public ClientReportViewXlsx(OutputStream printStream) {
    this.printStream = printStream;
  }

  @Override
  public void start(ClientReportHeadData headData) {
    sheet = workbook.createSheet("Client Report");

    Row row = sheet.createRow(rowNum++);
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
    Row row = sheet.createRow(rowNum++);
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
    Row rowAuthor = sheet.createRow(rowNum++);
    rowAuthor.createCell(0).setCellValue("Author: ");
    rowAuthor.createCell(1).setCellValue(String.valueOf(footData.generatedBy));

    Row rowDate= sheet.createRow(rowNum++);
    rowDate.createCell(0).setCellValue("Generated at: ");
    rowDate.createCell(1).setCellValue(String.valueOf(footData.generatedAt));

    // Resize all columns to fit the content size
    for (int i = 0; i < 7; i++) {
      sheet.autoSizeColumn(i);
    }

    workbook.write(printStream);
  }

  public static void main(String[] args) throws Exception {
    File file = new File("build/report/test_report.xlsx");
    file.getParentFile().mkdir();

    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      try (PrintStream printStream = new PrintStream(fileOutputStream, false, "UTF-8")) {
        ClientReportViewXlsx viewXlsx = new ClientReportViewXlsx(printStream);

        ClientReportHeadData head = new ClientReportHeadData();
        head.title = "Отчет:";

        viewXlsx.start(head);

        for (int i = 0; i < 10; i++) {
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
