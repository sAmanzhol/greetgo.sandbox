import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";
import {Component, OnInit,} from "@angular/core";
import {HttpService} from "../HttpService";
import {UsersService} from "../users.service";
import {ClientAsd} from "../../model/ClientAsd";
import {ClientDetails} from "../../model/ClientDetails";
import {Client} from "../../model/Client";
import {ClientFilter} from "../../model/ClientFilter";
import {ClientRecord} from "../../model/ClientRecord";
import {GenderType} from "../../model/GenderType";

@Component({
  selector: 'app-modal',
  template: require('./modal.component.html'),
  styles: [require('./modal.component.css')],
  providers: [UsersService],
})
// TODO: asset 9/4/18 Razdeli componenty list i edit client ili customer
export class ModalComponent implements OnInit {

  clientFilter:ClientFilter=new ClientFilter();
  clientRecord:Array<ClientRecord>=new Array<ClientRecord>();
  clientDetails:ClientDetails = new ClientDetails();
  tmpClient: Client;
  formClientParameters: Client = new Client();
  cloneFormClientParameters: Client = new Client();
  genders:GenderType[] = [GenderType.MALE,GenderType.FEMALE];



  getClientFilter(){
    var self = this;
    this.http.get('/client/client-filter-set')
      .subscribe(data => {
        console.log('TADAM')
         self.clientFilter.assign(data.json());

      })
  }

  getClientFilterFilter() {
    let self = this;
    var clientRec:ClientRecord = new ClientRecord();
    var clientRecs:Array<ClientRecord> = new Array<ClientRecord>();
    var dates=[];
    this.http.get('/client/client-filter',{clientFilter:JSON.stringify(self.clientFilter)})
      .subscribe(data => {
        dates = data.json();
          for (let i = 0; i < dates.length; i++) {
            clientRec = dates[i];
            clientRecs.push(clientRec);
          }
          self.clientRecord = clientRecs;
          console.log(self.clientRecord.length);

      })
    self.getClientFilter();


  }
  getClientFilterSort(orderBy:string,sort:boolean){
    let self = this;
    this.clientFilter.orderBy=orderBy;
    this.clientFilter.sort= !sort;
      var clientRec:ClientRecord = new ClientRecord();
      var clientRecs:Array<ClientRecord> = new Array<ClientRecord>();
      var dates=[];
    this.http.get('/client/client-filter',{clientFilter:JSON.stringify(self.clientFilter)})
      .subscribe(data => {
        dates = data.json();
        if(dates.length ==0){
          self.clientFilter.offSet = self.clientFilter.offSet-1;
        }
        else {
          for (let i = 0; i < dates.length; i++) {
            clientRec = dates[i];
            clientRecs.push(clientRec);
          }
          self.clientRecord = clientRecs;
          console.log(self.clientRecord.length);
        }
      })
  }
    getClientFilterPagination(offSet){
        let self = this;
        self.clientFilter.offSet=offSet;
        var clientRec:ClientRecord = new ClientRecord();
        var clientRecs:Array<ClientRecord> = new Array<ClientRecord>();
        let dates:ClientRecord[];
        console.log(self.clientFilter.offSet + " IS OFFSET that i'm sending to Server");
        this.http.get('/client/client-filter',{clientFilter:JSON.stringify(self.clientFilter)})
            .subscribe(data => {
                dates = data.json();
                if(dates.length ==0){
                 self.clientFilter.offSet = self.clientFilter.offSet-1;
                }
                else {
                    for (let i = 0; i < dates.length; i++) {
                        clientRec = dates[i];
                        clientRecs.push(clientRec);
                    }

                    self.clientRecord = clientRecs;

                }
            })
    }

    // CRUD
    getClient() {
        // TODO: asset 9/4/18 U nas konvensya API ssylke userInfo -> user-info i pereimenu userInfo na client-list ili customer-list chto by bylo ponyatno
        // TODO: asset 9/4/18 Sozdai class tipa Client ili CustomerRecord dlya lista
        let self = this;
        this.http.get("/client/client-list").subscribe((data) => {
            var red = data.json();
            for(let i =0;i<red.length;i++)
                self.clientRecord.push(red[i]);
            console.log(self.clientRecord[0]);
            console.log("SASORI")
        })

    }



// TODO: asset 9/4/18 sozdai class ClientFilter ili tipa takoe. MODEL
  searchFilter = {
    firstname: '',
    lastname: '',
    patronymic: '',
  };
// TODO: asset 9/4/18 sozdai ENUM
  characters = [];


  clients = [];
  indexes = {index: null};
  chooseClient: boolean = false;
  pagins = [];
  editButtonOrAddButton = false;

// TODO: asset 9/4/18 Uberi ne izpolzuimy peremennye USERSERVICE
  constructor(private http: HttpService) {

  }

  ngOnInit() {
    this.getClient();

  }

