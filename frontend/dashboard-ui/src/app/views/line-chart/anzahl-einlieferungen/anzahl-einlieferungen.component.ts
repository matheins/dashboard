import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { IAufenthalt, IAufenthaltCount } from 'src/app/interfaces/aufenthalt';
import { AufenthaltService } from 'src/app/services/aufenthalt.service';
import { reduce } from 'rxjs/operators';
import { IDringlichkeit } from 'src/app/interfaces/dringlichkeit';
import { DatepickerRangeComponent } from 'src/app/tools/datepicker-range/datepicker-range.component';
import { getLocaleDateFormat } from '@angular/common';
import {NgbDate, NgbCalendar} from '@ng-bootstrap/ng-bootstrap';
import { toDate } from '@angular/common/src/i18n/format_date';



@Component({
  selector: 'app-anzahl-einlieferungen',
  templateUrl: './anzahl-einlieferungen.component.html',
  styleUrls: ['./anzahl-einlieferungen.component.css']
})
export class AnzahlEinlieferungenComponent implements OnInit {

  title: String;
  chart: Chart;
  aufenthalte: IAufenthalt[] = [];
  countRettungsdienst: IAufenthaltCount[] = [];
  countEinweisung: IAufenthaltCount[] = [];
  countSelbst: IAufenthaltCount[] = [];
  countZuweisungVerlegung: IAufenthaltCount[] = [];
  countNotarzt: IAufenthaltCount[] = [];
  startYear: number;
  startMonth: number;
  startDay: number;
  endYear: number;
  endMonth: number;
  endDay: number;
  range: String = "monate";

  ngOnInit() {
    this.loadData();
  }

  constructor(private aufenthaltService: AufenthaltService, private calendar: NgbCalendar) {
    this.fromDate = calendar.getToday();
    this.toDate = calendar.getNext(calendar.getToday(), 'd', 10);
  }

  loadData(){
    this.getDate();
    this.loadAnzahlEinlieferungenRettungsdienst();
    this.loadAnzahlEinlieferungenEinweisung();
    this.loadAnzahlEinlieferungenNotarzt();
    this.loadAnzahlEinlieferungenZuweisungVerlegung();
    this.loadAnzahlEinlieferungenSelbst();
  }



  createChartRettungsdienst(){
    this.chart = new Chart('rettungsdienst', {
      type: 'line',
      data: {
        labels: this.countRettungsdienst.map(x => x.datum),
        datasets: [
          {
            label: ["Rettungsdienst"],
            data: this.countRettungsdienst.map(x => x.anzahl),
            backgroundColor: "#4BC0C0",
          },
        ]
      }
    });
  }

  createChartEinweisung(){
    this.chart = new Chart('einweisung', {
      type: 'line',
      data: {
        labels: this.countEinweisung.map(x => x.datum),
        datasets: [
          {
            label: ["Einweisung"],
            data: this.countEinweisung.map(x => x.anzahl),
            backgroundColor: "#7294cb",
          },
        ]
      }
    });
  }

  createChartSelbst(){
    this.chart = new Chart('selbst', {
      type: 'line',
      data: {
        labels: this.countSelbst.map(x => x.datum),
        datasets: [
          {
            label: ["Selbst"],
            data: this.countSelbst.map(x => x.anzahl),
            backgroundColor: "#3f9852",
          },
        ]
      }
    });
  }

  createChartNotarzt(){
    this.chart = new Chart('notarzt', {
      type: 'line',
      data: {
        labels: this.countNotarzt.map(x => x.datum),
        datasets: [
          {
            label: ["Notarzt"],
            data: this.countNotarzt.map(x => x.anzahl),
            backgroundColor: "#cc2528",
          },
        ]
      }
    });
  }

  createChartZuweisungVerlegung(){
    this.chart = new Chart('zuweisung_verlegung', {
      type: 'line',
      data: {
        labels: this.countZuweisungVerlegung.map(x => x.datum),
        datasets: [
          {
            label: ["Zuweisung-Verlegung"],
            data: this.countZuweisungVerlegung.map(x => x.anzahl),
            backgroundColor: "#535055",
          },
        ]
      }
    });
  }


