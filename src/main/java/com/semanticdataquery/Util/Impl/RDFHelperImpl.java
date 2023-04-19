package com.semanticdataquery.Util.Impl;

import com.semanticdataquery.Constants.AppConstants;
import com.semanticdataquery.DTO.SelectQueryResponseDTO;
import com.semanticdataquery.Util.RDFHelper;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RDFHelperImpl implements RDFHelper {

    static Model model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

    static {
        model.read(AppConstants.OWL_FILE_NAME, "RDF/XML");
    }

    public ResponseEntity<SelectQueryResponseDTO> processSelectQuery(String queryString) {
        SelectQueryResponseDTO responseDTO = new SelectQueryResponseDTO();

        Query query = QueryFactory.create(queryString);
        if (!query.isSelectType()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, model)) {

            ResultSet results = queryExecution.execSelect();
            responseDTO.setHeaders(results.getResultVars());
            while (results.hasNext()) {
                List<String> row = new ArrayList<>();
                QuerySolution qs = results.nextSolution();
                results.getResultVars().forEach( var ->
                        row.add(String.valueOf(qs.get(var)))
                );
                responseDTO.addRow(row);
            }
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    public ResponseEntity<Boolean> processAskQuery(String queryString) {
        Query query = QueryFactory.create(queryString);
        if (!query.isAskType()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, model)) {
            return new ResponseEntity<>(queryExecution.execAsk(), HttpStatus.OK);
        }
    }

    public ResponseEntity<String> processConstructQuery(String queryString) {
        Query query = QueryFactory.create(queryString);
        if (!query.isConstructType()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, model)) {
            Model results = queryExecution.execConstruct();
            StringWriter sw = new StringWriter();
            try (BufferedWriter out = new BufferedWriter(sw)) {
                results.write(out);
                out.flush();
                return new ResponseEntity<>(sw.toString(), HttpStatus.OK);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    public ResponseEntity<String> processDescribeQuery(String queryString) {
//        try (QueryExecution queryExecution = QueryExecutionFactory.create(queryString, model)) {
//            return queryExecution.execDescribe();
//        }
//    }
}
