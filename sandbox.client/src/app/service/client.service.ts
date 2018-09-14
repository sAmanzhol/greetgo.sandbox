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

  updateClientDetail (c: ClientDetail): Observable<any> {
    return this._http.put(this.url + '/clientDetails/' + c.id, c, httpOptions).pipe(
      catchError(this.handleError<any>('update client'))
    );
  }

  updateClientRecord (c: ClientRecord): Observable<any> {
    console.log(c);
    return this._http.put(this.url + '/clientRecord/' + c.id, c, httpOptions).pipe(
      catchError(this.handleError<any>('update client'))
    );
  }

  deleteClientDetails (id: number): Observable<any> {
    return this._http.delete(this.url + '/clientDetails/' + id, httpOptions).pipe(
      catchError(this.handleError<any>('update client'))
    );
  }

  deleteClientRecord (id: number): Observable<any> {
    return this._http.delete(this.url + '/clientRecord/' + id, httpOptions).pipe(
      catchError(this.handleError<any>('update client'))
    );
  }

  addClientRecord (c: ClientRecord): Observable<ClientRecord> {
    return this._http.post<ClientRecord>(this.url + '/clientRecord/', c, httpOptions).pipe(
      catchError(this.handleError<ClientRecord>('add new record'))
    );
  }

  addClientDetailes (c: ClientDetail): Observable<ClientDetail> {
    return this._http.post<ClientDetail>(this.url + '/clientDetails/', c, httpOptions).pipe(
      catchError(this.handleError<ClientDetail>('add new record'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}
