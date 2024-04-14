import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  constructor(private http: HttpClient) { }

  upload(files: File[]) {
    const formData: FormData = new FormData();
    files.forEach(file => {
      formData.append('files', file, file.name);
    });

    return this.http.post('http://localhost:8080/file/upload', formData, {
      responseType: 'text'
    });
  }

  reset() {
    return this.http.post('http://localhost:8080/file/reset', {},{
      responseType: 'text'
    });
  }
}
