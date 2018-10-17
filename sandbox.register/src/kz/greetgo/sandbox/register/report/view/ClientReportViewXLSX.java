package kz.greetgo.sandbox.register.report.view;

import kz.greetgo.msoffice.xlsx.gen.Sheet;
import kz.greetgo.msoffice.xlsx.gen.Xlsx;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportFootData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportHeadData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportRow;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class ClientReportViewXLSX implements ReportView  {

    OutputStream outputStream;
    Xlsx f;
    Sheet sheet;

    public ClientReportViewXLSX(OutputStream outputStream) {
        this.outputStream = outputStream;
        f = new Xlsx();
        sheet = f.newSheet(true);
    }

    @Override
    public void start(ClientReportHeadData headData) {
        sheet.row().start();
        sheet.cellStr(1,headData.header);
        sheet.row().finish();
        sheet.row().start();
        sheet.cellStr(1,"ID");
        sheet.cellStr(2,"ФИО");
        sheet.cellStr(3,"ХАРАКТЕР");
        sheet.cellStr(4,"ВОЗРАСТ");
        sheet.cellStr(5,"Общ.остаток");
        sheet.cellStr(6,"Мин.остаток");
        sheet.cellStr(7,"Мак.остаток");

        sheet.style().alignment().horizontalCenter();
    }

    @Override
    public void addRow(ClientReportRow row) throws Exception
    {
        sheet.row().start();
            sheet.cellStr(1,row.id.toString());
            sheet.cellStr(2,row.surname + " " + row.name + " " + row.patronymic);
            sheet.cellStr(3,row.charmName);
            sheet.cellStr(4,row.age.toString());
            sheet.cellDouble(5,row.sumbal);
            sheet.cellDouble(6,row.minbal);
            sheet.cellDouble(7,row.maxbal);
        sheet.row().finish();
    }

    @Override
    public void finish(ClientReportFootData footData) throws Exception{
        sheet.skipRows(3);
        sheet.row().start();
             sheet.cellStr(5,"Сгенерировано пользователем :");
             sheet.cellStr(6,footData.username);
        sheet.row().finish();
        sheet.row().start();
             sheet.cellStr(5,"Дата и время :");
             sheet.cellDMY(6,footData.generateTime);
        sheet.row().finish();
        f.complete(outputStream);
        outputStream.flush();
    }

    public static void main(String[] args) {
        try {
            String tmpDir = System.getProperty("user.home") + "/trans/tmp";
            FileOutputStream fOut = new FileOutputStream(tmpDir + "/generated.xlsx");
            ClientReportViewXLSX clientReportViewXLSX = new ClientReportViewXLSX(fOut);
            clientReportViewXLSX.start(new ClientReportHeadData("HELLO"));
            clientReportViewXLSX.finish(new ClientReportFootData("Finis",new Date()));
            fOut.flush();
            fOut.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
