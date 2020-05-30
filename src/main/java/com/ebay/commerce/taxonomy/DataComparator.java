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

package com.ebay.commerce.taxonomy;

import com.ebay.commerce.taxonomy.constants.Constants;
import com.ebay.commerce.taxonomy.model.AspectsDelta;
import com.ebay.commerce.taxonomy.exceptions.ClientException;
import com.ebay.commerce.taxonomy.context.ExecutionContext;
import com.ebay.commerce.taxonomy.exceptions.DownloadException;
import com.ebay.commerce.taxonomy.exceptions.FileReadException;
import com.ebay.commerce.taxonomy.model.Aspect;
import com.ebay.commerce.taxonomy.utils.AspectDeltaProcessor;
import com.ebay.commerce.taxonomy.utils.DataLoader;
import com.ebay.commerce.taxonomy.utils.OutputGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
public class DataComparator {
    @Autowired
    private DataLoader dataLoader;

    @Autowired
    private OutputGenerator outputGenerator;

    @Autowired
    private AspectDeltaProcessor aspectDeltaProcessor;

    private Logger logger = LoggerFactory.getLogger(DataComparator.class);

    public void compare() {
        if (!ExecutionContext.executionContextInitialized()) {
            return;
        }
        try {
            CompletableFuture<Map<String, List<Aspect>>> completableOld = dataLoader.loadBulkDataFromFile(ExecutionContext.previousFileName());
            CompletableFuture<Map<String, List<Aspect>>> completableNew = ExecutionContext.useLatest() ?
                    dataLoader.downloadLatest(ExecutionContext.environment(), ExecutionContext.categoryTreeId(), ExecutionContext.bearerToken()):
                    dataLoader.loadBulkDataFromFile(ExecutionContext.currentFileName());
            CompletableFuture.allOf(completableOld, completableNew).join();
            logger.info("Data load completed. Initiating analysis...");
            AspectsDelta delta = aspectDeltaProcessor.process(completableOld.get(), completableNew.get());
            logger.info("Data analysis completed. Writing to file " + ExecutionContext.outputDirectory() + Constants.OUTPUT_FILE_NAME);
            outputGenerator.write(delta, ExecutionContext.outputDirectory());
            logger.info("Processing completed");
        } catch (ClientException | CompletionException | DownloadException | FileReadException  ce) {
            logger.error(ce.getMessage());
        } catch (Exception ex) {
            // Deliberate Catch all - Should not get to this block
            logger.error("Processing terminated with error:", ex);
        } finally {
            ExecutionContext.reset();
        }
    }


}
