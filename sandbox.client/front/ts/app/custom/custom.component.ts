import {Component, Input, OnInit} from "@angular/core";
import {UsersService} from "../users.service";
import {HttpService} from "../HttpService";
import {PhoneType} from "../../model/PhoneType";
import {UserInfo} from "../../model/UserInfo";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";

export class User{
  name: string;
  email: string;
  phone: string;
}


@Component({
  selector:'app-custom',
  template: require('./custom.component.html'),
  styles: [require('./custom.component.css')],
  providers:[UsersService],
})





export class CustomComponent {



  myForm : FormGroup;
  constructor(){
    this.myForm = new FormGroup({

      "userName": new FormControl("Tom", [Validators.required]),
      "userEmail": new FormControl("", [
        Validators.required,
        Validators.pattern("[a-zA-Z_]+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}")
      ]),
      "phones": new FormArray([
        new FormControl("+7", Validators.required)
      ])
    });
  }
  addPhone(){
    // if()
    (<FormArray>this.myForm.controls["phones"]).push(new FormControl("+7", Validators.required));
  }
  submit(){
    console.log(this.myForm);
  }

}


  /*@Input() client;
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
        return `${this.firstname} ${this.lastname} ${this.patronymic} `
    }
  }

  searchFilter={
    firstname:'',
    lastname:'',
    patronymic:'',
  };
  clientForms=true;
  datas =[];
  showButtons=true;
  userInfo=[];

  clients=[]

  constructor(private userService:UsersService, private http:HttpService){
    // this.userService.getUsers().subscribe(users=>{this.datas= users})

  }

  ngOnInit(){
this.loadUserInfoButtonClicked()
   this.pagination()
      this.clients=this.userService.clients
  }


  loadUserInfoButtonClicked() {

    var self = this
    this.http.get("/hello/userInfo").
    map((response) => response.json())
      .map(users =>{
        return users.map(u=>{
          return {
            name:   u.mProduct,
            gender: u.mSurname,
            dateOfBirth: u.mSurname2

          }
        })
      }).
    subscribe((data) => {
      self.datas=data;

    console.log(self.datas)})


  }





   addList= {
     mProduct:'',
     mSurname:'',
     mSurname2:''
  }

  editButtons(){

    this.showButtons=true;

  }
  editButtons1(){

    this.showButtons=false;

  }
  addClient(objectForms){


    this.addList.mProduct=objectForms.firstname;
    this.addList.mSurname=objectForms.lastname;
    this.addList.mSurname2=objectForms.patronymic;

    console.log(this.addList)

    var self = this;
    this.datas.push({name: objectForms.fullname.split(' ')[0],
        gender: objectForms.fullname.split(' ')[1],
        dateOfBirth: objectForms.fullname.split(' ')[2]
      })
    console.log(this.datas)
    console.log('soro ')

      this.http.get('/hello/addUserInfo',self.addList).
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
      self.datas=data;
      console.log(self.datas)})
  }

  addList2= {
    mProduct:'',
    mSurname:'',
    mSurname2:'',
    index:0
  }

  changeClient(){


    var self = this;
    var name, gender, dateOfBirth, mass;

      lolo:for (var i = 0; i < this.datas.length; i++) {


        name = this.datas[i].name;
        gender = this.datas[i].gender
        dateOfBirth = this.datas[i].dateOfBirth


        if(dateOfBirth == this.objectLine.patronymic && name == this.objectLine.firstname && gender == this.objectLine.lastname) {
          this.datas[i].name = this.objectForms.firstname
          this.datas[i].gender= this.objectForms.lastname
          this.datas[i].dateOfBirth= this.objectForms.patronymic
          console.log('kiki')

          this.addList2.mProduct=this.objectForms.firstname;
          this.addList2.mSurname=this.objectForms.lastname;
          this.addList2.mSurname2=this.objectForms.patronymic;
          this.addList2.index = i;

          this.http.get('/hello/editUserInfo',self.addList2).
          map((response) => response.json())
            .map(users =>{
              return users.map(u=>{
                return {
                  name:   u.mProduct,
                  gender: u.mSurname,
                  dateOfBirth: u.mSurname2

                }
              })
            }).
          subscribe((data) => {
            self.datas=data;
            console.log(self.datas)})

          break lolo;


        }
        if(i == this.datas.length-1){
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


  addList1= {
    mProduct:'',
    mSurname:'',
    mSurname2:'',
    index:0
  }
  deleteClient(){


    var self = this;
    var name, gender, dateOfBirth, mass;
    for(var i =0;i<this.datas.length;i++){
      name = this.datas[i].name;
      gender = this.datas[i].gender
      dateOfBirth = this.datas[i].dateOfBirth
      // console.log(name + ' ' + gender + ' ' + dateOfBirth + 'lololololo ' )



      if(dateOfBirth == this.objectLine.patronymic && name == this.objectLine.firstname && gender == this.objectLine.lastname){
        this.addList1.mProduct=name;
        this.addList1.mSurname=gender;
        this.addList1.mSurname2=dateOfBirth;
        this.addList1.index = i;
        if(i ==0) {

          this.addList1.index = 100;
        }


        console.log('done')
        // this.datas.splice(i,1)
        this.http.delete('/hello/deleteUserInfo',self.addList1).
        map((response) => response.json())
          .map(users =>{
            return users.map(u=>{
              return {
                name:   u.mProduct,
                gender: u.mSurname,
                dateOfBirth: u.mSurname2

              }
            })
          }).
        subscribe((data) => {
          self.datas=data;
          console.log(self.datas)})


      }
    }



  }

  showParamData(data){

    this.objectForms.firstname = data.name;
    this.objectForms.lastname = data.gender;
    this.objectForms.patronymic = data.dateOfBirth;
    this.objectLine.firstname = data.name;
    this.objectLine.lastname = data.gender;
    this.objectLine.patronymic = data.dateOfBirth;
    console.log(this.objectForms.fullname);

  }


pagins =[];
  indexPagins=[];
  pagination(){

    var self = this
    var pagin :number|string;


    this.http.get("/hello/pagination").toPromise().then(res => {
      pagin = res.text();
      console.log('lololol');
      pagin = Number(pagin);
      console.log(pagin);
      for (var i =0;i<pagin; i++){
        this.pagins[i]=i;
      }
      this.indexPagins=this.pagins;
      
    })



    // this.http.get("/hello/pagination").
    // map((response) => response.json())
    //   .map(users =>{
    //     return users.map(u=>{
    //       return {
    //         name:   u.mSurname,
    //         gender: u.mProduct,
    //         dateOfBirth: u.mSurname2
    //
    //       }
    //     })
    //   }).
    // subscribe((data) => {
    //   self.datas=data;
    //   console.log(self.datas)})


  }
  indexes ={
    index:null
  }
  changePagination(index){
    this.pagination();

    this.indexPagins=null;
    console.log(index)
    var self = this

    this.indexes.index=index;

    this.http.get("/hello/getPagination", this.indexes).
    map((response) => response.json())
      .map(users =>{
        return users.map(u=>{
          return {
            gender: u.mProduct,
            name:   u.mSurname,
            dateOfBirth: u.mSurname2

          }
        })
      }).
    subscribe((data) => {
      self.datas=data;
      console.log(self.datas)})


    if(self.indexes.index>14)
    self.indexPagins=self.pagins.slice(self.indexes.index-2,self.indexes.index+3);
  console.log(this.indexPagins)


  }
  rigthChangePagination(){
    if(this.indexes.index==null){
      this.indexes.index=0
    }


    if(this.indexes.index==this.pagins.length-1){
      this.indexes.index
    }
    else {
      ++this.indexes.index;
    }
    this.changePagination(this.indexes.index)
  }
  leftChangePagination(){
    if(this.indexes.index==null){
      this.indexes.index=0
    }

    if(this.indexes.index==0){
      this.indexes.index=0;
    }
    else {
      --this.indexes.index
    }

    this.changePagination(this.indexes.index)
  }


  getFilter(){
    var notAlways;
    var self =this;
    this.http.get("/hello/getFilter", this.searchFilter).
    map((response) => response.json())
      .map(users =>{
        return users.map(u=>{
          return {
            name:   u.mProduct,
            gender: u.mSurname,
            dateOfBirth: u.mSurname2

          }
        })
      }).
    subscribe((data) => {
       this.datas = data;
      console.log(this.datas)}

      )

  }

  isMarked=false;
    getMarked(){
    this.isMarked = !this.isMarked;
    }

}
*/