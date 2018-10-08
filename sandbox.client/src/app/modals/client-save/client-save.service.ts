import {Injectable} from '@angular/core';
import {HttpService} from "../../http.service";
import {ClientDisplay} from "../../../model/ClientDisplay";

@Injectable({
  providedIn: 'root'
})
export class ClientSaveService {

  //fixme на стороне сервера ты разделил на разные контролеры и регистры, а тут почему-то нет
  constructor(private http: HttpService) {
  }

  saveClient(clientToSave): Promise<ClientDisplay> {
    return this.http.post("/client/save", {clientToSave: JSON.stringify(clientToSave)})
      .toPromise()
      .then(resp => resp.body as ClientDisplay)
  }

  getClient(id): Promise<ClientDisplay> {
    return this.http.get("/client/details", {id: id})
      .toPromise()
      .then(resp => ClientDisplay.of(resp.body))
  }
}
