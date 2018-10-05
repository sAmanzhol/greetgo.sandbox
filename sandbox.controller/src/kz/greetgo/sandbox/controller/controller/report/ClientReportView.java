package kz.greetgo.sandbox.controller.controller.report;

import kz.greetgo.sandbox.controller.controller.report.model.ClientReportFootData;
import kz.greetgo.sandbox.controller.controller.report.model.ClientReportHeadData;
import kz.greetgo.sandbox.controller.model.ClientRecord;

public interface ClientReportView {
  void start(ClientReportHeadData headData) throws Exception;

  void addRow(ClientRecord row) throws Exception;

  void finish(ClientReportFootData footData) throws Exception;
}
