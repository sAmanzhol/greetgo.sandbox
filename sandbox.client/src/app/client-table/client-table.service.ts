import {Injectable} from '@angular/core';
import {HttpService} from "../http.service";
import {ClientRecord} from "../../model/ClientRecord";
import {ClientFilter} from "../../model/ClientFilter";
import {ClientToSave} from "../../model/ClientToSave";

@Injectable({
  providedIn: 'root'
})
export class ClientTableService {

  constructor(private http: HttpService) {
  }


  public loading: boolean = false;

  public list: ClientRecord[] = [];

  loadRecords(): Promise<ClientRecord[]> {
    return this.http.get("/client/list")
      .toPromise()
      .then(resp => resp.body as Array<any>)
      .then(body => body.map(r => ClientRecord.create(r)));
  }

  async load(clientFilter: ClientFilter) {
    try {
      this.loading = true;
      await this.filter(clientFilter)//loadRecords();
      // this.list = await this.filter(clientFilter)//loadRecords();
      this.loading = false;

      //return Promise.resolve(this.list);
    } catch (e) {

      this.loading = false;
      console.error(e);

    }

  }

  async deleteClient(rec: ClientRecord) {
    await this.http.delete("/client/delete", {'id': rec.clientId}).subscribe(res => console.log(res));
    //this.deleteClientFromList(rec);
  }

  // deleteClientFromList(rec: ClientRecord) {
  //   this.list.splice(this.list.indexOf(rec), 1);
  // }

  //addClientToList(rec: ClientRecord) {
  //debugger;
  // let isFound: boolean = false;
  // this.list.forEach(function (clientRecord) {
  //   //debugger;
  //   if (rec.clientId == clientRecord.clientId) {
  //     //debugger;
  //     clientRecord = clientRecord.update(rec);
  //     isFound = true;
  //     //break;
  //     //debugger;
  //   }
  // });
  // if (!isFound)
  //   this.list.push(rec);
// }

  //  async filter(clientFilter: ClientFilter) {
  //    try {
  //      this.loading = true;
  //      this.list = await this.loadFilteredRecords(clientFilter);
  //      this.loading = false;
  //
  //      //return Promise.resolve(this.list);
  //    } catch (e) {
  //
  //      this.loading = false;
  //      console.error(e);
  //
  //    }
  // }
  // loadFilteredRecords(clientFilter: ClientFilter): Promise<ClientRecord[]> {
  //   return this.http.get("/client/filter", {'clientFilter': JSON.stringify(clientFilter)})
  //     .toPromise()
  //     .then(resp => resp.body as Array<any>)
  //     .then(body => body.map(r => ClientRecord.create(r)));
  //  // return null;
  // }

  async filter(clientFilter: ClientFilter){


    try {
      this.loading = true;
      // await this.filter(clientFilter)//loadRecords();
      this.list = await this.loadFilteredRecords(clientFilter)//loadRecords();
      this.loading = false;

      //return Promise.resolve(this.list);
    } catch (e) {

      this.loading = false;
      console.error(e);

    }
  }

  loadFilteredRecords(clientFilter: ClientFilter): Promise<ClientRecord[]> {
     return this.http.get("/client/filter",
      {'clientFilter': JSON.stringify(clientFilter)})
      .toPromise()
      .then(resp => resp.body as Array<any>)
      .then(body => body.map(r => ClientRecord.create(r)));
  }

}
