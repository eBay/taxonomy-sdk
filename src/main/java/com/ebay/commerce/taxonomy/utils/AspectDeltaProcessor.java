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

import com.ebay.commerce.taxonomy.model.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AspectDeltaProcessor {

    private final String EMPTY_STRING = "";

    public AspectsDelta process (Map<String, List<Aspect>> oldData, Map<String, List<Aspect>> newData) {
        AspectsDelta delta = new AspectsDelta();
        newData.entrySet().parallelStream().forEach(entry -> {
                    if (oldData.containsKey(entry.getKey())) {
                        AspectsResult result = compareAspects(oldData.get(entry.getKey()), entry.getValue());
                        if (isCategoryModified(result)) {
                            delta.addToModifiedCategory(getModifiedCategory(entry.getKey(),result));
                        }
                        oldData.remove(entry.getKey());
                    } else {
                        delta.addToNewCategory(new CategoryAspect() {
                            {
                                setCategory(new Category() {
                                    {
                                        setCategoryId(entry.getKey());
                                        setAspects(entry.getValue());
                                    }
                                });
                            }
                        });
                    }
                }
        );
        delta.setRemovedCategories(new ArrayList<String>(oldData.keySet()));
        return delta;
    }

    private ModifiedCategory getModifiedCategory(String categoryId, AspectsResult result) {
        return new ModifiedCategory() {
            {
                setCategoryId(categoryId);
                setModifiedAspects(result.getModifiedAspects());
                setNewAspects(result.getNewAspects());
                setRemovedAspects(result.getRemovedAspects());
            }
        };
    }

    private boolean isCategoryModified(AspectsResult result) {
        return !isNullOrEmpty(result.getModifiedAspects()) || !isNullOrEmpty(result.getNewAspects()) || !isNullOrEmpty(result.getRemovedAspects());
    }

    private AspectsResult compareAspects(List<Aspect> prevAspects, List<Aspect> currentAspects) {
        Map<String, Aspect> prevAspectMap = prevAspects.stream().collect(Collectors.toMap(c -> c.getLocalizedAspectName(), c -> c));
        Map<String, Aspect> currentAspectMap = currentAspects.stream().collect(Collectors.toMap(c -> c.getLocalizedAspectName(), c -> c));
        AspectsResult result = new AspectsResult();

        currentAspectMap.entrySet().stream().forEach(entry -> {
            if (prevAspectMap.containsKey(entry.getKey())) {
                AspectConstraint modifiedConstraints = getModifiedConstraints(prevAspectMap.get(entry.getKey()).getAspectConstraint(),
                        currentAspectMap.get(entry.getKey()).getAspectConstraint());
                List<String> removedAspectValues = getAspectValuesDifference(prevAspectMap.get(entry.getKey()).getAspectValues(), currentAspectMap.get(entry.getKey()).getAspectValues());
                List<String> newAspectValues = getAspectValuesDifference(currentAspectMap.get(entry.getKey()).getAspectValues(), prevAspectMap.get(entry.getKey()).getAspectValues());
                if(isAspectModified(modifiedConstraints,removedAspectValues,newAspectValues )) {
                    result.addToModifiedAspects(getModifiedAspect(entry.getKey(), modifiedConstraints, removedAspectValues, newAspectValues));
                }
                prevAspectMap.remove(entry.getKey());
            } else {
                result.addToNewAspects(entry.getValue());
            }
        });
        result.setRemovedAspects(new ArrayList<String>(prevAspectMap.keySet()));
        return result;
    }

    private boolean isAspectModified(AspectConstraint modifiedConstraints, List<String> removedAspectValues, List<String> newAspectValues) {
        return (modifiedConstraints!=null || !isNullOrEmpty(removedAspectValues) || !isNullOrEmpty(newAspectValues));
    }

    private ModifiedAspect getModifiedAspect(String aspectName, AspectConstraint modifiedConstraints, List<String> removedAspectValues, List<String> newAspectValues) {
         return new ModifiedAspect() {
                    {
                        setLocalizedAspectName(aspectName);
                        setModifiedConstraint(modifiedConstraints);
                        setRemovedAspectValues(removedAspectValues);
                        setNewAspectValues(newAspectValues);
                    }
                };
    }

     //   TBD: Need to 'reflect' on this method :-/


    private AspectConstraint getModifiedConstraints(AspectConstraint prevConstraint, AspectConstraint currentConstraint) {
        if (currentConstraint == null) return null;
        AspectConstraint constraint = null;
        if (isNotEqual(currentConstraint.getAspectDataType(), prevConstraint.getAspectDataType())) {
            constraint = getAspectConstraint(constraint);
            constraint.setAspectDataType(getWithDefault(currentConstraint.getAspectDataType()));
        }
        if (isNotEqual(currentConstraint.getAspectFormat(), prevConstraint.getAspectFormat())) {
            constraint = getAspectConstraint(constraint);
            constraint.setAspectFormat(getWithDefault(currentConstraint.getAspectFormat()));
        }
        if (isNotEqual(currentConstraint.getAspectMaxLength(), prevConstraint.getAspectMaxLength())) {
            constraint = getAspectConstraint(constraint);
            constraint.setAspectMaxLength(getWithDefault(currentConstraint.getAspectMaxLength()));
        }
        if (isNotEqual(currentConstraint.getAspectMode(), prevConstraint.getAspectMode())) {
            constraint = getAspectConstraint(constraint);
            constraint.setAspectMode( getWithDefault(currentConstraint.getAspectMode()));
        }
        if (isNotEqual(currentConstraint.getAspectUsage(), prevConstraint.getAspectUsage())) {
            constraint = getAspectConstraint(constraint);
            constraint.setAspectUsage( getWithDefault(currentConstraint.getAspectUsage()));
        }
        if (isNotEqual(currentConstraint.isAspectRequired(), prevConstraint.isAspectRequired())) {
            constraint = getAspectConstraint(constraint);
            constraint.setAspectRequired(getWithDefault(currentConstraint.isAspectRequired()));
        }
        if (isNotEqual(currentConstraint.getItemToAspectCardinality(), prevConstraint.getItemToAspectCardinality())) {
            constraint = getAspectConstraint(constraint);
            constraint.setItemToAspectCardinality( getWithDefault(currentConstraint.getItemToAspectCardinality()));
        }
        if (isNotEqual(currentConstraint.getAspectApplicableTo(), prevConstraint.getAspectApplicableTo())) {
            constraint = getAspectConstraint(constraint);
            constraint.setAspectApplicableTo(getWithDefault(currentConstraint.getAspectApplicableTo()));
        }
        if (isNotEqual(currentConstraint.isAspectEnabledForVariations(), prevConstraint.isAspectEnabledForVariations())) {
            constraint = getAspectConstraint(constraint);
            constraint.setAspectEnabledForVariations(getWithDefault(currentConstraint.isAspectEnabledForVariations()));
        }
        if (isNotEqual(currentConstraint.getExpectedRequiredByDate(), prevConstraint.getExpectedRequiredByDate())) {
            constraint = getAspectConstraint(constraint);
            constraint.setExpectedRequiredByDate(getWithDefault(currentConstraint.getExpectedRequiredByDate()));
        }

        return constraint;
    }

    private AspectConstraint getAspectConstraint(AspectConstraint constraint) {
        if(constraint==null)constraint = new AspectConstraint();
        return constraint;
    }

    /*
         Returns x-y
    */
    private List<String> getAspectValuesDifference(List<AspectValue> x, List<AspectValue> y) {
        List<String> xValues = x.stream().map(a -> a.getLocalizedValue()).collect(Collectors.toList());
        List<String> yValues = y.stream().map(a -> a.getLocalizedValue()).collect(Collectors.toList());
        xValues.removeAll(yValues);
        return xValues;
    }
    private <T extends List<?>> boolean isNotEqual(T x, T y) {
        return (!isNullOrEmpty(x) && !isNullOrEmpty(y) && !(x.size()==y.size() && x.containsAll(y)))
                || (isNullOrEmpty(x) && !isNullOrEmpty(y)) || (!isNullOrEmpty(x) && isNullOrEmpty(y));
    }

    private <T> boolean isNotEqual(T x, T y) {
        return (x!=null && y!=null && !y.equals(x))||(x!=null && y==null)||(x==null && y!=null);
    }

    private <T extends Enum> String getWithDefault(T t) {
        return t == null ? EMPTY_STRING : t.name();
    }

    private String getWithDefault(String str) {
        return str == null ? EMPTY_STRING : str;
    }

    private String getWithDefault(Integer integer) {
        return integer == null ? EMPTY_STRING : integer.toString();
    }

    private Boolean getWithDefault(Boolean bool) {
        return bool == null ? Boolean.FALSE : bool;
    }

    private <T extends List<String>> List<String> getWithDefault(T list) {
        if (isNullOrEmpty(list)) return new ArrayList<>();
        return list;
    }

    private boolean isNullOrEmpty(final Collection<?> c) {
        return c == null || c.isEmpty();
    }

    private boolean isNullOrEmpty(final Map<?, ?> m) {
        return m == null || m.isEmpty();
    }

}
