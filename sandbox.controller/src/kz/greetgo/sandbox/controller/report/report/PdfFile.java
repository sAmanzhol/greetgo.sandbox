package kz.greetgo.sandbox.controller.report.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class PdfFile {

	public static void main(String[] args) {

		Font font = new Font(Font.FontFamily.COURIER,13, Font.BOLD,new BaseColor(0,0,0));
		Document document = new Document();
		document.setPageSize(PageSize.A4);
		try {
			PdfWriter.getInstance(document, new FileOutputStream("build/report/result.pdf"));
			document.open();
			Paragraph paragraph = new Paragraph("",font);
			paragraph.add("asdasdsa\n" );
			paragraph.add("asdasdsa\n" );
			paragraph.add("asdasdsa\n" );
			document.add(paragraph);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
