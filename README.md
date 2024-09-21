## About The Project

This is the backend microservice for the search system built with Spring Boot and Apache Jena. It queries semantic datasets using SPARQL and provides the necessary API endpoints for the Angular front-end to retrieve results.

## Datasets

### We used 3 open datasets - placed in resources folder.

- Housing.owl generated via code from [Zillow CSV, containing the monthly house price index of different neighbourhoods in LA, from 2010 to present](https://files.zillowstatic.com/research/public_csvs/zhvi/Metro_zhvi_uc_sfrcondo_tier_0.33_0.67_sm_sa_month.csv?t=1677524930)
- [RDF from data.lacity.org, containing crime records of LA, from 2010 to 2019](https://data.lacity.org/Public-Safety/Crime-Data-from-2010-to-2019/63jg-8b9z)
- [RDF from data.lacity.org, containing crime records of LA, from 2020 to present](https://data.lacity.org/Public-Safety/Crime-Data-from-2020-to-Present/2nrs-mtv8)


## Technologies

- Apache Jena
- SpringBoot


## Getting Started

### Prerequisites
- Java 11+
- Maven

### Step 1: Clone the repository

### Step 2: Use below maven command to start the springboot microservive

`mvn spring-boot:run`
