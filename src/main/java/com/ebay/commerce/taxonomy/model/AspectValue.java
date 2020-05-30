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

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)

public class AspectValue {

    private String localizedValue;

  // List<ValueConstraint> valueConstraints = new ArrayList<ValueConstraint>();


    /**
     * @return the localizedValue
     */
    public String getLocalizedValue() {
        return localizedValue;
    }

    /**
     * @param localizedValue the localizedValue to set
     */
    public void setLocalizedValue(String localizedValue) {
        this.localizedValue = localizedValue;
    }
/*

    */
/**
     * @return the valueConstraints
     *//*

    public List<ValueConstraint> getValueConstraints() {
        return valueConstraints;
    }

    */
/**
     * @param valueConstraints the valueConstraints to set
     *//*

    public void setValueConstraints(List<ValueConstraint> valueConstraints) {
        this.valueConstraints = valueConstraints;
    }
*/

}