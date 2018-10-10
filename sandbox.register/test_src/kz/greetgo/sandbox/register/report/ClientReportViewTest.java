package kz.greetgo.sandbox.register.report;

import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.report.ClientReportView;
import kz.greetgo.sandbox.controller.report.model.ClientReportFootData;
import kz.greetgo.sandbox.controller.report.model.ClientReportHeadData;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ClientReportViewTest implements ClientReportView {

  public ClientReportHeadData headData = null;
  public ClientReportFootData footData = null;

  public final List<ClientRecord> clientRecordList = new ArrayList<>();

  public ClientReportViewTest() {

  }

  @Override
  public void start(ClientReportHeadData headData) {
    this.headData = headData;
  }

  @Override
  public void addRow(ClientRecord row) {
    this.clientRecordList.add(row);
  }

  @Override
  public void finish(ClientReportFootData footData) {
    this.footData = footData;
  }
}
