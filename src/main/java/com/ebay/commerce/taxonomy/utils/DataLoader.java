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

package com.ebay.commerce.taxonomy.utils;

import com.ebay.commerce.taxonomy.client.BulkAspectClient;
import com.ebay.commerce.taxonomy.constants.Environment;
import com.ebay.commerce.taxonomy.exceptions.DownloadException;
import com.ebay.commerce.taxonomy.exceptions.FileReadException;
import com.ebay.commerce.taxonomy.model.Aspect;
import com.ebay.commerce.taxonomy.model.BulkAspectResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;


@Component
public class DataLoader {

    @Autowired
    private BulkAspectClient client;

    private ObjectMapper mapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Map<String, List<Aspect>>> loadBulkDataFromFile(String fileName) {
        logger.info("Loading data from file "+fileName);
        Map<String, List<Aspect>> responseMap = null;
        try (InputStream fileInputStream = new FileInputStream(fileName);
        ) {
            responseMap = loadDataFromInStream(fileInputStream);
            logger.info(String.format("Categories loaded for fileName %s: %s", fileName, responseMap.keySet().size()));
        } catch (IOException ex) {
            throw new FileReadException(String.format("Exception while processing file %s", fileName), ex);
        }
        return CompletableFuture.completedFuture(responseMap);
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Map<String, List<Aspect>>> downloadLatest(Environment environment, String categoryTreeId, String token) {
        logger.info("Loading latest data for categoryTreeId "+categoryTreeId);
        Map<String, List<Aspect>> responseMap = null;
        try (InputStream inputStream = client.getDataStream(environment, categoryTreeId, token);
        ) {
            responseMap = loadDataFromInStream(inputStream);
            logger.info(String.format("Categories loaded for category_tree_id %s is %s", categoryTreeId, responseMap.keySet().size()));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new DownloadException(String.format("Exception while downloading category treeId %s on %s", categoryTreeId,environment.name()));
        }
        return CompletableFuture.completedFuture(responseMap);
    }

    private Map<String, List<Aspect>> loadDataFromInStream(InputStream inputStream) throws IOException {
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            InputStreamReader reader = new InputStreamReader(gzipInputStream);
        ) {
            return getCategoryIdAspectMap(reader);
        }
    }

    private Map<String, List<Aspect>> getCategoryIdAspectMap(InputStreamReader reader) throws IOException {
        BulkAspectResponse response = mapper.readValue(reader, BulkAspectResponse.class);
        return response.getCategoryAspects().
                stream().
                collect(Collectors.toMap(c -> c.getCategory().getCategoryId(), c -> c.getAspects() != null ? c.getAspects() : new ArrayList<Aspect>()));
    }

}
