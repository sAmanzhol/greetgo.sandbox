package kz.greetgo.sandbox.register.report.view;

import kz.greetgo.sandbox.controller.model.report_models.ClientReportFootData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportHeadData;
import kz.greetgo.sandbox.controller.model.report_models.ClientReportRow;

public interface ReportView  {

    void start(ClientReportHeadData headData);

    void addRow(ClientReportRow row) throws Exception;

    void finish(ClientReportFootData footData) throws Exception;

}
