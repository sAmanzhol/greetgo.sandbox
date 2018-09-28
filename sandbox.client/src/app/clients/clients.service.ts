import {Injectable} from '@angular/core';
import {ClientRecord} from "../../model/ClientRecord";
import {HttpService} from "../http.service";

@Injectable({
  providedIn: 'root'
})
export class ClientsService {

  constructor(private http: HttpService) {
  }

  getCount(filter): Promise<number> {
    return this.http.get("/client/count", {filter: JSON.stringify(filter)})
      .toPromise()
      .then(resp => resp.body as number)
  }

  getClients(filter): Promise<ClientRecord[]> {
    return this.http.get("/client/list", {filter: JSON.stringify(filter)})
      .toPromise()
      .then(resp => resp.body as Array<any>)
      .then(body => body.map(r => ClientRecord.create(r)));
  }

  deleteClient(id): Promise<void> {
    return this.http.delete("/client/", {id: id})
      .toPromise()
      .then(resp => resp.body);
  }
}
