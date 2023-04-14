package com.semanticdataquery.Util;

import com.semanticdataquery.DTO.SelectQueryResponseDTO;

public interface RDFHelper {

    SelectQueryResponseDTO getResultsFromQuery(String queryString);

}
