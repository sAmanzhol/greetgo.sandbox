import {Injectable} from '@angular/core';
import {HttpService} from "../http.service";
import {ClientRecord} from "../../model/ClientRecord";
import {ClientFilter} from "../../model/ClientFilter";
import {ClientRecordListWrapper} from "../../model/ClientRecordListWrapper";

@Injectable({
  providedIn: 'root'
})
export class ClientTableService {

  constructor(private http: HttpService) {
  }

  public loading: boolean = false;

  public list: ClientRecord[] = [];
  public clientWrapper: ClientRecordListWrapper;

  async deleteClient(rec: ClientRecord) {
    await this.http.delete("/client/delete", {'id': rec.clientId}).toPromise();
  }

  async filter(clientFilter: ClientFilter){
    try {
      this.loading = true;
      this.clientWrapper = await this.loadFilteredRecords(clientFilter);//loadRecords();
      this.list = this.clientWrapper.records;
      this.loading = false;
    } catch (e) {
      this.loading = false;
      console.error(e);

    }
  }

  loadFilteredRecords(clientFilter: ClientFilter): Promise<ClientRecordListWrapper> {
    return this.http.get("/client/list",
      {'clientFilter': JSON.stringify(clientFilter)})
      .toPromise()
      .then(resp => resp.body as any)
      .then(body => ClientRecordListWrapper.create(body));
  }

}
