package com.semanticdataquery.Constants;

import java.util.ArrayList;
import java.util.List;

public class AppConstants {

    public static final String CRIME_RDF_FILE_1 = "crime_2010_to_2019.rdf";
    public static final String CRIME_RDF_FILE_2 = "crime_2020_to_present.rdf";
    public static final String HOUSING_OWL_FILE = "Housing.owl";

    public static final List<String> DEFAULT_FILES = new ArrayList<>(){
            {
                add(AppConstants.CRIME_RDF_FILE_1);
                add(AppConstants.CRIME_RDF_FILE_2);
                add(AppConstants.HOUSING_OWL_FILE);
            }
    };

}
