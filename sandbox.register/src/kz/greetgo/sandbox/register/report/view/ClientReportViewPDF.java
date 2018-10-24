package kz.greetgo.sandbox.register.report.view;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportFootData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportHeadData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportRow;
import kz.greetgo.sandbox.controller.register.report.ReportView;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientReportViewPDF implements ReportView {
    final int TABLE_COL_SIZE = 6;
    final int FONT_SIZE = 13;

    OutputStream outputStream;
    Document document = null;
    PdfPTable table = new PdfPTable(TABLE_COL_SIZE);
    Font font = null;

    public ClientReportViewPDF(OutputStream outputStream) {

        this.outputStream = outputStream;
        try {
            BaseFont bf = BaseFont.createFont(
                    getClass()
                            .getClassLoader()
                            .getResource("./fonts/timesi.ttf").toString(),
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED);
            //подключаем файл шрифта, который поддерживает кириллицу
            font = new Font(bf, FONT_SIZE);

        } catch (Exception ex) {
            font = new Font(Font.FontFamily.ZAPFDINGBATS);
            ex.printStackTrace();
        }
    }

    @Override
    public void start(ClientReportHeadData headData) {
        try {
            document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Chunk(""));
            document.addTitle(headData.header);
            document.addHeader("Header",headData.header);
            document.add(table);
        } catch (Exception e) {
           document.close();
            e.printStackTrace();
        }
    }

    @Override
    public void addRow(ClientReportRow row) {
        PdfPCell[] cells = new PdfPCell[table.getNumberOfColumns()];
        cells[0] = new PdfPCell(new Phrase(row.surname + " " + row.name + " " + row.patronymic, font));
        cells[1] = new PdfPCell(new Phrase(String.valueOf(row.age)));
        cells[2] = new PdfPCell(new Phrase(row.charmName, font));
        cells[3] = new PdfPCell(new Phrase(String.valueOf(row.maxbal)));
        cells[4] = new PdfPCell(new Phrase(String.valueOf(row.minbal)));
        cells[5] = new PdfPCell(new Phrase(String.valueOf(row.sumbal)));

        PdfPRow pRow = new PdfPRow(cells);
        table.getRows().add(pRow);
    }

    @Override
    public void finish(ClientReportFootData footData) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("");

            document.add(table);
            document.addAuthor(footData.username);
            document.addCreationDate();
            document.add(new Paragraph("Сгенерировано пользователем : " + footData.username,font));
            document.add(new Paragraph("Дата и время : " + footData.generateTime,font));
            outputStream.flush();
            document.close();
        } catch (Exception e) {
            document.close();
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            String tmpDir = System.getProperty("user.home") + "/trans/tmp";
            FileOutputStream fOut = new FileOutputStream(tmpDir + "/generated.pdf");
            ClientReportViewPDF clientReportViewPDF = new ClientReportViewPDF(fOut);
            clientReportViewPDF.start(new ClientReportHeadData("HELLO"));
            clientReportViewPDF.finish(new ClientReportFootData("Finish", new Date()));
            fOut.flush();
            fOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}

