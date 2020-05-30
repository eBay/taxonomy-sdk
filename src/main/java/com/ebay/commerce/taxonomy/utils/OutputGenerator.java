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

import com.ebay.commerce.taxonomy.constants.Constants;
import com.ebay.commerce.taxonomy.exceptions.WriteToOutputException;
import com.ebay.commerce.taxonomy.model.AspectsDelta;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class OutputGenerator {

    ObjectWriter writer = new ObjectMapper().writer(new DefaultPrettyPrinter());

    public void write(AspectsDelta delta, String directory) {
        try {
            writer.writeValue(new File(directory + Constants.OUTPUT_FILE_NAME), delta);
        } catch (IOException e) {
            throw new WriteToOutputException(String.format("Output generation failed while writing to %s ", directory+Constants.OUTPUT_FILE_NAME),e);
        }

    }

}
