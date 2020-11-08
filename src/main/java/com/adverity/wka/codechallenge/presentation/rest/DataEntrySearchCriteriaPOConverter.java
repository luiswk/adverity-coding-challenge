package com.adverity.wka.codechallenge.presentation.rest;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.adverity.wka.codechallenge.domain.DataEntrySearchCriteria;
import com.adverity.wka.codechallenge.presentation.rest.model.DataEntrySearchCriteriaPO;

/**
 * @author Wojciech Kaczmarek
 */
@Component
class DataEntrySearchCriteriaPOConverter {

    private static final int DEFAULT_PAGE_SIZE = 20;

    public DataEntrySearchCriteria convert(DataEntrySearchCriteriaPO from) {
        return DataEntrySearchCriteria.builder()
                .from(from.getFromDate())
                .to(from.getToDate())
                .aggregateOnMetrics(Optional.ofNullable(from.getAggregateOn())
                        .map(col -> col.stream()
                                .filter(Objects::nonNull)
                                .map(DataEntrySearchCriteriaPO.AggregateOnEnum::toString)
                                .collect(Collectors.toSet()))
                        .orElse(Collections.emptySet()))
                .groupByDimensions(Optional.ofNullable(from.getGroupBy())
                        .map(col -> col.stream()
                                .filter(Objects::nonNull)
                                .map(DataEntrySearchCriteriaPO.GroupByEnum::toString)
                                .collect(Collectors.toSet()))
                        .orElse(Collections.emptySet()))
                .filterByDimensions(Optional.ofNullable(from.getFilterBy())
                        .map(col -> col.stream()
                                .filter(f -> f.getName() != null)
                                .filter(f -> !CollectionUtils.isEmpty(f.getValues()))
                                .collect(Collectors.toMap(f -> f.getName().toString(), f -> Set.copyOf(f.getValues()))))
                        .orElse(Collections.emptyMap()))
                .pageNumber(Optional.ofNullable(from.getPageNumber()).orElse(0))
                .pageSize(Optional.ofNullable(from.getPageSize()).orElse(DEFAULT_PAGE_SIZE))
                .build();
    }

}
