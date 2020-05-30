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

import com.ebay.commerce.taxonomy.model.ModifiedAspect;
import com.ebay.commerce.taxonomy.model.Aspect;

import java.util.ArrayList;
import java.util.List;

public class AspectsResult {

    private List<Aspect> newAspects = new ArrayList<Aspect>();
    private List<String> removedAspects = new ArrayList<String>();
    private List<ModifiedAspect> modifiedAspects = new ArrayList<ModifiedAspect>();

    public List<Aspect> getNewAspects() {
        return newAspects;
    }

    public void setNewAspects(List<Aspect> newAspects) {
        this.newAspects = newAspects;
    }

    public List<String> getRemovedAspects() {
        return removedAspects;
    }

    public void setRemovedAspects(List<String> removedAspects) {
        this.removedAspects = removedAspects;
    }

    public List<ModifiedAspect> getModifiedAspects() {
        return modifiedAspects;
    }

    public void setModifiedAspects(List<ModifiedAspect> modifiedAspects) {
        this.modifiedAspects = modifiedAspects;
    }

    public void addToNewAspects(Aspect newAspect) {
        this.newAspects.add(newAspect);
    }

    public void addToModifiedAspects(ModifiedAspect aspect) {
        this.modifiedAspects.add(aspect);
    }

    public void addToRemovedAspects(String aspectName) {
        this.removedAspects.add(aspectName);
    }
}
