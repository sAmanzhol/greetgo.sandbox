import {Injectable} from '@angular/core';
import {HttpService} from "../../http.service";
import {ClientDisplay} from "../../../model/ClientDisplay";
import {CharacterRecord} from "../../../model/CharacterRecord";
import {PhoneRecord} from "../../../model/PhoneRecord";

@Injectable({
  providedIn: 'root'
})
export class ClientInfoService {

  //fixme на стороне сервера ты разделил на разные контролеры и регистры, а тут почему-то нет
  constructor(private http: HttpService) {
  }

  crupdateClient(clientToSave): Promise<ClientDisplay> {
    return this.http.post("/client/" + clientToSave.id, {clientToSave: JSON.stringify(clientToSave)})
      .toPromise()
      .then(resp => resp.body as ClientDisplay)
  }

  getClient(id): Promise<ClientDisplay> {
    return this.http.get("/client/", {id: id})
      .toPromise()
      .then(resp => resp.body as ClientDisplay)
  }

  getCharacters(): Promise<CharacterRecord[]> {
    return this.http.get("/character/list")
      .toPromise()
      .then(resp => resp.body as CharacterRecord[])
  }

  getPhoneTypes(): Promise<PhoneRecord[]> {
    return this.http.get("/phone/list")
      .toPromise()
      .then(resp => resp.body as PhoneRecord[])
  }
}
