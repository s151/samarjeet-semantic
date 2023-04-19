package com.semanticdataquery.Util.Impl;

import com.semanticdataquery.Constants.AppConstants;
import com.semanticdataquery.DTO.SelectQueryResponseDTO;
import com.semanticdataquery.Util.RDFHelper;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;

import java.util.ArrayList;
import java.util.List;

public class RDFHelperImpl implements RDFHelper {

    static Model model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

    static {
        model.read(AppConstants.OWL_FILE_NAME, "RDF/XML");
    }

    public SelectQueryResponseDTO processSelectQuery(String queryString) {
        SelectQueryResponseDTO responseDTO = new SelectQueryResponseDTO();

        try (QueryExecution queryExecution = QueryExecutionFactory.create(queryString, model)) {
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

        return responseDTO;
    }

    public Boolean processAskQuery(String queryString) {
        try (QueryExecution queryExecution = QueryExecutionFactory.create(queryString, model)) {
            return queryExecution.execAsk();
        }
    }
}
