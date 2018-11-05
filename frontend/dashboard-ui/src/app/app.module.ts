import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PatientComponent } from './general/patient/patient.component';
import { PatientenComponent } from './views/patienten/patienten.component';

@NgModule({
  declarations: [
    AppComponent,
    PatientComponent,
    PatientenComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
