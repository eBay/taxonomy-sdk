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

public class Aspect {

    private String localizedAspectName;

    private AspectConstraint aspectConstraint;

    private List<AspectValue> aspectValues = new ArrayList<AspectValue>();


    /**
     * @return the aspectName
     *//*
	public String getAspectName() {
		return aspectName;
	}


	*//**
     * @param aspectName the aspectName to set
     *//*
	public void setAspectName(String aspectName) {
		this.aspectName = aspectName;
	}
*/

    /**
     * @return the localizedAspectName
     */
    public String getLocalizedAspectName() {
        return localizedAspectName;
    }


    /**
     * @param localizedAspectName the localizedAspectName to set
     */
    public void setLocalizedAspectName(String localizedAspectName) {
        this.localizedAspectName = localizedAspectName;
    }


    /**
     * @return the aspectConstraint
     */
    public AspectConstraint getAspectConstraint() {
        return aspectConstraint;
    }


    /**
     * @param aspectConstraint the aspectConstraint to set
     */
    public void setAspectConstraint(AspectConstraint aspectConstraint) {
        this.aspectConstraint = aspectConstraint;
    }


    /**
     * @return the aspectValues
     */
    public List<AspectValue> getAspectValues() {
        return aspectValues;
    }


    /**
     * @param aspectValues the aspectValues to set
     */
    public void setAspectValues(List<AspectValue> aspectValues) {
        this.aspectValues = aspectValues;
    }


}
