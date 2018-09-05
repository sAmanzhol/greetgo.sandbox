import {Component, OnInit,} from "@angular/core";
import {HttpService} from "../HttpService";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";
import {UsersService} from "../users.service";
import {ClientAsd} from "../../model/ClientAsd";
import {ClientDetails} from "../../model/ClientDetails";
import {Client} from "../../model/Client";
// import {HeadMarkTable} from "../../model/HeadMarkTable";


@Component({
  selector: 'app-debug',
  template: require('./debug.component.html'),
  styles: [require('./debug.component.css')],
  providers: [UsersService],
})

export class DebugComponent implements OnInit {
  tmpClient: Client;

  formClientParameters: Client = new Client();

  cloneFormClientParameters: Client = new Client();

  headMarkTable: {
    firsname:"firstname",
    character:"character"
  }


  searchFilter = {
    firstname: '',
    lastname: '',
    patronymic: '',
  };
  genders = {
    male: 'Мужчина',
    female: 'Женщина',
  };
  characters = [];


  clients = [];
  indexes = {index: null};
  chooseClient: boolean = false;
  pagins = [];
  editButtonOrAddButton = false;


  constructor(private userService: UsersService, private http: HttpService) {
  }

  ngOnInit() {
    this.getClient();
    this.pagination();
    this.getCharacter();

  }


  // CRUD
  getClient() {
    let self = this;
    this.http.get("/client/userInfo").map((response) => response.json())
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

  getCharacter() {

    this.http.get('/client/getCharacter').subscribe(res => {


      this.characters = res.json();

      console.log(this.characters);

    })
  };


  addClient() {


    let forms: Client = new Client();

    this.goRec(this.formClientParameters, forms);
    console.log(forms);


    this.http.get('/client/addUserInfo', {client: JSON.stringify((forms))}).subscribe(res => {
      let ret: Client = res.json();
      console.log(ret);
      console.log("I!!!!!")

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
  getHeadMarkClient(headMarkTable) {
    let sort = {sort: headMarkTable};
    let self = this;
    this.http.get("/client/userSort", sort).map((response) => response.json())
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
    });
    this.indexes.index = 0;
  }

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


  //ALL Function for PAGINATION
  pagination() {

    let pagin: number | string;
    this.http.get("/client/pagination").toPromise().then(res => {
      pagin = res.text();
      console.log('lololol');
      pagin = Number(pagin);
      console.log(pagin);
      for (let i = 0; i < pagin; i++) {
        this.pagins[i] = i;
      }
    })

  }

  changePagination(index) {

    this.pagination();

    console.log(index);
    let self = this;

    this.indexes.index = index;

    this.http.get("/client/getPagination", this.indexes).map((response) => response.json())
      .map(users => {
        return users.map(u => {
          return {
            id: u.id,
            firstname: u.firstname,
            lastname: u.lastname,
            patronymic: u.patronymic,
            character: u.character,
            dateOfBirth: u.dateOfBirth,
            totalAccountBalance: u.totalAccountBalance,
            maximumBalance: u.maximumBalance,
            minimumBalance: u.minimumBalance,

          }
        })
      }).subscribe((data) => {
      self.clients = data;
      console.log(self.clients)
    })


  }

  rigthChangePagination() {
    if (this.indexes.index == null) {
      this.indexes.index = 0
    }


    if (this.indexes.index == this.pagins.length - 1) {
      this.indexes.index = this.pagins.length - 1;
    }
    else {
      ++this.indexes.index;
    }
    this.changePagination(this.indexes.index)
  }

  leftChangePagination() {
    if (this.indexes.index == null) {
      this.indexes.index = 0
    }

    if (this.indexes.index == 0) {
      this.indexes.index = 0;
    }
    else {
      --this.indexes.index
    }

    this.changePagination(this.indexes.index)
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


  testDebug() {
    let test: ClientAsd = new ClientAsd();
    test.name = "asdasd";
    test.surname = "tttt";

    this.http.post("/client/test-debug", {test: JSON.stringify(test)}).subscribe(res => {
      let ret: ClientDetails = res.json();

      console.log("ret.aaa: " + ret.aaa + ", ret.sss: " + ret.sss);
    });
  }

}

