package com.adverity.wka.codechallenge.presentation.rest;

import org.springframework.stereotype.Component;

import com.adverity.wka.codechallenge.domain.DataEntrySearchItem;
import com.adverity.wka.codechallenge.presentation.rest.model.DataEntrySearchItemPO;

/**
 * @author Wojciech Kaczmarek
 */
@Component
class DataEntrySearchItemDtoToPOConverter {

    public DataEntrySearchItemPO convert(DataEntrySearchItem from) {
        return new DataEntrySearchItemPO()
                .campaign(from.getCampaign())
                .datasource(from.getDatasource())
                .daily(from.getDaily())
                .clicks(from.getClicks())
                .impressions(from.getImpressions());
    }

}
