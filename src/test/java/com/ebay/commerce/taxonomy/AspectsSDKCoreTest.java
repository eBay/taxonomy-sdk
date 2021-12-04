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

import com.ebay.commerce.taxonomy.data.DataProvider;
import com.ebay.commerce.taxonomy.model.Aspect;
import com.ebay.commerce.taxonomy.model.AspectsDelta;
import com.ebay.commerce.taxonomy.model.BulkAspectResponse;
import com.ebay.commerce.taxonomy.utils.AspectDeltaProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AspectsSDKCoreTest {

    @Autowired
    private DataProvider dataProvider;

    @Autowired
    private AspectDeltaProcessor aspectDeltaProcessor;

    @Test
    public void testAspectsDelta() {
        BulkAspectResponse previousFileData = dataProvider.getBulkAspectMockedData(true);
        BulkAspectResponse currentFileData = dataProvider.getBulkAspectMockedData();
        AspectsDelta actualDelta = aspectDeltaProcessor.process(getIndexedData(previousFileData), getIndexedData(currentFileData));
        AspectsDelta expectedDelta = dataProvider.getExpectedResult();
        ReflectionAssert.assertLenientEquals(expectedDelta, actualDelta);

    }

    private Map<String, List<Aspect>> getIndexedData(BulkAspectResponse bulkAspectResponse) {
        return bulkAspectResponse.getCategoryAspects().stream().
                collect(Collectors.toMap(c -> c.getCategory().getCategoryId(), c -> c.getAspects() != null ? c.getAspects() : new ArrayList<>()));
    }
}
