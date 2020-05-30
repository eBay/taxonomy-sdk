/*
 *  Copyright (c) 2020 eBay Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.ebay.commerce.taxonomy.data;

import com.ebay.commerce.taxonomy.model.AspectsDelta;
import com.ebay.commerce.taxonomy.model.BulkAspectResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class DataProvider {

    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String PREVIOUS_DATA = "src/test/java/com/ebay/commerce/taxonomy/data/previous.json";
    private static final String CURRENT_DATA = "src/test/java/com/ebay/commerce/taxonomy/data/current.json";
    private static final String EXPECTED_RESULTS = "src/test/java/com/ebay/commerce/taxonomy/data/aspects_difference.json";


    public BulkAspectResponse getBulkAspectMockedData() {
        return getBulkAspectMockedData(false);
    }
    public BulkAspectResponse getBulkAspectMockedData(Boolean previous) {
        try {
            String dataFile = previous == Boolean.TRUE?PREVIOUS_DATA:CURRENT_DATA;
            return objectMapper.readValue(readFile(dataFile),
                    BulkAspectResponse.class);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AspectsDelta getExpectedResult() {
        try {

            return objectMapper.readValue(readFile(EXPECTED_RESULTS),
                    AspectsDelta.class);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }
}