  setRange(id: number){
    if(id == 1){
      this.range = "monate";
    }else if(id == 2){
      this.range = "wochen";
    }
    this.loadData();

  }


  getDate(){
    this.startYear = this.fromDate.year;
    this.startMonth = this.fromDate.month;
    this.startDay = this.fromDate.day;
    this.endYear = this.toDate.year;
    this.endMonth = this.toDate.month;
    this.endDay = this.toDate.day;
  }





    //Rettungsdienst
    loadAnzahlEinlieferungenRettungsdienst(){
      this.getDate();
      this.aufenthaltService.getAufenthalteByDate(this.range, this.startYear, this.startMonth, this.startDay, this.endYear, this.endMonth, this.endDay, "Rettungsdienst")
      .subscribe( data => {
        this.countRettungsdienst = data;
        console.log(data);
        this.createChartRettungsdienst();

        console.log(this.aufenthalte);
        //console.log(this.startYear, this.startMonth, this.startDay, this.endYear, this.endMonth, this.endDay);
      })
    }

    //Einweisung
    loadAnzahlEinlieferungenEinweisung(){
      this.aufenthaltService.getAufenthalteByDate(this.range, this.startYear, this.startMonth, this.startDay, this.endYear, this.endMonth, this.endDay, "Einweisung")
      .subscribe( data => {
        this.countEinweisung = data;
        console.log(data);
        this.createChartEinweisung();

        console.log(this.aufenthalte);
        //console.log(this.startYear, this.startMonth, this.startDay, this.endYear, this.endMonth, this.endDay);
      })
    }

    //Selbst
    loadAnzahlEinlieferungenSelbst(){
      this.aufenthaltService.getAufenthalteByDate(this.range, this.startYear, this.startMonth, this.startDay, this.endYear, this.endMonth, this.endDay, "Selbst")
      .subscribe( data => {
        this.countSelbst = data;
        console.log(data);
        this.createChartSelbst();

        console.log(this.aufenthalte);
        //console.log(this.startYear, this.startMonth, this.startDay, this.endYear, this.endMonth, this.endDay);
      })
    }

    //zuweisung_verlegung
    loadAnzahlEinlieferungenZuweisungVerlegung(){
      this.aufenthaltService.getAufenthalteByDate(this.range, this.startYear, this.startMonth, this.startDay, this.endYear, this.endMonth, this.endDay, "zuweisung_verlegung")
      .subscribe( data => {
        this.countZuweisungVerlegung = data;
        console.log(data);
        this.createChartZuweisungVerlegung();

        console.log(this.aufenthalte);
        //console.log(this.startYear, this.startMonth, this.startDay, this.endYear, this.endMonth, this.endDay);
      })
    }

    //Notarzt
    loadAnzahlEinlieferungenNotarzt(){
      this.aufenthaltService.getAufenthalteByDate(this.range, this.startYear, this.startMonth, this.startDay, this.endYear, this.endMonth, this.endDay, "Notarzt")
      .subscribe( data => {
        this.countNotarzt = data;
        console.log(data);
        this.createChartNotarzt();

        console.log(this.aufenthalte);
        //console.log(this.startYear, this.startMonth, this.startDay, this.endYear, this.endMonth, this.endDay);
      })
    }


      // events on slice click
  public chartClicked(e:any):void {
    console.log(e);
  }

 // event on pie chart slice hover
  public chartHovered(e:any):void {
    console.log(e);
  }

  //Datepicker
  hoveredDate: NgbDate;

  fromDate: NgbDate;
  toDate: NgbDate;

  onDateSelection(date: NgbDate) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
      console.log(this.fromDate);
    } else if (this.fromDate && !this.toDate && date.after(this.fromDate)) {
      this.toDate = date;
      console.log(this.toDate);
    } else {
      this.toDate = null;
      this.fromDate = date;
      console.log(this.fromDate);
    }
  }

  isHovered(date: NgbDate) {
    return this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate);
  }

  isInside(date: NgbDate) {
    return date.after(this.fromDate) && date.before(this.toDate);
  }

  isRange(date: NgbDate) {
    return date.equals(this.fromDate) || date.equals(this.toDate) || this.isInside(date) || this.isHovered(date);
  }

}
