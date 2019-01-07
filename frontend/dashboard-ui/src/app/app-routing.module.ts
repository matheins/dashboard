import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { PatientenComponent } from './views/patienten/patienten.component';
import { PieChartComponent } from './views/pie-chart/pie-chart.component';
import { BarChartComponent } from './views/bar-chart/bar-chart.component';
import { AufenthalteComponent } from './views/aufenthalte/aufenthalte.component';
import { AnzahlEinlieferungenComponent } from './views/line-chart/anzahl-einlieferungen/anzahl-einlieferungen.component';

const routes: Routes = [
  {
    path: 'patienten',
    component: PatientenComponent,
  },
  {
    path: 'aufenthalte',
    component: AufenthalteComponent,
  },
  {
    path: 'pie-chart',
    component: PieChartComponent,
  },
  {
    path: 'bar-chart',
    component: BarChartComponent,
  },
  {
    path: '',
    component: AnzahlEinlieferungenComponent,
  },
  {
    path: 'anzahl-einlieferungen',
    component: AnzahlEinlieferungenComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
