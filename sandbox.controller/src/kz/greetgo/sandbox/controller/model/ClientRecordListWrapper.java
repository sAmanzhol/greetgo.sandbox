package kz.greetgo.sandbox.controller.model;

import java.util.List;

// TODO: 02.10.18 для второго пэйджа count не нужен

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
