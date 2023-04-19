package com.semanticdataquery.Util;

import com.semanticdataquery.DTO.SelectQueryResponseDTO;

public interface RDFHelper {

    SelectQueryResponseDTO processSelectQuery(String queryString);

    Boolean processAskQuery(String queryString);

}
