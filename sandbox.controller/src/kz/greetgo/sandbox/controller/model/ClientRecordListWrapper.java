package kz.greetgo.sandbox.controller.model;

import java.util.List;

public class ClientRecordListWrapper {
  public List<ClientRecord> records;
  public int count;

  public ClientRecordListWrapper(List<ClientRecord> records, int count) {
    this.records = records;
    this.count = count;
  }

  public ClientRecordListWrapper() {
  }
}
