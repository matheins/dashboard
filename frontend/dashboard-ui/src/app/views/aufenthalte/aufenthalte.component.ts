import { Component, OnInit } from '@angular/core';
import { IAufenthalt } from 'src/app/interfaces/aufenthalt';
import { AufenthaltService } from 'src/app/services/aufenthalt.service';

@Component({
  selector: 'app-aufenthalte',
  templateUrl: './aufenthalte.component.html',
  styleUrls: ['./aufenthalte.component.css']
})
export class AufenthalteComponent implements OnInit {
  title = 'Aufenthalte';
  public aufenthalte: IAufenthalt[];

  constructor(private aufenthaltService: AufenthaltService) {
  }




  ngOnInit() {
    this.aufenthaltService.getAllAufenthalte()
        .subscribe(data => this.aufenthalte = data);


  }

}
