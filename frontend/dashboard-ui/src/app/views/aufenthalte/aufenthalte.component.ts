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
  start: number;
  itemsPerPage: number;
  totalItems: any;
  page: any;
  previousPage: any;
  nxtPage: any;
  public aufenthalte: IAufenthalt[];

  constructor(private aufenthaltService: AufenthaltService) {
    this.page = 1;
    this.previousPage = 0;
    this.nxtPage = this.page + 1;
    this.start = 1;
    this.itemsPerPage = 20;

  }

  nextPage(){
    this.page++;
    this.nxtPage++;
    this.previousPage++
    this.start = this.start + this.itemsPerPage;
    this.loadData();
  }

  prevPage(){
    this.page--;
    this.nxtPage--;
    this.previousPage--;
    this.start = this.start - this.itemsPerPage;
    this.loadData();

  }



  ngOnInit() {


    this.loadData();



  }

  loadData(){
    this.aufenthaltService.getAufenthaltePaginiert(this.start, this.itemsPerPage)
    .subscribe(data => this.aufenthalte = data);
  }

}
