package com.adverity.wka.codechallenge.extractors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.adverity.wka.codechallenge.domain.DataEntryRepository;
import lombok.extern.log4j.Log4j2;

/**
 * @author Wojciech Kaczmarek
 */
@Log4j2
@Component
class DataCreator {

    private final List<DataExtractor> dataExtractors;
    private final DataEntryRepository dataEntryRepository;

    @Autowired
    DataCreator(List<DataExtractor> dataExtractors,
                DataEntryRepository dataEntryRepository) {
        this.dataExtractors = dataExtractors;
        this.dataEntryRepository = dataEntryRepository;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void fillDb() {
        log.info("Found total number of {} data extractors.", dataExtractors.size());
        dataExtractors.stream()
                .map(DataExtractor::extractData)
                .forEach(list -> list.forEach(dataEntryRepository::save));
    }

}
