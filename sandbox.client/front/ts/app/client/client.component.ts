import {Component, OnInit} from "@angular/core";
import {UsersService} from "../users.service";
import {HttpService} from "../HttpService";
import {PhoneType} from "../../model/PhoneType";
import {UserInfo} from "../../model/UserInfo";

@Component({
  selector:'app-client',
  template: require('./client.component.html'),
  styles: [require('./client.component.css')],
  providers:[UsersService],
})
export class ClientComponent implements OnInit{
  objectLine={
    firstname:'',
    lastname:'',
    patronymic:'',
  };
  objectForms={
    firstname:'',
    lastname:'',
    patronymic:'',
    get fullname(){
        return ` ${this.patronymic} ${this.firstname} ${this.lastname} `
    }
  }
  searchStr ='';
  clientForms=true;
  datas =[]
  constructor(private userService:UsersService, private http:HttpService){
    this.userService.getUsers().subscribe(users=>{this.datas= users})

  }

  ngOnInit(){

this.loadUserInfoButtonClicked()
  }
  userInfo={};

  loadUserInfoButtonClicked() {

    // this.http.get("/hello/userInfo").toPromise().then(result => {
    //   this.userInfo = result.json();
    //   console.log(result.json());
    //     console.log(result);
    //   },
    //
    //
    //     error => {
    //   console.log(error);
    // });
var self = this
    this.http.get("/hello/userInfo").
    map((response) => response.json())
      .map(users =>{
        return users.map(u=>{
          return {
            name:   u.mSurname,
            gender: u.mProduct,
            dateOfBirth: u.mSurname2

          }
        })
      }).
    subscribe((data) => {
      self.userInfo=data;
    console.log(self.userInfo)})

// this.http.get('/hello/userInfo').toPromise().then(res=> {
//   console.log("dfsdf: "+res.json());
// });



  }

  enterButtonClicked() {

    // this.http.get("/hello/userInfo").toPromise().then(res => {
    //   this.http.token = res.text();
    //   console.log(this.http.token);
    //
    // }), error => {
    //   console.error("AUTHENTICATION_UNKNOWN_ERROR", error);
    //   };
  }


  showForms(){
    this.clientForms= !this.clientForms;
  }

  addClient(objectForms){

    console.log(this.searchStr)
    this.datas.push({name: objectForms.fullname})
  }

  changeClient(){


    this.clientForms= !this.clientForms;

    if( this.clientForms==true) {
      var name, mass;

      lolo:for (var i = 0; i < this.datas.length; i++) {

        name = this.datas[i].name;
        mass = name.split(' ');

        if (mass[0] == this.objectLine.patronymic && mass[1] == this.objectLine.firstname && mass[2] == this.objectLine.lastname) {
          this.datas[i].name = this.objectForms.fullname
          console.log('kiki')
          break lolo;


        }
        if(i == this.datas.length){
          alert('you did not choose client to change')

        }

      }

    this.objectForms={
      firstname:'',
      lastname:'',
      patronymic:'',
      get fullname(){
        return ` ${this.patronymic} ${this.firstname} ${this.lastname} `
      }
    }
    }

  }

  deleteClient(){

    var name, mass;
    this.clientForms=!this.clientForms;
    for(var i =0;i<this.datas.length;i++){

      name = this.datas[i].name;
      mass =name.split(' ');

      if(mass[0] == this.objectLine.patronymic && mass[1] == this.objectLine.firstname && mass[2] == this.objectLine.lastname){
        this.datas.splice(i,1)

      }

    }

  }



  showParamData(data){

    this.objectForms.firstname = data.name;
    this.objectForms.lastname = data.gender;
    this.objectForms.patronymic = data.dateOfBirth;
    this.objectLine.firstname = data.name;
    this.objectLine.lastname = data.name;
    this.objectLine.patronymic = data.name;
    console.log(this.objectForms.fullname);

}

  showModalComponent :boolean = false;
  showModal(){
    this.showModalComponent =true
  }



}
