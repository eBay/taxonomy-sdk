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

package com.ebay.commerce.taxonomy.context;

import com.ebay.commerce.taxonomy.constants.Environment;

public class Control {

    private String prevFileName;
    private String currentFileName;
    private String outputDirectory;
    private Boolean useLatest;
    private String accessToken;
    private String categoryTreeId;
    private Environment environment;

    public Control(String env, String catTreeId, String prev, String current, String output, Boolean latest, String token) {
        environment = getEnvironmentEnumWithDefault(env);
        categoryTreeId = catTreeId;
        prevFileName = prev;
        currentFileName= current;
        outputDirectory = output;
        useLatest = latest;
        accessToken = token;

    }

    private Environment getEnvironmentEnumWithDefault(String env) {
        try {
            return Environment.valueOf(env.toUpperCase());
        } catch(Exception e) {
            // noop
        }
        return Environment.PRODUCTION;
    }

    public Boolean useLatest() {
        return useLatest;
    }

    public String previousFileName() {
        return prevFileName;
    }

    public String currentFileName() {
        return currentFileName;
    }

    public String outputDirectory() {
        return outputDirectory;
    }

    public String bearerToken() {
        return "bearer "+accessToken;
    }

    public String categoryTreeId() {
        return categoryTreeId;
    }

    public Environment environment() {
        return environment;
    }
}
