import {Injectable} from '@angular/core';
import {ClientRecord} from "../../model/ClientRecord";
import {HttpService} from "../http.service";
import {ClientDisplay} from "../../model/ClientDisplay";

@Injectable({
  providedIn: 'root'
})
export class ClientsService {

  public list: ClientRecord[] = [];
  public count: number = 0;

  constructor(private http: HttpService) {
  }

  getCount(filter): Promise<number> {
    return this.http.get("/client/count", {filter: JSON.stringify(filter)})
      .toPromise()
      .then(resp => resp.body as number)
  }

  loadRecords(filter): Promise<ClientRecord[]> {
    return this.http.get("/client/list", {filter: JSON.stringify(filter)})
      .toPromise()
      .then(resp => resp.body as Array<any>)
      .then(body => body.map(r => ClientRecord.create(r)));
  }

  deleteClient(id): Promise<ClientDisplay> {
    return this.http.delete("/client/", {id: id})
      .toPromise()
      .then(resp => resp.body as ClientDisplay);
  }

  async load(filter) {
    try {
      this.count = await this.getCount(filter);
      this.list = await this.loadRecords(filter);
    } catch (e) {
      console.error(e);
    }
  }

  async delete(id, filter) {
    try {
      await this.deleteClient(id);
      this.load(filter);
    } catch (e) {
      console.error(e);
    }
  }
}
