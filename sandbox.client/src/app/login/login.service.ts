import { Injectable,  OnInit} from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class LoginService implements OnInit {
  role: string;

  constructor() { }

  ngOnInit(): void {
  }

  setUserRole(l: string, p:string): string {
    if(p==='123' && l==='pushkin'){
      this.role=Role.User;
    }
    if(p==='admin' && l==='admin'){
      this.role=Role.Admin;
    }
    return this.role;
  }

}


export enum Role{
  User = 'User',
  Admin = 'Admin'
}
