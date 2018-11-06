import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PatientenComponent } from './views/patienten/patienten.component';
import { HttpClientModule } from '@angular/common/http';
import { PatientService } from './services/patient.service';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { PieChartComponent } from './views/pie-chart/pie-chart.component';
import { BarChartComponent } from './views/bar-chart/bar-chart.component';

@NgModule({
  declarations: [
    AppComponent,
    PatientenComponent,
    PieChartComponent,
    BarChartComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ChartsModule
  ],
  providers: [PatientService],
  bootstrap: [AppComponent]
})
export class AppModule { }
