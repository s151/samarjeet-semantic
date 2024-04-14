import { Component, OnInit } from '@angular/core';
import {FileUploadService} from "../file-upload.service";
import {SharedService} from "../shared.service";

@Component({
  selector: 'app-manage-dataset',
  templateUrl: './manage-dataset.component.html',
  styleUrls: ['./manage-dataset.component.css']
})
export class ManageDatasetComponent implements OnInit {

  selectedFiles!: File[];
  message: string = "";

  constructor(private fileUploadService: FileUploadService, public sharedService: SharedService) { }

  ngOnInit() {
    this.message = this.sharedService.getMsg();
  }

  onFileSelected(event: any): void {
    this.selectedFiles = event.target.files.length ? Array.from(event.target.files) : [];
    this.sharedService.setFiles(this.selectedFiles);
  }

  onUpload(): void {
    if (this.selectedFiles && this.selectedFiles.length > 0) {
      this.fileUploadService.upload(this.selectedFiles).subscribe(
        response => {
          this.message = response;
          this.sharedService.setMsg(this.message);
        },
        error => {
          this.message = 'Could not upload the files!'
        }
      );
    }
  }

  onReset(): void {
    if(this.sharedService.getFiles()!= 'Default') {
      this.sharedService.clearData();
      this.fileUploadService.reset().subscribe(
        response => {
          this.message = response;
          this.sharedService.setMsg(this.message);
        },
        error => {
          this.message = 'Could not upload the files!'
        }
      );
    }
  }
}
