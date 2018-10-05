import {Injectable,IterableDiffers, DoCheck } from "@angular/core";
import {HttpService} from "../http.service";
import {PersonRecord} from "../../model/PersonRecord";
import {ClientRecord} from "../../model/ClientRecord";
import {ClientItem} from "../client-table/client-table.component";
import {Phone} from "../../model/Phone";
import {GenderType} from "../../model/GenderType";
import {Charm} from "../../model/Charm";
import {Address} from "../../model/Address";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {getRandomString} from "selenium-webdriver/safari";
import {Account} from "../../model/Account";

@Injectable({
  providedIn: 'root'
})
export class ClientRepositoryService  {

  clientRecList  : ClientRecord[] = [];

  charmList : Charm[] = [];
  loading: boolean = false;

  constructor(private http: HttpService) {
    this.setTestCharms();
    this.setTestData();
  }

  public transformClientRecToClientItem(clientRecList:ClientRecord[]) : ClientItem[]
  {

    let result : ClientItem[] = [];

    for(let rec of clientRecList)
    {
      result.push({
        id:rec.id,
        fio:rec.surname + " " + rec.name + " " + rec.patronomic,
        age:rec.getAge(),
        charm:rec.charm.name,
        totalAccBal:rec.getTotalAccBal(),
        maxAccBal:rec.getMaxAccBal(),
        minAccBal:rec.getMinAccBal()
      })
    }
    return result;
  }

  loadRecords(param: any | any): Promise<ClientRecord[]> {
    return this.http.get("/client/getAll")
      .toPromise()
      .then(resp => resp.body as Array<any>)
      //.then(body => body.map(r => ClientRecord.create(r)))
  }

  setTestCharms(){
    this.charmList = [
      Charm.create({id:1,name:"Строгий",description:"Бла бла бла",energy:100}),
      Charm.create({id:2,name:"Няшка",description:"Бла бла бла",energy:50}),
      Charm.create({id:3,name:"Говняшка",description:"Бла бла бла",energy:10})
    ];
  }

   getRandomInt(max) {
    return Math.floor(Math.random() * Math.floor(max));
  }

  getTestAccount() : Account[]{
   let accounts : Account[] = [];
   let account = new Account();

   console.log(accounts);
    for(let i = 0; i < 3;i++) {
      let money = this.getRandomInt(100000)/this.getRandomInt(9);
      let account = Account.create({id:0,client_id:0,money : money,number:86484646846874,
        registered_at : "KZ ALMATY"});
      accounts.push(account);
    }

    return accounts;
  }

  setTestData(){
    this.clientRecList = [ClientRecord.create({
      id: 1, surname: "Elgondy", name: "Ersultanbek",
      patronomic: "Elgondy", birthDate: "04.12.1997",
      gender: GenderType.FEMALE, charm: this.charmList[0], addressP: new Address(),
      addressF:  new Address(), phoneH:  new Phone(), phoneW:  new Phone(), phoneM:  new Phone(),
      account: this.getTestAccount()
    }),
      ClientRecord.create({
        id: 2, surname: "Kanybek", name: "Tauke",
        patronomic: "Bla bla", birthDate: "04.12.1997",
        gender: GenderType.FEMALE, charm: this.charmList[0], addressP: new Address(),
        addressF:  new Address(), phoneH:  new Phone(), phoneW:  new Phone(), phoneM:  new Phone(),
        account: this.getTestAccount()
      }),ClientRecord.create({
        id: 3, surname: "Иванов", name: "Игорь",
        patronomic: "Сергеевичь", birthDate: "04.12.1997",
        gender: GenderType.FEMALE, charm: this.charmList[0], addressP: new Address(),
        addressF:  new Address(), phoneH:  new Phone(), phoneW:  new Phone(), phoneM:  new Phone(),
        account: this.getTestAccount()
      }),ClientRecord.create({
        id: 4, surname: "Красавчиков", name: "Красавчик",
        patronomic: "Красавчиковичь", birthDate: "04.12.1997",
        gender: GenderType.FEMALE, charm: this.charmList[0], addressP: new Address(),
        addressF:  new Address(), phoneH:  new Phone(), phoneW:  new Phone(), phoneM:  new Phone(),
        account: this.getTestAccount()
      }),ClientRecord.create({
        id: 5, surname: "Сахаров", name: "Пирожок",
        patronomic: "Сладовичь", birthDate: "04.12.1997",
        gender: GenderType.FEMALE, charm: this.charmList[0], addressP: new Address(),
        addressF:  new Address(), phoneH:  new Phone(), phoneW:  new Phone(), phoneM:  new Phone(),
        account: this.getTestAccount()
      }),ClientRecord.create({
        id: 6, surname: "Big", name: "JUICE",
        patronomic: "ICE", birthDate: "04.12.1997",
        gender: GenderType.FEMALE, charm: this.charmList[0], addressP: new Address(),
        addressF:  new Address(), phoneH:  new Phone(), phoneW:  new Phone(), phoneM:  new Phone(),
        account: this.getTestAccount()
      }),ClientRecord.create({
        id: 7, surname: "Elgondy1", name: "Ersultanbek1",
        patronomic: "Elgondy1", birthDate: "04.12.1997",
        gender: GenderType.FEMALE, charm: this.charmList[0], addressP: new Address(),
        addressF:  new Address(), phoneH:  new Phone(), phoneW:  new Phone(), phoneM:  new Phone(),
        account: this.getTestAccount()
      })
    ];

  }

  getClientItem(): ClientItem[]{
    return this.transformClientRecToClientItem(this.clientRecList);
  }

  getById(id:number){
    return this.clientRecList.find(x => x.id === id);
  }

  async getAll(param: any){
    try {
      this.loading = true;

      if (param != null)
        this.clientRecList = await this.loadRecords(param);
      else
        this.clientRecList = await this.loadRecords(null);

      this.loading = false;
    }
    catch (e) {
      this.loading = false;
      console.error(e)
    }
  }

  async create(newClient : ClientRecord){
    this.clientRecList.push(newClient);
    /*
    * Отправляешь запрос на контролер
    * */

    console.log(this.loadRecords("someting"));
  }

  async update(client : ClientRecord){
    this.clientRecList.find(x=>x.id === client.id).assign(client);
    /*
    * Отправляешь запрос на контролер
    * */
  }

  async delete(client : ClientRecord){
    let indx = this.clientRecList.indexOf(client);
    this.clientRecList.splice(indx,1);
    /*
    * Отправляешь запрос на контролер
    * */
  }
}
