import {Injectable} from '@angular/core';
import {HttpService} from "../../http.service";
import {ClientDisplay} from "../../../model/ClientDisplay";

@Injectable({
  providedIn: 'root'
})
export class ClientInfoService {

  //fixme на стороне сервера ты разделил на разные контролеры и регистры, а тут почему-то нет
  constructor(private http: HttpService) {
  }

  crupdateClient(clientToSave): Promise<ClientDisplay> {
    return this.http.post("/client/", {clientToSave: JSON.stringify(clientToSave)})
      .toPromise()
      .then(resp => resp.body as ClientDisplay)
  }

  getClient(id): Promise<ClientDisplay> {
    return this.http.get("/client/", {id: id})
      .toPromise()
      .then(resp => ClientDisplay.of(resp.body))
  }
}
