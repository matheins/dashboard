import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IPatient } from '../interfaces/patient';
import { Observable } from 'rxjs';



@Injectable()
export class PatientService {

  readonly ROOT_URL = 'https://jsonplaceholder.typicode.com';
  patients: any;

  constructor(private http: HttpClient) {}

  getAllPatients(): Observable<IPatient[]> {
    return this.http.get<IPatient[]>(this.ROOT_URL + '/users');
  }
}
