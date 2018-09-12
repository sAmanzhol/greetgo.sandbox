import {Injectable} from "@angular/core";
import {HttpService} from "../http.service";
import {ClientDetail} from "../../model/ClientDetail";
import {ClientToSave} from "../../model/ClientToSave";
import {toPromise} from "rxjs-compat/operator/toPromise";
import {ClientRecord} from "../../model/ClientRecord";

@Injectable({
  providedIn: 'root'
})
export class ClientDetailService {

  constructor(private http: HttpService) {
  }

  public loading: boolean = false;
  public clientDetail: ClientDetail = new ClientDetail();

  loadClientDetailRecord(id: number): Promise<ClientDetail> {
    //debugger;
    return this.http.get("/client/detail", {'id': id}) // {params: HttpParams})
      .toPromise()
      .then(resp => resp.body as any)
      .then(body => ClientDetail.create(body));

  }
  async load(id: number) {
    try {
      this.loading = true;
      this.clientDetail = await this.loadClientDetailRecord(id);
      this.loading = false;
    } catch (e) {
      this.loading = false;
      console.error(e);
    }
  }
    saveClient(ToSave: ClientToSave){
    //debugger;
      let s = JSON.stringify(ToSave);
      //debugger;
     return this.http.post("/client/save", {'toSave': s}) // {params: HttpParams})
      .toPromise()
      .then(resp => resp.body as any)
      .then(body => ClientRecord.create(body));
  }


}
