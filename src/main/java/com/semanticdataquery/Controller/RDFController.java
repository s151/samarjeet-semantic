package com.semanticdataquery.Controller;

import com.semanticdataquery.DTO.SelectQueryResponseDTO;
import com.semanticdataquery.Util.Impl.RDFHelperImpl;
import com.semanticdataquery.Util.RDFHelper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/query")
@CrossOrigin(origins = "http://localhost:4200")
public class RDFController {

    RDFHelper rdfHelper = new RDFHelperImpl();

    @PostMapping("/select")
    public SelectQueryResponseDTO processSelectQuery(@RequestBody String queryString) {
        return rdfHelper.processSelectQuery(queryString);
    }

    @PostMapping("/ask")
    public Boolean processAskQuery(@RequestBody String queryString) {
        return rdfHelper.processAskQuery(queryString);
    }
}
