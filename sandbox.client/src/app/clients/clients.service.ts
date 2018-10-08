import {Injectable} from '@angular/core';
import {ClientRecord} from "../../model/ClientRecord";
import {HttpService} from "../http.service";
import {HttpResponse} from "@angular/common/http";
import {LoginService} from "../login/login.service";

@Injectable({
  providedIn: 'root'
})
export class ClientsService {

  constructor(private http: HttpService, private loginService: LoginService) {
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
    return this.http.delete("/client/delete", {id: id})
      .toPromise()
      .then(resp => resp.body);
  }

  renderClientRecords(filter, type) {
    this.http.downloadResource("/client/render", {filter: JSON.stringify(filter), type: type, username: this.loginService.personDisplay.username})
  }
}
