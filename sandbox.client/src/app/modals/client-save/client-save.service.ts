import {Injectable} from '@angular/core';
import {HttpService} from "../../http.service";
import {ClientDetails} from "../../../model/ClientDetails";

@Injectable({
  providedIn: 'root'
})
export class ClientSaveService {

  constructor(private http: HttpService) {
  }

  saveClient(clientToSave): Promise<ClientDetails> {
    return this.http.post("/client/save", {clientToSave: JSON.stringify(clientToSave)})
      .toPromise()
      .then(resp => resp.body as ClientDetails)
  }

  getClient(id): Promise<ClientDetails> {
    return this.http.get("/client/details", {id: id})
      .toPromise()
      .then(resp => ClientDetails.of(resp.body))
  }
}
