import {Injectable} from "@angular/core";
import {HttpService} from "../http.service";
import {PersonRecord} from "../../model/PersonRecord";
import {ClientRecord} from "../../model/ClientRecord";

@Injectable({
  providedIn:'root'
})

export class ClientTableService {

  constructor(private http:HttpService){}

  public loading : boolean = false;

  public list: ClientRecord[] = [];

  loadRecords(param : any| any): Promise<ClientRecord[]>{
    return this.http.get("/client/list/")
      .toPromise()
      .then(resp => resp.body as Array<any>)
      .then(body => body.map(r => ClientRecord.create(r)))
  }

   async load(param : any){
      try{
        this.loading = true;

        if(param != null)
          this.list = await this.loadRecords(param);
        else
          this.list = await this.loadRecords(null);

        this.loading = false;
      }
      catch (e) {
        this.loading = false;
        console.error(e)
      }
   }
}
