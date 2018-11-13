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
import { LineChartComponent } from './views/line-chart/line-chart.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DatepickerRangeComponent } from './tools/datepicker-range/datepicker-range.component';

@NgModule({
  declarations: [
    AppComponent,
    PatientenComponent,
    PieChartComponent,
    BarChartComponent,
    LineChartComponent,
    DatepickerRangeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ChartsModule,
    NgbModule
  ],
  providers: [PatientService],
  bootstrap: [AppComponent]
})
export class AppModule { }
