package kz.greetgo.sandbox.register.report.view;

import kz.greetgo.sandbox.controller.model.report_models.ClientReportFootData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportHeadData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportRow;

//TODO
public class ClientReportViewPDF implements ReportView {
    @Override
    public void start(ClientReportHeadData headData) {

    }

    @Override
    public void addRow(ClientReportRow row) {
        System.out.println(row);
    }

    @Override
    public void finish(ClientReportFootData footData) {

    }
}
