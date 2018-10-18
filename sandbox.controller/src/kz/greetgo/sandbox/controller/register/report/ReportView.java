package kz.greetgo.sandbox.controller.register.report;

import kz.greetgo.sandbox.controller.model.report_models.ClientReportFootData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportHeadData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportRow;

public interface ReportView  {
    final static String TYPE_XLSX = "xlsx";
    final static String TYPE_PDF = "pdf";

    void start(ClientReportHeadData headData);

    void addRow(ClientReportRow row) throws Exception;

    void finish(ClientReportFootData footData) throws Exception;

}
