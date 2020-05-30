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
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)

public class ModifiedAspect {

    private String localizedAspectName;

    private AspectConstraint modifiedConstraint;

    private List<String> newAspectValues;

    private List<String> removedAspectValues;

    public AspectConstraint getModifiedConstraint() {
        return modifiedConstraint;
    }

    public void setModifiedConstraint(AspectConstraint modifiedConstraint) {
        this.modifiedConstraint = modifiedConstraint;
    }

    public List<String> getNewAspectValues() {
        return newAspectValues;
    }

    public void setNewAspectValues(List<String> newAspectValues) {
        this.newAspectValues = newAspectValues;
    }

    public List<String> getRemovedAspectValues() {
        return removedAspectValues;
    }

    public void setRemovedAspectValues(List<String> removedAspectValues) {
        this.removedAspectValues = removedAspectValues;
    }

    public String getLocalizedAspectName() {
        return localizedAspectName;
    }

    public void setLocalizedAspectName(String localizedAspectName) {
        this.localizedAspectName = localizedAspectName;
    }
}
