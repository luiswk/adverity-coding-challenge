package com.adverity.wka.codechallenge.presentation.rest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.adverity.wka.codechallenge.presentation.rest.model.DataEntrySearchCriteriaPO;
import com.adverity.wka.codechallenge.presentation.rest.model.DimensionFilterPO;

/**
 * @author Wojciech Kaczmarek
 */
class DataEntrySearchCriteriaPOValidatorUnitTest {

    private final DataEntrySearchCriteriaPOValidator validator = new DataEntrySearchCriteriaPOValidator();

    @Test
    void shouldPassValidation() {
        // given
        DataEntrySearchCriteriaPO criteria = new DataEntrySearchCriteriaPO()
                .addAggregateOnItem(DataEntrySearchCriteriaPO.AggregateOnEnum.CLICKS)
                .addGroupByItem(DataEntrySearchCriteriaPO.GroupByEnum.CAMPAIGN)
                .addFilterByItem(new DimensionFilterPO().name(DimensionFilterPO.NameEnum.CAMPAIGN));

        // when
        validator.validate(criteria);

        // then
    }

    @Test
    void shouldFailValidationOnInvalidAggregateOn() {
        // given
        DataEntrySearchCriteriaPO criteria = new DataEntrySearchCriteriaPO().addAggregateOnItem(null);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> validator.validate(criteria));
    }

    @Test
    void shouldFailValidationOnInvalidGroupBy() {
        // given
        DataEntrySearchCriteriaPO criteria = new DataEntrySearchCriteriaPO().addGroupByItem(null);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> validator.validate(criteria));
    }

    @Test
    void shouldFailValidationOnInvalidFilterBy() {
        // given
        DataEntrySearchCriteriaPO criteria = new DataEntrySearchCriteriaPO().addFilterByItem(new DimensionFilterPO().name(null));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> validator.validate(criteria));
    }

}
