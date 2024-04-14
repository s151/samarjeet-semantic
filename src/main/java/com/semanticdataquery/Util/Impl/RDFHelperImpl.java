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

    // create model
    static Model model = null;

    public void initModel(List<String> files){
        model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        if (files.size()>=1) {
            model.read(files.get(0), "RDF/XML");
            for (int i =1; i<files.size(); i++) {
                model.add(model.read(files.get(i), "RDF/XML"));
            }
        }
    }

    void checkModel() {
        if (model == null) {
            initModel(AppConstants.DEFAULT_FILES);
        }
    }

    public ResponseEntity<SelectQueryResponseDTO> processSelectQuery(String queryString) {
        checkModel();
        SelectQueryResponseDTO responseDTO = new SelectQueryResponseDTO();

        Query query = QueryFactory.create(queryString);
        // check if the given query is of select type, otherwise return 400
        if (!query.isSelectType()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, model)) {

            ResultSet results = queryExecution.execSelect();
            responseDTO.setHeaders(results.getResultVars());
            // iterate through the query results and add to the responseDTO
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
        checkModel();
        Query query = QueryFactory.create(queryString);
        // check if the given query is of ask type, otherwise return 400
        if (!query.isAskType()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, model)) {
            return new ResponseEntity<>(queryExecution.execAsk(), HttpStatus.OK);
        }
    }

    public ResponseEntity<String> processConstructQuery(String queryString) {
        checkModel();
        Query query = QueryFactory.create(queryString);
        // check if the given query is of construct type, otherwise return 400
        if (!query.isConstructType()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, model)) {
            Model results = queryExecution.execConstruct();
            return new ResponseEntity<>(getResultStringFromModel(results), HttpStatus.OK);
        }
    }

    public ResponseEntity<String> processDescribeQuery(String queryString) {
        checkModel();
        Query query = QueryFactory.create(queryString);
        // check if the given query is of describe type, otherwise return 400
        if (!query.isDescribeType()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, model)) {
            Model results = queryExecution.execDescribe();
            return new ResponseEntity<>(getResultStringFromModel(results), HttpStatus.OK);
        }
    }


    /**
     * @param model - results from describe/construct query
     * @return String - model in RDF/XML format
     */
    private String getResultStringFromModel(Model model) {
        StringWriter sw = new StringWriter();
        try (BufferedWriter out = new BufferedWriter(sw)) {
            model.write(out);
            out.flush();
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
