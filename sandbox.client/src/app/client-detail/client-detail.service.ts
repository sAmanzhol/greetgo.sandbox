import {Injectable} from "@angular/core";
import {HttpService} from "../http.service";
import {ClientDetail} from "../../model/ClientDetail";

@Injectable({
  providedIn: 'root'
})
export class ClientDetailService {

  constructor(private http: HttpService) {
  }

  public loading: boolean = false;
  public clientDetail: ClientDetail= new ClientDetail();

  loadClientRecord(id: number): Promise<ClientDetail> {
    return this.http.get("/client/detail", {'id': id}) // {params: HttpParams})
      .toPromise()
      .then(resp => resp.body as any)
      .then(body => ClientDetail.create(body));
  }

  async load(id: number) {
    try {
      this.loading = true;
      this.clientDetail = await this.loadClientRecord(id);
      this.loading = false;
    } catch (e) {
      this.loading = false;
      console.error(e);
    }
  }
}
