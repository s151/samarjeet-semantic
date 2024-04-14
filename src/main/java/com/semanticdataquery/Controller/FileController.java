package com.semanticdataquery.Controller;

import com.semanticdataquery.Constants.AppConstants;
import com.semanticdataquery.Util.Impl.RDFHelperImpl;
import com.semanticdataquery.Util.RDFHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "http://localhost:4200")
public class FileController {

    RDFHelper rdfHelper = new RDFHelperImpl();

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        List<String> filenames = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                filenames.add(filename);
            }
            rdfHelper.initModel(filenames);
            return ResponseEntity.ok("Files uploaded successfully: " + filenames);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload files: " + e.getMessage());
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetFile() {
        try {
            rdfHelper.initModel(AppConstants.DEFAULT_FILES);
            return ResponseEntity.ok("File Reset successful");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File Reset failed. Try again." + e.getMessage());
        }

    }
}
