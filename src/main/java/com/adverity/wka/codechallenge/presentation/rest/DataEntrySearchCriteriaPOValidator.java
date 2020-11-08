package com.adverity.wka.codechallenge.presentation.rest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.adverity.wka.codechallenge.presentation.rest.model.DataEntrySearchCriteriaPO;
import com.adverity.wka.codechallenge.presentation.rest.model.DimensionFilterPO;

/**
 * @author Wojciech Kaczmarek
 */
@Component
class DataEntrySearchCriteriaPOValidator {

    public void validate(DataEntrySearchCriteriaPO criteria) {
        if (!CollectionUtils.isEmpty(criteria.getAggregateOn()) && criteria.getAggregateOn().contains(null)) {
            throw new IllegalArgumentException(
                    "Invalid metric on the list of aggregate on! Expected one of: " + Arrays.toString(DataEntrySearchCriteriaPO.AggregateOnEnum.values()));
        }
        if (!CollectionUtils.isEmpty(criteria.getGroupBy()) && criteria.getGroupBy().contains(null)) {
            throw new IllegalArgumentException(
                    "Invalid dimension on the list of group by! Expected one of: " + Arrays.toString(DataEntrySearchCriteriaPO.GroupByEnum.values()));
        }
        if (!CollectionUtils.isEmpty(criteria.getFilterBy())) {
            List<DimensionFilterPO.NameEnum> filterNames = criteria.getFilterBy().stream()
                    .map(DimensionFilterPO::getName)
                    .collect(Collectors.toList());
            if (filterNames.contains(null)) {
                throw new IllegalArgumentException(
                        "Invalid dimension on the list of filter by! Expected one of: " + Arrays.toString(DimensionFilterPO.NameEnum.values()));
            }
        }
    }

}
