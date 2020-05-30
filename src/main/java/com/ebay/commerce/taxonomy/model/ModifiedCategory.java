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

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)

public class ModifiedCategory {

    private String  categoryId;
    private List<ModifiedAspect> modifiedAspects = new ArrayList<ModifiedAspect>();
    private List<String> removedAspects;
    private List<Aspect> newAspects;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<ModifiedAspect> getModifiedAspects() {
        return modifiedAspects;
    }

    public void setModifiedAspects(List<ModifiedAspect> modifiedAspects) {
        this.modifiedAspects = modifiedAspects;
    }

    public void addToModifiedAspects(ModifiedAspect modifiedAspect) {
        modifiedAspects.add(modifiedAspect);
    }

    public List<String> getRemovedAspects() {
        return removedAspects;
    }

    public void setRemovedAspects(List<String> removedAspects) {
        this.removedAspects = removedAspects;
    }

    public List<Aspect> getNewAspects() {
        return newAspects;
    }

    public void setNewAspects(List<Aspect> newAspects) {
        this.newAspects = newAspects;
    }
}
