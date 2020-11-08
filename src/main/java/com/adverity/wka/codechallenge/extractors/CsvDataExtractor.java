package com.adverity.wka.codechallenge.extractors;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import com.adverity.wka.codechallenge.domain.DataEntry;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.log4j.Log4j2;

/**
 * @author Wojciech Kaczmarek
 */
@Log4j2
@Component
class CsvDataExtractor implements DataExtractor {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");

    @Override
    public List<DataEntry> extractData() {
        log.info("Extracting data entries from csv file...");
        try (InputStream inputStream = getClass().getResourceAsStream("/data-warehouse.csv")) {
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            MappingIterator<Map<String, String>> rowsIterator = mapper.readerFor(Map.class).with(schema).readValues(inputStream);
            Iterable<Map<String, String>> iterable = () -> rowsIterator;
            return StreamSupport.stream(iterable.spliterator(), false)
                    .map(CsvDataExtractor::convertToDataEntry)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error while reading csv file.", e);
        }
        return Collections.emptyList();
    }

    private static DataEntry convertToDataEntry(Map<String, String> row) {
        DataEntry entry = new DataEntry();
        entry.setDatasource(row.get(Headers.Datasource.name()));
        entry.setCampaign(row.get(Headers.Campaign.name()));
        entry.setDaily(LocalDate.parse(row.get(Headers.Daily.name()), DATE_TIME_FORMATTER));
        entry.setClicks(Long.valueOf(row.get(Headers.Clicks.name())));
        entry.setImpressions(Long.valueOf(row.get(Headers.Impressions.name())));
        return entry;
    }

    private enum Headers {
        Datasource,
        Campaign,
        Daily,
        Clicks,
        Impressions
    }

}
