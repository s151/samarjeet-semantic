import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  private files: File[] = [];
  private msg: string = "";

  constructor() { }

  setFiles(files: File[]): void {
    this.files = files;
  }

  setMsg(msg: string): void {
    this.msg = msg;
  }

  getFiles(): string {
    if (this.files.length == 0) {
      return "Default";
    }
    let filesString: string = "";
    this.files.forEach(file => {
      filesString += file.name;
    });

    return filesString;
  }

  getMsg(): string {
    return this.msg;
  }

  clearData(): void {
    this.files = [];
    this.msg = "";
  }
}