  // CRUD

// TODO: asset 9/4/18 I dlya Character tozhe nuzhno sozdat class
  getCharacter() {

    this.http.get('/client/getCharacter').subscribe(res => {


      this.characters = res.json();

      console.log(this.characters);

    })
  };

  addClient() {

    var self = this;
  console.log(this.clientDetails);

    this.http.get('/client/client-details-save', {clientDetails: JSON.stringify((self.clientDetails))})
      .subscribe(res => {

        console.log(res.json().firstname + "ITSSS ADDD")
        this.clientRecord.push(res.json())
      })
  }


  editClient() {

    let forms: Client = new Client();

    this.goRec(this.formClientParameters, forms);
    console.log(forms);
    console.log("EDIT EDIT EDIT ");

    this.http.get('/client/editUserInfo', {edit: JSON.stringify((forms))}).subscribe(res => {
      let ret: Client = res.json();
      console.log(ret);
      console.log("OGOOGOGOGOG")

    })


  }

  goRec(obj, obj1) {
    for (let key in obj) {
      obj1[key] = obj[key];

      if (typeof obj1[key] == "object") {

        this.goRec(obj[key], obj1[key]);
      }
    }


  }

  deleteClient() {

    let self = this;

    this.http.delete('/client/deleteUserInfo', {id: self.formClientParameters.id}).map((response) => response.json())
      .map(users => {
        return users.map(u => {
          return {
            id: u.id,
            firstname: u.firstname,
            lastname: u.lastname,
            patronymic: u.patronymic,
            dateOfBirth: u.dateOfBirth,
            character: u.character,
            totalAccountBalance: u.totalAccountBalance,
            maximumBalance: u.maximumBalance,
            minimumBalance: u.minimumBalance,

          }
        })

      }).subscribe((data) => {
      self.clients = data;
      console.log(this.clients);
      console.log("ITS!!!!!")
    })
  }

  //FILTER
  getFilter() {

    this.http.get("/client/getFilter", this.searchFilter).map((response) => response.json())
      .map(users => {
        return users.map(u => {
          return {
            firstname: u.firstname,
            lastname: u.lastname,
            patronymic: u.patronymic,
            dateOfBirth: u.dateOfBirth,
            character: u.character,
            totalAccountBalance: u.totalAccountBalance,
            maximumBalance: u.maximumBalance,
            minimumBalance: u.minimumBalance,
          }
        })
      }).subscribe((data) => {
        this.clients = data;
        console.log(this.clients)
      }
    )
  }


  //function CLICK for TABLE


  // TODO: asset 9/4/18 imena methodo dolzhno byt ponyatnym tipa onSelect() i ewe tmpClient
  getMarkClient(client) {
    this.tmpClient = client;
    this.chooseClient = true;
    console.log(client);
    this.formClientParameters.id = client.id;
    this.formClientParameters.firstname = client.firstname;
    this.formClientParameters.lastname = client.lastname;
    this.formClientParameters.patronymic = client.patronymic;
    this.formClientParameters.character = client.character;
    this.formClientParameters.dateOfBirth = client.dateOfBirth;
    this.formClientParameters.gender = client.gender;
    this.cloneFormClientParameters.id = client.id;
    this.cloneFormClientParameters.firstname = client.firstname;
    this.cloneFormClientParameters.lastname = client.lastname;
    this.cloneFormClientParameters.patronymic = client.patronymic;
    this.cloneFormClientParameters.character = client.character;
    this.cloneFormClientParameters.dateOfBirth = client.dateOfBirth;
  }




  //SHOW FUNCTIONS I DON'T WHAT IS THAT
  showAddForm() {

    this.chooseClient = false;
    this.editButtonOrAddButton = false;
    this.formClientParameters.id = 0;
    this.formClientParameters.firstname = '';
    this.formClientParameters.lastname = '';
    this.formClientParameters.patronymic = '';
    this.formClientParameters.character = '';
    this.formClientParameters.dateOfBirth = '';
    this.formClientParameters.gender = '';
    this.formClientParameters.addressOfResidence.street = '';
    this.formClientParameters.addressOfResidence.home = '';
    this.formClientParameters.addressOfResidence.apartment = '';
    this.formClientParameters.addressOfRegistration.street = '';
    this.formClientParameters.addressOfRegistration.home = '';
    this.formClientParameters.addressOfRegistration.apartment = '';
    this.formClientParameters.phone.home = 0;
    this.formClientParameters.phone.work = 0;
    this.formClientParameters.phone.mobile1 = 0;
    this.formClientParameters.phone.mobile2 = 0;
    this.formClientParameters.phone.mobile3 = 0;

  }


  showEditForm() {
    let self = this;
    let ret: Client;
    this.http.post("/client/getClientForEdit", {id: self.formClientParameters.id}).subscribe(res => {
      ret = res.json();
      self.formClientParameters = ret[0];
      console.log(ret[0].firstname);
      console.log(self.formClientParameters.addressOfRegistration);
    });
    this.editButtonOrAddButton = true;
  }


}

