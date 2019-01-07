import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnzahlEinlieferungenComponent } from './anzahl-einlieferungen.component';

describe('AnzahlEinlieferungenComponent', () => {
  let component: AnzahlEinlieferungenComponent;
  let fixture: ComponentFixture<AnzahlEinlieferungenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnzahlEinlieferungenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnzahlEinlieferungenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
