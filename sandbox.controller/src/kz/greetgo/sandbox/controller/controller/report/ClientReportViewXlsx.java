package kz.greetgo.sandbox.controller.controller.report;

import kz.greetgo.sandbox.controller.controller.report.model.ClientReportFootData;
import kz.greetgo.sandbox.controller.controller.report.model.ClientReportHeadData;
import kz.greetgo.sandbox.controller.model.ClientRecord;

import java.io.PrintStream;

public class ClientReportViewXlsx implements ClientReportView {

  public ClientReportViewXlsx(PrintStream printStream) {
  }

  @Override
  public void start(ClientReportHeadData headData) {

  }

  @Override
  public void addRow(ClientRecord row) {

  }

  @Override
  public void finish(ClientReportFootData footData) {

  }
}
