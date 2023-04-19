package com.semanticdataquery.Util;

import com.semanticdataquery.DTO.SelectQueryResponseDTO;
import org.apache.jena.rdf.model.Model;
import org.springframework.http.ResponseEntity;

public interface RDFHelper {

    ResponseEntity<SelectQueryResponseDTO> processSelectQuery(String queryString);

    ResponseEntity<Boolean> processAskQuery(String queryString);

    Boolean processAskQuery(String queryString);

}
