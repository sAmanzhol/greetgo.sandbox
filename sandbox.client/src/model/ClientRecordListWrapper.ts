import {ClientRecord} from "./ClientRecord";

export class ClientRecordListWrapper {
  public records: ClientRecord[];
  public count: number/*int*/;

  public static create(a: any): ClientRecordListWrapper {
    const ret = new ClientRecordListWrapper();
    ret.assign(a);
    return ret;
  }

  assign(a: any) {
    this.records = a.records;
    this.count = a.count;
  }
}
