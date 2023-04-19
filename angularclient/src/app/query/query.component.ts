import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormControl} from "@angular/forms";
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-query',
  templateUrl: './query.component.html',
  styleUrls: ['./query.component.css']
})
export class QueryComponent {

  str: string = "";
  queryType: string | undefined;
  selectedQuery: QueryData = {
    query: "",
    queryType: "",
    question: ""
  };

  displayedColumns: string[] | undefined;

  dataSource:string[][] | undefined;

  askQueryResponse:boolean | undefined;

  queriesControl = new FormControl([]);

  queryList: QueryData[] = [];

  constructor(private httpClient: HttpClient, public snackBar: MatSnackBar) {
    this.httpClient.get<any>('assets/json/queries.json').subscribe((res) => {
      this.queryList = res.queryList;
    });
  }

  processQuery() {
    if (this.str.replaceAll(" ", "") != "") {
      this.httpClient.post<any>("http://localhost:8080/query/" + this.queryType, this.str).subscribe(
        res => {
          this.displayQueryResponse(res);
        },
        error => {
          let errorMessage = (error.status == '400') ? "Query and type mismatch" : "Invalid Query. Please check your SPARQL Query";
          this.snackBar.open(errorMessage, '', {
            duration: 3000,
            verticalPosition: "top"
          });
        }
      );
    }
  }

  displayQueryResponse(res: any) {
    switch(this.queryType) {
      case "select":
        this.displayedColumns = res.headers;
        this.dataSource = res.data;
        break;
      case "ask":
        this.askQueryResponse = res;
        break;
      case "describe":
      case "construct":
    }
  }

  clear() {
    this.str = "";
    this.queryType = undefined;
    this.queriesControl.reset();
  }

  clearResults() {
    this.displayedColumns = undefined;
    this.dataSource = undefined;
    this.askQueryResponse = undefined;
  }

  selectQueryFromDropdown() {
    this.clearResults();
    this.queryType = this.selectedQuery.queryType;
    this.str = this.selectedQuery.query;
  }
}

interface QueryData {
  queryType: string,
  question: string,
  query: string
}
