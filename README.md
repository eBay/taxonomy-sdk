Taxonomy Metadata SDK &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    [![Build Status](https://travis-ci.org/eBay/taxonomy-sdk.svg?branch=master)](https://travis-ci.org/eBay/taxonomy-sdk)
==========
A simple springboot CLI SDK that does a deep comparison of aspects metadata and reports changes in a structured manner. 

Table of contents
==========
* [What is aspects?](#aspects)
* [Motivation](#motivation)
* [Usage](#usage)
* [Output](#output)
* [Logging](#logging)
* [License](#license)

# Aspects

Aspect identifies an item attribute (for example, color) for which the seller will be required or encouraged to provide a value (or variation values) when offering an item in that category on eBay. Each eBay category has its set of predefined aspects. Aspects metadata also include constraints that define behavior and value recommendations.

# Motivation

eBay's Category Aspects metadata evolves fairly rapidly. New aspects may be added, existing ones removed or modified, new aspect values may be introduced or existing values removed. Also newer categories may have been added or existing ones modified or removed.  There is a consequent challenge of keeping this fast evolving aspects metadata in sync and gaining quick insights into what might have changed. 

In May 2020 eBay introduced the capability to bulk download all aspects metadata for a marketplace or more specifically a category tree. 

This SDK is designed to aid users with insights on changes leveraging the bulk download feature for aspects.  The output is a precise structured response to address the following scenarios:

* What has changed between a previous bulk data _**file A**_ and bulk data _**file B**_ downloaded recently
* My cache was last synced with file A. Compare A **_to the latest on the site_** and report differences

The output will show without any ambiguity: 

* What is new 
* What is modified 
* What is removed

The above includes Categories, Aspects and associated information (i.e aspect constraints & values) 

# Usage

Prerequisites
```
maven: version 3.5.0 (or later)
jdk: 8

```
Install
```
mvn clean install 
```
Run
```
java -jar target/taxonomy-metadata-sdk-1.0.0-RELEASE.jar -h

```

Following options are suported:

| Option        | Long form     | Description  |
| ------------- |:-------------:| :-------|
| ct      | category_tree_id |The category tree Id for the data being compared (Not validated. Optional for file compare) |
| p      | previous_file      |   The previous .gz file to be used for comparison |
| c      | current_file      |   The current .gz file to be used for comparison (optional if option 'l' is used) |
| o      | out      |   The path to output directory for the result |
| l      | latest    |   This option ignores current_file and attepts to fetch the latest |
| e      | environment |   The environment. Supported values: SANDBOX or PRODUCTION. Default: PRODUCTION |
| cc    | client_config_file      |  Path to client configurations containing oauth credentials for the environment.|
| t      | token      |   An override option to pass oauth token directly for the environment (for option 'l').|
| h      | help      |   Standard usage help.|

#### Examples:

Get Help:
```
java -jar target/taxonomy-metadata-sdk-1.0.0-RELEASE.jar -h
```
Compare downloaded file A and B and write output to directory Z: 
```
java -jar target/taxonomy-metadata-sdk-1.0.0-RELEASE.jar --previous_file={pathToFileA} --current_file={pathToFileB} --out={pathToOutputDirectory}
```
Compare file A with the latest on the site using my client credentials for oauth and write output to directory Z: 
```
java -jar target/taxonomy-metadata-sdk-1.0.0-RELEASE.jar --latest --previous_file={pathToFileA} --out={pathToOutputDirectory} --client_config_file={pathToClientCredentials}
```
Compare file A with the latest on the site using this valid client credentials oauth token and write output to directory Z:
```
java -jar target/taxonomy-metadata-sdk-1.0.0-RELEASE.jar --latest --previous_file={pathToFileA} --out={pathToOutputDirectory} --token={token}
```
Client Credentials Configuration Sample: [ebay-config.yaml](samples/ebay-config.yaml)

## Output 

Schema: [OutputSchema.json](schema/OutputSchema.json)

Note that for the sake of brevity and precision, the SDK is designed to surface differnces only. So: 

#### Constraint differences example: 

If aspect 'brand' was OPTIONAL and was modified to RECOMMENDED - all other constraints being the same, the result will show as follows:
```
 "modifiedAspects" : [ {
      "localizedAspectName" : "Brand",
      "modifiedConstraint" : {
        "aspectUsage" : "RECOMMENDED"
      }
      ...
```

#### New, Removed and modified:

The SDK clearly surfaces new, modified and removed entities as the [test sample](src/test/java/com/ebay/commerce/taxonomy/data/aspects_difference.json) shows.  


## Logging

Uses standard slf4j console logging. 


## License

Copyright 2020 eBay Inc.  
Developer: Sekhar Banerjee

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
