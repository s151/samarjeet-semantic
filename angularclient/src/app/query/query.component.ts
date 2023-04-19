import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormControl} from "@angular/forms";

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

  queriesControl = new FormControl([]);

  queryList: QueryData[] = [];

  constructor(private httpClient: HttpClient) {
    this.httpClient.get<any>('assets/json/queries.json').subscribe((res) => {
      this.queryList = res.queryList;
    });
  }

  processQuery() {
    if (this.str.replaceAll(" ", "") != "") {
      let requestBody = {
        queryType: this.queryType,
        value: this.str
      }
      this.httpClient.post<any>("http://localhost:8080/query", requestBody).subscribe(
        res => {
          this.displayedColumns = res.headers;
          this.dataSource = res.data;
        }
      );
    }
  }

  clear() {
    this.str = "";
    this.queryType = undefined;
    this.displayedColumns = undefined;
    this.dataSource = undefined;
    this.queriesControl.reset();
  }

  selectQueryFromDropdown() {
    this.queryType = this.selectedQuery.queryType;
    this.str = this.selectedQuery.query;
  }
}

interface QueryData {
  queryType: string,
  question: string,
  query: string
}
