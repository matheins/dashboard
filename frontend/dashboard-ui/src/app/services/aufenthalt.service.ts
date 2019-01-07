import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IAufenthalt, IAufenthaltCount, IAufenthaltCountArt } from '../interfaces/aufenthalt';
import { IDringlichkeit } from '../interfaces/dringlichkeit';
import { map, catchError, tap } from 'rxjs/operators';



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
  getAufenthaltePaginiert(start, size): Observable<IAufenthalt[]> {
    return this.http.get<IAufenthalt[]>(this.ROOT_URL + '/aufenthalte?'+'start='+start+'&'+'size='+size);
  }

  getDringlichkeiten(): Observable<IDringlichkeit[]> {
        return this.http.get<IDringlichkeit[]>(this.ROOT_URL + '/aufenthalte/dringlichkeit');

  }

  getAufenthalteByDate(range, startYear, startMonth, startDay, endYear, endMonth, endDay, art): Observable<IAufenthaltCountArt[]> {
    console.log(this.ROOT_URL + '/aufenthalte/zeit/' + range + '?vonDatum=' + startYear + '-' + startMonth +'-' + startDay + '%2000:00:00&bisDatum=' + endYear + '-' + endMonth + '-' + endDay + '%2000:00:00&&einweisungsart=' + art);
    return this.http.get<IAufenthaltCountArt[]>(this.ROOT_URL + '/aufenthalte/zeit/' + range + '?vonDatum=' + startYear + '-' + startMonth +'-' + startDay + '%2000:00:00&bisDatum=' + endYear + '-' + endMonth + '-' + endDay + '%2000:00:00&einweisungsart=' + art)
  }



}
