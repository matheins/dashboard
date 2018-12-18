import { Component, OnInit } from '@angular/core';
import { AufenthaltService } from 'src/app/services/aufenthalt.service';
import { ChartsModule } from 'ng2-charts';
import { Chart } from 'chart.js';
import { IAufenthalt } from 'src/app/interfaces/aufenthalt';
import { reduce } from 'rxjs/operators';

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.css']
})
export class LineChartComponent implements OnInit {

  title: String;
  chart: Chart;
  aufenthalte: IAufenthalt[] = [];


  loadAnzahlEinlieferungen(){
    this.aufenthaltService.getAufenthaltePaginiert(1, 100)
    .subscribe( data => {
      this.title = "Anzahl Aufenthalte";
      this.aufenthalte = data;
      this.createAnzahlEinlieferungenChart();

      console.log(this.aufenthalte);
    })
  }

  createAnzahlEinlieferungenChart(){
    this.chart = new Chart('canvas', {
      type: 'line',
      data: {
        labels: this.aufenthalte.map(x => x.startdate),
        datasets: [
          {
            label: ['Aufenthalte'],
            data: this.aufenthalte.map(x => x.dringlichkeit),
            backgroundColor: "#4BC0C0",
          },
          {
            label: ['Aufenthalte'],
            data: this.aufenthalte.map(x => x.alter),
            backgroundColor: "#FF6384",
          },

        ]
      }
    });
  }

  // events on slice click
  public chartClicked(e:any):void {
    console.log(e);
  }

 // event on pie chart slice hover
  public chartHovered(e:any):void {
    console.log(e);
  }
  constructor(private aufenthaltService: AufenthaltService) { }

  ngOnInit() {
    this.loadAnzahlEinlieferungen();
  }

}
