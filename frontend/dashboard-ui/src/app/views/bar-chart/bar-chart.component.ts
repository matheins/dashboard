import { Component, OnInit } from '@angular/core';
import { AufenthaltService } from 'src/app/services/aufenthalt.service';
import { IDringlichkeit } from 'src/app/interfaces/dringlichkeit';
import { ChartsModule } from 'ng2-charts';
import { Chart } from 'chart.js';
import { IAufenthalt } from 'src/app/interfaces/aufenthalt';



@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit {

  title: String;
  chart: Chart;
  dringlichkeiten: IDringlichkeit[] = [];
  aufenthalte: IAufenthalt[] = [];


  constructor(private aufenthaltService: AufenthaltService) {
  }

  ngOnInit() {

    this.loadDringlichkeitenData();

  }



  loadDringlichkeitenData(){
    this.aufenthaltService.getDringlichkeiten()
    .subscribe( data => {
      this.title = "Dringlichkeiten";
      this.dringlichkeiten = data;
      this.createDringlichkeitenChart();

      //dringlichkeiten-Array full
      console.log(this.dringlichkeiten);
    });
  }



  createDringlichkeitenChart(){
    this.chart = new Chart('canvas', {
      type: 'bar',
      data: {
        labels: this.dringlichkeiten.map(x => x.id),
        datasets: [
          {
            label: ['Dringlichkeiten'],
            data: this.dringlichkeiten.map(x => x.value),
            backgroundColor: ['#FF6384', '#4BC0C0', '#FFCE56', '#E7E9ED', '#36A2EB']
          }
        ]
      },
    });

  }


  // loadData(){
  //   this.aufenthaltService.getDringlichkeiten()
  //   .subscribe( data => {
  //     this.dringlichkeiten = data;
  //     this.setDringlichkeit(this.dringlichkeiten);
  //     console.log(this.dringlichkeiten);
  //   });
  // }



  // events on slice click
  public chartClicked(e:any):void {
    console.log(e);
  }

 // event on pie chart slice hover
  public chartHovered(e:any):void {
    console.log(e);
  }









}
