package com.adverity.wka.codechallenge.domain;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Wojciech Kaczmarek
 */
@Builder
@Getter
public class DataEntrySearchCriteria {

    private final LocalDate from;
    private final LocalDate to;

    private final Set<String> aggregateOnMetrics;
    private final Set<String> groupByDimensions;
    private final Map<String, Set<String>> filterByDimensions;

    private final int pageNumber;
    private final int pageSize;

}
