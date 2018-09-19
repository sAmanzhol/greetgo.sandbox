import { Injectable } from '@angular/core';
import {ClientRecord} from "../../model/ClientRecord";
import {HttpService} from "../http.service";
import {PersonRecord} from "../../model/PersonRecord";

@Injectable({
  providedIn: 'root'
})
export class ClientsService {

  public list: ClientRecord[] = [];

  public loading: boolean = false;

  constructor(private http: HttpService) { }

  loadRecords(target="default", type="asc", query=""): Promise<ClientRecord[]> {
    return this.http.get("/client/list", {target: target, type: type, query: query})
      .toPromise()
      .then(resp => resp.body as Array<any>)
      .then(body => body.map(r => ClientRecord.create(r)));
  }

  async load(target, type, query) {
    try {

      this.loading = true;
      this.list = await this.loadRecords(target, type, query);
      this.loading = false;

    } catch (e) {

      this.loading = false;
      console.error(e);
    }
  }
}
