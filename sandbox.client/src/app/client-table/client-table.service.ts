import { Injectable } from '@angular/core';
import {HttpService} from "../http.service";
import {ClientRecord} from "../../model/ClientRecord";

@Injectable({
  providedIn: 'root'
})
export class ClientTableService {

  constructor(private http: HttpService) {}


  public loading: boolean = false;

  public list: ClientRecord[] = [];

  loadRecords(): Promise<ClientRecord[]> {
    return this.http.get("/client/list")
      .toPromise()
      .then(resp =>
        resp.body as Array<any>)
      .then(body => body.map(r => ClientRecord.create(r)));
  }

  async load() {
    try {

      this.loading = true;
      this.list = await this.loadRecords();
      this.loading = false;

    } catch (e) {

      this.loading = false;
      console.error(e);

    }
  }
  // async editClient() {
  //   try {
  //
  //     this.loading = true;
  //     this.list = await this.loadRecords();
  //     this.loading = false;
  //
  //   } catch (e) {
  //
  //     this.loading = false;
  //     console.error(e);
  //
  //   }
  // }
}
