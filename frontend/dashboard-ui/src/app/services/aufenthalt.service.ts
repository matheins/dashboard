import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IAufenthalt } from '../interfaces/aufenthalt';


@Injectable({
  providedIn: 'root'
})
export class AufenthaltService {

  readonly ROOT_URL = 'http://localhost:4567';
  patients: any;

  constructor(private http: HttpClient) {}

  getAllAufenthalte(): Observable<IAufenthalt[]> {
    return this.http.get<IAufenthalt[]>(this.ROOT_URL + '/aufenthalte');
  }
}
