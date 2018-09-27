import {Injectable} from '@angular/core';
import {HttpService} from "../http.service";
import {PhoneTypeRecord} from "../../model/PhoneTypeRecord";

@Injectable({
  providedIn: 'root'
})
export class PhonesService {

  constructor(private http: HttpService) {
  }

  getPhoneTypes(): Promise<PhoneTypeRecord[]> {
    return this.http.get("/phone/list")
      .toPromise()
      .then(resp => resp.body as PhoneTypeRecord[])
  }
}
