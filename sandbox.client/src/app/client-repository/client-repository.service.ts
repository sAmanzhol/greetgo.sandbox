import {Injectable, OnInit} from "@angular/core";
import {HttpService} from "../http.service";
import {ClientRecord} from "../../model/ClientRecord";
import {Charm} from "../../model/Charm";
import {ClientReqParams} from "../../model/ClientReqParams";
import {Observable} from "rxjs/Observable";
import {HttpResponse} from "@angular/common/http";
import {ClientDisplay} from "../../model/ClientDisplay";
import {PhoneType} from "../../model/PhoneType";
import {AddressType} from "../../model/AddressType";

@Injectable({
  providedIn: 'root'
})
export class ClientRepositoryService implements OnInit{
  REPORT_XLSX = "xlsx";
  REPORT_PDF  = "pdf";
  GET_BY_ID_URL = "/client/getById";
  GET_CHARMS_URL = "/client/getCharms";
  GET_COUNT_URL = "/client/getCount";
  UPDATE_CLIENT_URL = "/client/update";
  INSERT_CLIENT_URL = "/client/insert";
  DELETE_CLIENT_URL = "/client/delete";
  GET_ALL_CLIENT_URL = "/client/getAll";
  phoneTypes   =   [{name: "Домашний", value:PhoneType.HOME}, {name: "Мобильный", value: PhoneType.MOBILE}, {name: "Рабочий", value: PhoneType.WORK}];
  addressTypes =   [{name: "Фактический", value: AddressType.FACT }, {name: "Регистрации", value: AddressType.REG}];



  clientRecList  : ClientRecord[] = [];
  clientItemList : ClientDisplay[] = [];
  charmList : Charm[] = [];

  constructor(private http: HttpService) {}

  public transformClientRecToClientDisplay(clientRecList:ClientRecord[]) : ClientDisplay[]
  {

    let result : ClientDisplay[] = [];

    for(let rec of clientRecList)
    {
        result.push(ClientDisplay.createFromClientRec(rec));
    }
    return result;
  }

  ngOnInit(){

  }

  private transfClientRecToClientDisp(clientRec : ClientRecord) : ClientDisplay{
    if(clientRec.charm.id != null)
       clientRec.charm = this.getCharmById(clientRec.charm.id);

      let clientDisp = ClientDisplay.createFromClientRec(clientRec);

   return clientDisp;
  }

  loadClientRecords(param: ClientReqParams): Observable<HttpResponse<any>> {
    return this.http.get(this.GET_ALL_CLIENT_URL,
      {"params" : JSON.stringify(param)});
  }

  loadClientById(id:number){
    return this.http.get(this.GET_BY_ID_URL,{"id":id});
  }

  loadCharms(): Observable<HttpResponse<any>> {
    return this.http.get(this.GET_CHARMS_URL);
  }

  loadClientsCount(): Promise<number> {
    return this.http.get(this.GET_COUNT_URL)
      .toPromise()
      .then(resp => resp.body as number);
  }

  updateClient(client:ClientRecord){
    return this.http.post(this.UPDATE_CLIENT_URL,{'client':JSON.stringify(client)}).toPromise();
  }

  insertClient(client:ClientRecord){
    return this.http.post(this.INSERT_CLIENT_URL,{'client':JSON.stringify(client)}).toPromise();
  }
  deleteClient(id){
     this.http.post(this.DELETE_CLIENT_URL,{"id":id}).toPromise();
  }

  assingClientItem(from_item:ClientDisplay, to_item:ClientDisplay){
    from_item.fio = to_item.fio;
    from_item.minbal = to_item.minbal;
    from_item.maxbal = to_item.maxbal;
    from_item.age = to_item.age;
    from_item.sumbal = to_item.sumbal;
    from_item.charm_name = to_item.charm_name;
  }

  async getClientById(id:number){
    let cl = await this.loadClientById(id).toPromise().then(resp=>resp.body as any).then(body=>{return ClientRecord.create(body)});
    cl.charm = this.getCharmById(cl.charm.id);
    return cl;
  }

  getCharmById(id:number){
    return this.charmList.find(x=> x.id == id);
  }

  async getClientRecList(params:ClientReqParams){
    this.clientRecList = await this.loadClientRecords(params).toPromise()
      .then(resp => resp.body as Array<any>).then(body => body.map(r => ClientRecord.create(r)));

    return this.clientRecList;
  }

  async getDispClientList(params:ClientReqParams){
    await this.getClientRecList(params);
    this.clientItemList = this.transformClientRecToClientDisplay(this.clientRecList);
    return this.clientItemList;
  }

  async getCharmlist() {
     this.charmList = await this.loadCharms().toPromise().then(resp=>resp.body as Array<any>).then(body=>body.map(r => Charm.create(r)));
     return this.charmList;
  }

  async create(newClient : ClientRecord){
    let prevFormat = newClient.birthDate;
    newClient.birthDate =  Date.parse(newClient.birthDate);
    await this.insertClient(newClient);

    newClient.birthDate = prevFormat;
    this.clientRecList.splice(0,0,newClient);
    this.clientItemList.splice(0,0,this.transfClientRecToClientDisp(newClient));
  }

  async update(client : ClientRecord){
    let prevFormat = client.birthDate;
    client.birthDate =  Date.parse(client.birthDate);
    await this.updateClient(client);

    client.birthDate =  prevFormat;
    this.clientRecList.find(x=>x.id === client.id).copyAssign(client);
    let clientItem:ClientDisplay = this.clientItemList.find(x=>x.id === client.id);
    this.assingClientItem(clientItem,this.transfClientRecToClientDisp(client));
  }

  async delete(client : ClientRecord){

    let localClient = this.clientRecList.find(x=>x.id == client.id);
    let indx = this.clientRecList.indexOf(localClient);

    this.clientRecList.splice(indx,1);
    this.clientItemList.splice(indx,1);

    this.deleteClient(client.id);
  }

  async getReport(type : string,user : any) {
      return await this.http.downloadResource("/client/report/"+type,{"username":JSON.stringify(user)});
  }

}
