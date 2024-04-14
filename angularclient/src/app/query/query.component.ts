import {Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormControl} from "@angular/forms";
import {MatSnackBar} from '@angular/material/snack-bar';
import * as vkbeautify from 'vkbeautify';
import { Pipe, PipeTransform } from "@angular/core";
import {SharedService} from "../shared.service";

@Pipe({
  name: 'xml'
})
export class XmlPipe implements PipeTransform {
  transform(value: string): string {
    return vkbeautify.xml(value);
  }
}

@Component({
  selector: 'app-query',
  templateUrl: './query.component.html',
  styleUrls: ['./query.component.css']
})
export class QueryComponent implements OnInit{

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

  constructQueryResponse:string | undefined;

  describeQueryResponse:string | undefined;

  queriesControl = new FormControl([]);

  queryList: QueryData[] = [];

  dataset: string = "";

  constructor(private httpClient: HttpClient, public snackBar: MatSnackBar, public sharedService: SharedService) {
    // fetch queries from queries.json to display in the "Sample Queries" dropdown
    this.httpClient.get<any>('assets/json/queries.json').subscribe((res) => {
      this.queryList = res.queryList;
    });
  }

  ngOnInit() {
    this.dataset = this.sharedService.getFiles();
  }

  getOptions() {
    if (this.queryType == 'construct' || this.queryType == 'describe') {
      return "text";
    } else return "json";
  }

  processQuery() {
    // we don't want to make API calls when the query textbox is empty/only has spaces
    if (this.str.replaceAll(" ", "") != "") {
      // @ts-ignore
      this.httpClient.post("http://localhost:8080/query/" + this.queryType, this.str, {responseType: this.getOptions()}).subscribe(
        res => {
          this.displayQueryResponse(res);
        },
        error => {
          // response 400 is returned when the given query and the selected queryType do not match
          // response 500 is returned when there are syntactical errors in the given query
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
        this.describeQueryResponse = res;
        break;
      case "construct":
        this.constructQueryResponse = res;
    }
  }

  clear() {
    this.str = "";
    this.queryType = undefined;
    this.clearResults();
    this.queriesControl.reset();
  }

  clearResults() {
    this.displayedColumns = undefined;
    this.dataSource = undefined;
    this.askQueryResponse = undefined;
    this.constructQueryResponse = undefined;
    this.describeQueryResponse = undefined;
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
