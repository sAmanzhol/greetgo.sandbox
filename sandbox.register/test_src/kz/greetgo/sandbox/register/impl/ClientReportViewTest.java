package kz.greetgo.sandbox.register.impl;

import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.report.ClientReportView;
import kz.greetgo.sandbox.controller.report.model.ClientReportFootData;
import kz.greetgo.sandbox.controller.report.model.ClientReportHeadData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khamit Mateyev on 10/8/18.
 */
public class ClientReportViewTest implements ClientReportView {
  List<ClientRecord> records = new ArrayList<>();

  @Override
  public void start(ClientReportHeadData headData) throws Exception {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addRow(ClientRecord row) throws Exception {
    records.add(row);
  }

  @Override
  public void finish(ClientReportFootData footData) throws Exception {
    throw new UnsupportedOperationException();
  }
}
