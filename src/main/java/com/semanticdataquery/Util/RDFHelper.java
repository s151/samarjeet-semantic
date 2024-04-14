package com.semanticdataquery.Util;

import com.semanticdataquery.DTO.SelectQueryResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RDFHelper {

    ResponseEntity<SelectQueryResponseDTO> processSelectQuery(String queryString);

    ResponseEntity<Boolean> processAskQuery(String queryString);

    ResponseEntity<String> processConstructQuery(String queryString);

    ResponseEntity<String> processDescribeQuery(String queryString);

    void initModel(List<String> files);

}
