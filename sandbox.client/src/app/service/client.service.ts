import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { Client } from "../client-list/client-list.component";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private url: string = 'http://localhost:3000/clients';
  constructor(private _http: HttpClient) { }

  getClients (): Observable<Client[]> {
    return this._http.get<Client[]>(this.url)
      .pipe(
        catchError(this.handleError('getClients', []))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}
