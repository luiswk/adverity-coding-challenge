package com.adverity.wka.codechallenge.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.adverity.wka.codechallenge.domain.DataEntryRepository;
import com.adverity.wka.codechallenge.domain.DataEntrySearchCriteria;
import com.adverity.wka.codechallenge.domain.DataEntrySearchItem;

/**
 * @author Wojciech Kaczmarek
 */
@Service
class DataEntrySearchServiceImpl implements DataEntrySearchService {

    private final DataEntryRepository dataEntryRepository;

    @Autowired
    DataEntrySearchServiceImpl(DataEntryRepository dataEntryRepository) {
        this.dataEntryRepository = dataEntryRepository;
    }

    @Override
    @Transactional
    public List<DataEntrySearchItem> performSearch(DataEntrySearchCriteria searchCriteria) {
        Assert.notNull(searchCriteria, "criteria are required");
        return dataEntryRepository.performSearch(searchCriteria);
    }

}
