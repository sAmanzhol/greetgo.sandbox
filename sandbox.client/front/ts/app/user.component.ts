import {Component, OnInit} from "@angular/core";
import {UsersService} from "./users.service";

@Component({
  selector:'app-user',
  template: require('./user.component.html'),
  styles: [require('./user.component.css')],
  providers: [UsersService]

})
export class UserComponent implements OnInit{
  gender=[
    {
      name:"male"
    },
    {
      name:'female'
    }
  ];
  showForms =false;
  currentForms;
  textStr ='';
  settings =[];
  datas = [];
  isMarked = false;
  constructor(private userService:UsersService){}



  ngOnInit(){
    this.settings= this.userService.settings;
    this.currentForms =this.userService.forms;
    this.datas = this.userService.datas
  }

  //show Forms
  addUser(){
    this.showForms= !this.showForms;
    this.isMarked = !this.isMarked
    console.log(this.isMarked +' solo')
  }

  // changeUser(users) {
  //   for(var i =0; i<this.datas.length;i++){
  //     for (var key in this.datas[i])
  //       if(users.name == key){
  //         this.datas.sort(function(a, b){
  //           if(a[key] < b[key]) return -1;
  //           if(a[key] > b[key]) return 1;
  //           return 0;
  //         }
  //         )
  //         console.log(this.datas)
  //
  //       }
  //   }
  //
  //   }

  changeUser(users){
    var self = this
    function compareAge(personA, personB) {
      if(personA[users] < personB[users]) return -1;
      if(personA[users]> personB[users]) return 1;
      return 0;
    }
    self.datas.sort(compareAge);
    for(var i = 0; i < this.datas.length; i++) {
      console.log(this.datas[i].name); // Вовочка Маша Вася
      console.log(users)
    }
  }
addNewData(){
  this.datas.push({
    'name':this.currentForms.firstname + ' ' + this.currentForms.lastname + ' ' + this.currentForms.patronymic,
    'gender':this.currentForms.gender,
  })}




}