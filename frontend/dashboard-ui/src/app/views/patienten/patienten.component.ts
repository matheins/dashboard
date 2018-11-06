import { Component, OnInit } from '@angular/core';
import { PatientService } from 'src/app/services/patient.service';
import { IPatient } from 'src/app/interfaces/patient';



@Component({
  selector: 'app-patienten',
  templateUrl: './patienten.component.html',
  styleUrls: ['./patienten.component.css'],
})
export class PatientenComponent implements OnInit {

  title = 'Patienten';
  public patients: IPatient[];

  constructor(private patientService: PatientService) { }

  ngOnInit() {
    this.patientService.getAllPatients()
        .subscribe(data => this.patients = data);
  }
}
