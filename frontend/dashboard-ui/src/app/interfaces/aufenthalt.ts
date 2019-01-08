export interface IAufenthalt {
  aufenthaltID: String;
  startdate: Date;
  enddate: Date;
  einweisungsart: String;
  dringlichkeit: number;
  plz: String;
  alter: number;
}

export interface IAufenthaltCountArt{
  datum: String;
  anzahl: number;
  einweisungsart: String;
}

export interface IAufenthaltCount{
  datum: String;
  anzahl: number;
  einweisungsart: String;
}

export interface IEinlieferungen{
  einlieferungen: IAufenthaltCount[];
}
