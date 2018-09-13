import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError, map, tap} from 'rxjs/operators';
import {Observable, of} from 'rxjs';
import {ClientDetail, ClientRecord} from "../client-list/client-list.component";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private url: string = 'http://localhost:3000';

  constructor(private _http: HttpClient) {
  }

  getClientRecords(): Observable<ClientRecord[]> {
    return this._http.get<ClientRecord[]>(this.url + '/clientRecord', httpOptions)
      .pipe(
        catchError(this.handleError('getClients', []))
      );
  }

  getClientDetail(id: number): Observable<ClientDetail> {
    return this._http.get<ClientDetail>(this.url + '/clientDetails/' + id, httpOptions).pipe(
      catchError(this.handleError<ClientDetail>('get client detail'))
    );
  }

  updateClient (c: ClientDetail): Observable<any> {
    return this._http.put(this.url + '/clientDetails', c, httpOptions).pipe(
      catchError(this.handleError<any>('update client'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}
