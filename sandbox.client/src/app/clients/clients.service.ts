import {Injectable} from '@angular/core';
import {ClientRecord} from "../../model/ClientRecord";
import {HttpService} from "../http.service";
import {ClientDisplay} from "../../model/ClientDisplay";

@Injectable({
  providedIn: 'root'
})
export class ClientsService {

  public list: ClientRecord[] = [];
  public deletedClient: ClientDisplay = new ClientDisplay();

  public loading: boolean = false;

  constructor(private http: HttpService) {
  }

  loadRecords(target = "default", type = "asc", query = ""): Promise<ClientRecord[]> {
    return this.http.get("/client/list", {target: target, type: type, query: query})
      .toPromise()
      .then(resp => resp.body as Array<any>)
      .then(body => body.map(r => ClientRecord.create(r)));
  }

  deleteClient(id): Promise<ClientDisplay> {
    return this.http.delete("/client/", {id: id})
      .toPromise()
      .then(resp => resp.body as ClientDisplay);
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

  async delete(id) {
    try {
      this.loading = true;
      this.deletedClient = await this.deleteClient(id);
      console.log(this.deletedClient);
      this.loading = false;

      this.load("default", "asc", "");

    } catch (e) {

      this.loading = false;
      console.error(e);
    }
  }
}
