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

package com.ebay.commerce.taxonomy.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)


public class BulkAspectResponse {

    private String categoryTreeId;

    private String categoryTreeVersion;

    private List<CategoryAspect> categoryAspects;


    /**
     * @return the categoryTreeId
     */
    public String getCategoryTreeId() {
        return categoryTreeId;
    }

    /**
     * @param categoryTreeId the categoryTreeId to set
     */
    public void setCategoryTreeId(String categoryTreeId) {
        this.categoryTreeId = categoryTreeId;
    }

    /**
     * @return the categoryTreeVersion
     */
    public String getCategoryTreeVersion() {
        return categoryTreeVersion;
    }

    /**
     * @param categoryTreeVersion the categoryTreeVersion to set
     */
    public void setCategoryTreeVersion(String categoryTreeVersion) {
        this.categoryTreeVersion = categoryTreeVersion;
    }

    /**
     * @return the categoryAspects
     */
    public List<CategoryAspect> getCategoryAspects() {
        return categoryAspects;
    }

    /**
     * @param categoryAspects the categoryAspects to set
     */
    public void setCategoryAspects(List<CategoryAspect> categoryAspects) {
        this.categoryAspects = categoryAspects;
    }

}