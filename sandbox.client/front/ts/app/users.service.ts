import {Http} from "@angular/http";
import {Injectable, OnInit} from "@angular/core";
import "rxjs/add/operator/map";

@Injectable()
export class UsersService implements OnInit{

  constructor(private http:Http){}


  getUsers(){
    return this.http.get('https://randomuser.me/api/?inc=gender,name,picture,location,email,dob,phone,&results=16&nat=gb')
      .map(response=> response.json())
      .map(response => response.results)
      .map(users =>{
        return users.map(u=>{
          return {
            name:   u.name.title + ' ' + u.name.first + ' ' + u.name.last,
            gender: u.gender,
            dateOfBirth: u.dob.date,
            // addressOfResidence: u.location.city +  ' ' + u.location.state + ' '+ u.location.street,
            // addressOfRegistration: u.location.coordinates.latitude + ' ' + u.location.coordinates.longitude,
            // phone: u.phone

          }
        })
      })

      }


ngOnInit(){
  this.getUsers().subscribe(users=>{this.datas= users})

}

getTable(){

}
clients=[];
  settings = [
    {name:'name'},
    {name:'имя'},
    {name:'отчество'},
    {name:'gender'},
    {name:'dateOfBirth'},
    {name:'Характер'},
    {name:'addressOfResidence'},
    {name:'addressOfRegistration'},
    {name:'phone'},
  ];
  forms = {
    lastname:'',
    firstname:'',
    patronymic:'',
    gender:'',
    character:'',
    dateOfBirth:'',
    addressOfResidence:'',
    addressOfRegistration:'',
    PhoneType:'',
  }

  datas = [
    {
      lastname: "Bret",
      fisrtname: "Leanne Graham",
      patronymic: "Leanne Graham",
      gender:"Male",
      dateOfBirth:'22.12.1997',
      character:'Lololo',
      addressOfResidence:"Almaty Auez au mau",
      addressOfRegistration:'SKR Zhetisay city Mukhanov 25',
      PhoneType:'mobile',
    },
    {
      lastname: "Nazar",
      fisrtname: "Abu",
      patronymic: "Leanne Graham",
      gender:"Male",
      dateOfBirth:'22.12.1997',
      character:'Lololo',
      addressOfResidence:"Almaty Auez au mau",
      addressOfRegistration:'SKR Zhetisay city Mukhanov 25',
      PhoneType:'home',

    },

  ];

}