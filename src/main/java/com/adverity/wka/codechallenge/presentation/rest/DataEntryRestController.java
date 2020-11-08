package com.adverity.wka.codechallenge.presentation.rest;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.adverity.wka.codechallenge.application.DataEntrySearchService;
import com.adverity.wka.codechallenge.domain.DataEntrySearchItem;
import com.adverity.wka.codechallenge.presentation.rest.model.DataEntrySearchCriteriaPO;
import com.adverity.wka.codechallenge.presentation.rest.model.DataEntrySearchItemPO;

/**
 * @author Wojciech Kaczmarek
 */
@RestController
public class DataEntryRestController implements DataEntryApi {

    private final DataEntrySearchService searchService;
    private final DataEntrySearchCriteriaPOValidator poValidator;
    private final DataEntrySearchCriteriaPOConverter poConverter;
    private final DataEntrySearchItemDtoToPOConverter dtoToPOConverter;

    @Autowired
    public DataEntryRestController(DataEntrySearchService searchService,
                                   DataEntrySearchCriteriaPOValidator poValidator,
                                   DataEntrySearchCriteriaPOConverter poConverter,
                                   DataEntrySearchItemDtoToPOConverter dtoToPOConverter) {
        this.searchService = searchService;
        this.poValidator = poValidator;
        this.poConverter = poConverter;
        this.dtoToPOConverter = dtoToPOConverter;
    }

    @Override
    public ResponseEntity<List<DataEntrySearchItemPO>> search(@Valid DataEntrySearchCriteriaPO criteria) {
        poValidator.validate(criteria);
        List<DataEntrySearchItem> items = searchService.performSearch(poConverter.convert(criteria));
        return ResponseEntity.ok(items.stream().map(dtoToPOConverter::convert).collect(Collectors.toList()));
    }

}
