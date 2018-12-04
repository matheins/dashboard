import { Component, OnInit } from '@angular/core';
import { AufenthaltService } from 'src/app/services/aufenthalt.service';
import { IDringlichkeit } from 'src/app/interfaces/dringlichkeit';
import { ChartsModule } from 'ng2-charts';
import { Chart } from 'chart.js';



@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit {



  // public barChartLabels:String[] = ["1", "2", "3", "4", "5"];
  // public barChartData:number[] = this.dringlichkeiten;
  // public barChartType:string = 'bar';
  // public barChartOptions:any = {'backgroundColor': [
  //              "#FF6384",
  //           "#4BC0C0",
  //           "#FFCE56",
  //           "#E7E9ED",
  //           "#36A2EB"
  //           ]}

  title = "Dringlichkeiten";
  chart: Chart;
  dringlichkeiten: IDringlichkeit[] = [];


  constructor(private aufenthaltService: AufenthaltService) {
  }

  ngOnInit() {

    this.loadData();

  }

  loadData(){
    this.aufenthaltService.getDringlichkeiten()
    .subscribe( data => {
      this.dringlichkeiten = data;
      this.createChart();

      //dringlichkeiten-Array full
      console.log(this.dringlichkeiten);
    });
  }

  createChart(){
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
