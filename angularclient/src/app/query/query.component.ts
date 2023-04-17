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

  displayedColumns: string[] | undefined;

  dataSource:string[][] | undefined;

  queriesControl = new FormControl([]);

  queryList: Map<string, string> = new Map([
    ["Name of Area in descending order of total no of crimes",
      "PREFIX ns: <https://data.lacity.org#>\n" +
      "\n" +
      "SELECT ?area (COUNT(?crimeType) AS ?totalCrimes)\n" +
      "WHERE {\n" +
      "  ?crime ns:inArea ?neighborhood ;\n" +
      "         ns:crimeOccurred ?crimeType .\n" +
      "  ?neighborhood ns:areaDescription ?area;\n" +
      "}\n" +
      "GROUP BY ?area\n" +
      "ORDER BY DESC(?totalCrimes)"],
    ["Number of crimes of each crimeType in each area ",
      "PREFIX ns: <https://data.lacity.org#>\n" +
      "                \n" +
      "SELECT ?area ?crimeDesc (COUNT(?crimeType) AS ?totalCrimes)\n" +
      "WHERE {\n" +
      "  ?crime ns:inArea ?neighborhood;\n" +
      "         ns:crimeOccurred ?crimeType.\n" +
      "  ?neighborhood ns:areaDescription ?area.\n" +
      "  ?crimeType ns:crimeDescription ?crimeDesc;\n" +
      "}\n" +
      "GROUP BY ?area ?crimeDesc\n" +
      "ORDER BY DESC(?totalCrimes)"],
    ["No of crimes in each area per year",
      "PREFIX ns: <https://data.lacity.org#>\n" +
      "                \n" +
      "SELECT ?year ?area (COUNT(?crimeType) AS ?numCrimes)\n" +
      "WHERE {\n" +
      "  ?crime ns:inArea ?neighborhood;\n" +
      "         ns:crimeOccurred ?crimeType;\n" +
      "         ns:reportedOn ?date;\n" +
      "         BIND(YEAR(?date) AS ?year).\n" +
      "  ?neighborhood ns:areaDescription ?area.\n" +
      "}\n" +
      "GROUP BY ?year ?area\n" +
      "ORDER BY ?year ?area"],
  ]);

  questionList: string[] = Array.from(this.queryList.keys());

  constructor(private httpClient: HttpClient) { }

  processQuery() {
    if (this.str.replaceAll(" ", "") != "") {
      this.httpClient.post<any>("http://localhost:8080/query", this.str).subscribe(
        res => {
          this.displayedColumns = res.headers;
          this.dataSource = res.data;
        }
      );
    }
  }

  clear() {
    this.str = "";
    this.displayedColumns = undefined;
    this.dataSource = undefined;
    this.queriesControl.reset();
  }

  selectQuery() {
    this.str = this.queriesControl.value!.toString();
  }
}
