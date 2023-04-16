import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-query',
  templateUrl: './query.component.html',
  styleUrls: ['./query.component.css']
})
export class QueryComponent {

  str: string = "";
  displayedColumns: string[] | undefined;
  dataSource:String[][] | undefined;

  constructor(private httpClient: HttpClient) { }

  processQuery() {
    this.httpClient.post<any>("http://localhost:8080/query", this.str).subscribe(
      res => {
        this.displayedColumns = res.headers;
        this.dataSource = res.data;
      }
    );
  }

  clear() {
    this.str = "";
    this.displayedColumns = undefined;
    this.dataSource = undefined;
  }
}
