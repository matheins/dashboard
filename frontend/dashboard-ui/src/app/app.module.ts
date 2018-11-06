import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PatientenComponent } from './views/patienten/patienten.component';
import { HttpClientModule } from '@angular/common/http';
import { PatientService } from './services/patient.service';


@NgModule({
  declarations: [
    AppComponent,
    PatientenComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [PatientService],
  bootstrap: [AppComponent]
})
export class AppModule { }
