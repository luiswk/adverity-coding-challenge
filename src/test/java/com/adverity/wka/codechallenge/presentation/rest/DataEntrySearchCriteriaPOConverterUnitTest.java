package com.adverity.wka.codechallenge.presentation.rest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adverity.wka.codechallenge.domain.DataEntrySearchCriteria;
import com.adverity.wka.codechallenge.presentation.rest.model.DataEntrySearchCriteriaPO;
import com.adverity.wka.codechallenge.presentation.rest.model.DimensionFilterPO;

/**
 * @author Wojciech Kaczmarek
 */
class DataEntrySearchCriteriaPOConverterUnitTest {

    private final DataEntrySearchCriteriaPOConverter converter = new DataEntrySearchCriteriaPOConverter();

    @Test
    void shouldConvert() {
        // given
        LocalDate fromDate = LocalDate.of(2020, 11, 8);
        LocalDate toDate = LocalDate.of(2020, 11, 9);
        int pageNumber = 1;
        int pageSize = 10;


        DataEntrySearchCriteriaPO from = new DataEntrySearchCriteriaPO()
                .fromDate(fromDate)
                .toDate(toDate)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .addAggregateOnItem(DataEntrySearchCriteriaPO.AggregateOnEnum.CLICKS)
                .addGroupByItem(DataEntrySearchCriteriaPO.GroupByEnum.CAMPAIGN)
                .addFilterByItem(new DimensionFilterPO().name(DimensionFilterPO.NameEnum.CAMPAIGN).addValuesItem("abc"));

        // when
        DataEntrySearchCriteria to = converter.convert(from);

        // then
        assertEquals(fromDate, to.getFrom());
        assertEquals(toDate, to.getTo());
        assertEquals(pageNumber, to.getPageNumber());
        assertEquals(pageSize, to.getPageSize());
        assertEquals(Set.of("clicks"), to.getAggregateOnMetrics());
        assertEquals(Set.of("campaign"), to.getGroupByDimensions());
        assertEquals(Map.of("campaign", Set.of("abc")), to.getFilterByDimensions());
    }
}
