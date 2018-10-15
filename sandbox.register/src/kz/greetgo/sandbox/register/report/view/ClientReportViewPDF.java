package kz.greetgo.sandbox.register.report.view;

import com.itextpdf.text.pdf.PdfPTable;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportFootData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportHeadData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportRow;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.IOException;
import java.io.OutputStream;

public class ClientReportViewPDF implements ReportView {

    OutputStream outputStream;

    public ClientReportViewPDF(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    Document document = null;

    @Override
    public void start(ClientReportHeadData headData) {
        try {  // Using a custom page size
            Rectangle pagesize = new Rectangle(216f, 720f);
            document = new Document(pagesize, 36f, 72f, 108f, 180f);
            // step 2
            PdfWriter.getInstance(document, outputStream);
            // step 3
            document.open();
            document.addHeader("Заголовок", headData.header);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addRow(ClientReportRow row) {
        // document.add;
    }

    @Override
    public void finish(ClientReportFootData footData) {
        document.addAuthor(footData.username);
        document.addCreationDate();
        document.close();
    }

}

