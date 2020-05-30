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

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)


public class AspectConstraint {

    private String aspectDataType;

    private String aspectFormat;

    private String itemToAspectCardinality;

    private String aspectMode;

    private Boolean aspectRequired;

    private String aspectUsage;

    private Boolean aspectEnabledForVariations;

    private String aspectMaxLength;

    private String expectedRequiredByDate;

    private List<String> aspectApplicableTo;

    /**
     * @return the aspectDataType
     */
    public String getAspectDataType() {
        return aspectDataType;
    }

    /**
     * @param aspectDataType the aspectDataType to set
     */
    public void setAspectDataType(String aspectDataType) {
        this.aspectDataType = aspectDataType;
    }

    /**
     * @return the aspectFormat
     */
    public String getAspectFormat() {
        return aspectFormat;
    }

    /**
     * @param aspectFormat the aspectFormat to set
     */
    public void setAspectFormat(String aspectFormat) {
        this.aspectFormat = aspectFormat;
    }

    /**
     * @return the itemToAspectCardinality
     */
    public String getItemToAspectCardinality() {
        return itemToAspectCardinality;
    }

    /**
     * @param itemToAspectCardinality the itemToAspectCardinality to set
     */
    public void setItemToAspectCardinality(
            String itemToAspectCardinality) {
        this.itemToAspectCardinality = itemToAspectCardinality;
    }

    /**
     * @return the aspectMode
     */
    public String getAspectMode() {
        return aspectMode;
    }

    /**
     * @param aspectMode the aspectMode to set
     */
    public void setAspectMode(String aspectMode) {
        this.aspectMode = aspectMode;
    }

    /**
     * @return the aspectRequired
     */
    public Boolean isAspectRequired() {
        return aspectRequired;
    }

    /**
     * @param aspectRequired the aspectRequired to set
     */
    public void setAspectRequired(Boolean aspectRequired) {
        this.aspectRequired = aspectRequired;
    }

    public String getAspectUsage() {
        return aspectUsage;
    }

    public void setAspectUsage(String aspectUsage) {
        this.aspectUsage = aspectUsage;
    }

    /**
     * @return the aspectEnabledForVariations
     */
    public Boolean isAspectEnabledForVariations() {
        return aspectEnabledForVariations;
    }

    /**
     * @param aspectEnabledForVariations the aspectEnabledForVariations to set
     */
    public void setAspectEnabledForVariations(Boolean aspectEnabledForVariations) {
        this.aspectEnabledForVariations = aspectEnabledForVariations;
    }

    public String getAspectMaxLength() {
        return aspectMaxLength;
    }

    public void setAspectMaxLength(String aspectMaxLength) {
        this.aspectMaxLength = aspectMaxLength;
    }

    public String getExpectedRequiredByDate() {
        return expectedRequiredByDate;
    }

    public void setExpectedRequiredByDate(String expectedRequiredByDate) {
        this.expectedRequiredByDate = expectedRequiredByDate;
    }

    public List<String> getAspectApplicableTo() {
        return aspectApplicableTo;
    }

    public void setAspectApplicableTo(
            List<String> aspectApplicableTo) {
        this.aspectApplicableTo = aspectApplicableTo;
    }
}