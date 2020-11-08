package com.adverity.wka.codechallenge.application;

import java.util.List;

import com.adverity.wka.codechallenge.domain.DataEntrySearchCriteria;
import com.adverity.wka.codechallenge.domain.DataEntrySearchItem;

/**
 * @author Wojciech Kaczmarek
 */
public interface DataEntrySearchService {

    List<DataEntrySearchItem> performSearch(DataEntrySearchCriteria searchCriteria);

}
