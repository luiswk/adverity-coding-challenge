package com.adverity.wka.codechallenge.presentation.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adverity.wka.codechallenge.application.DataEntrySearchService;
import com.adverity.wka.codechallenge.domain.DataEntrySearchItem;
import com.adverity.wka.codechallenge.presentation.rest.model.DataEntrySearchCriteriaPO;

/**
 * @author Wojciech Kaczmarek
 */
@ExtendWith(MockitoExtension.class)
class DataEntryRestControllerUnitTest {

    private final DataEntrySearchService searchService;
    private final DataEntrySearchCriteriaPOConverter poConverter;
    private final DataEntrySearchItemDtoToPOConverter dtoToPOConverter;

    @InjectMocks
    private DataEntryRestController controller;

    DataEntryRestControllerUnitTest(@Mock DataEntrySearchService searchService,
                                    @Mock DataEntrySearchCriteriaPOConverter poConverter,
                                    @Mock DataEntrySearchItemDtoToPOConverter dtoToPOConverter) {
        this.searchService = searchService;
        this.poConverter = poConverter;
        this.dtoToPOConverter = dtoToPOConverter;
    }

    @Test
    void shouldCallServiceAndConvertersOnSearch() {
        // given
        when(searchService.performSearch(any()))
                .thenReturn(List.of(new DataEntrySearchItem(), new DataEntrySearchItem(), new DataEntrySearchItem()));

        // when
        controller.search(new DataEntrySearchCriteriaPO());

        // then
        verify(poConverter).convert(any());
        verify(dtoToPOConverter, times(3)).convert(any());
    }
}
