import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { PatientenComponent } from './views/patienten/patienten.component';
import { PieChartComponent } from './views/pie-chart/pie-chart.component';
import { BarChartComponent } from './views/bar-chart/bar-chart.component';

const routes: Routes = [
  {
    path: 'patienten',
    component: PatientenComponent,
  },
  {
    path: 'pie-chart',
    component: PieChartComponent,
  },
  {
    path: 'bar-chart',
    component: BarChartComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
