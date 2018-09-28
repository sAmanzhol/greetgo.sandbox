import {Injectable} from '@angular/core';
import {CharacterRecord} from "../../model/CharacterRecord";
import {HttpService} from "../http.service";

@Injectable({
  providedIn: 'root'
})
export class CharactersService {

  constructor(private http: HttpService) {
  }

  getCharacters(): Promise<CharacterRecord[]> {
    return this.http.get("/character/list")
      .toPromise()
      .then(resp => resp.body as CharacterRecord[])
  }
}
