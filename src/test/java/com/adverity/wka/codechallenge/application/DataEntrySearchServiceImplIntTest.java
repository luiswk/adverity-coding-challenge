package com.adverity.wka.codechallenge.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.adverity.wka.codechallenge.configs.CodeChallengeTestConfig;
import com.adverity.wka.codechallenge.domain.DataEntry;
import com.adverity.wka.codechallenge.domain.DataEntryRepository;
import com.adverity.wka.codechallenge.domain.DataEntrySearchCriteria;
import com.adverity.wka.codechallenge.domain.DataEntrySearchItem;

/**
 * @author Wojciech Kaczmarek
 */
@SpringJUnitConfig
@ContextConfiguration(classes = CodeChallengeTestConfig.class)
@Transactional
@Rollback
class DataEntrySearchServiceImplIntTest {

    @Autowired
    private DataEntrySearchService service;

    @Autowired
    private DataEntryRepository repository;

    @Test
    void shouldFilterByFromAndTo() {
        // given
        List<DataEntry> entries = List.of(
                DataEntry.builder().daily(LocalDate.of(2020, 11, 1)).build(),
                DataEntry.builder().daily(LocalDate.of(2020, 11, 2)).build(),
                DataEntry.builder().daily(LocalDate.of(2020, 11, 3)).build(),
                DataEntry.builder().daily(LocalDate.of(2020, 11, 4)).build(),
                DataEntry.builder().daily(LocalDate.of(2020, 11, 5)).build()
        );
        repository.saveAll(entries);

        DataEntrySearchCriteria criteria = DataEntrySearchCriteria.builder()
                .from(LocalDate.of(2020, 11, 2))
                .to(LocalDate.of(2020, 11, 4))
                .build();

        // when
        List<DataEntrySearchItem> results = service.performSearch(criteria);

        // then
        assertNotNull(results);
        assertEquals(3, results.size());
    }

    @Test
    void shouldAggregateOnClicks() {
        // given
        List<DataEntry> entries = List.of(
                DataEntry.builder().campaign("campaign1").datasource("datasource1").clicks(10L).build(),
                DataEntry.builder().campaign("campaign2").datasource("datasource1").clicks(20L).build(),
                DataEntry.builder().campaign("campaign1").datasource("datasource2").clicks(30L).build(),
                DataEntry.builder().campaign("campaign2").datasource("datasource2").clicks(40L).build(),
                DataEntry.builder().campaign("campaign1").datasource("datasource1").clicks(50L).build()
        );
        repository.saveAll(entries);

        DataEntrySearchCriteria criteria = DataEntrySearchCriteria.builder()
                .groupByDimensions(Set.of("campaign"))
                .aggregateOnMetrics(Set.of("clicks"))
                .build();

        // when
        List<DataEntrySearchItem> results = service.performSearch(criteria);

        // then
        assertNotNull(results);
        assertEquals(2, results.size());
        assertAggregatedSearchItem(results, DataEntrySearchItem::getCampaign, "campaign1", DataEntrySearchItem::getClicks, 90L);
        assertAggregatedSearchItem(results, DataEntrySearchItem::getCampaign, "campaign2", DataEntrySearchItem::getClicks, 60L);
    }

    @Test
    void shouldAggregateOnImpressions() {
        // given
        List<DataEntry> entries = List.of(
                DataEntry.builder().campaign("campaign1").datasource("datasource1").impressions(10L).build(),
                DataEntry.builder().campaign("campaign2").datasource("datasource1").impressions(20L).build(),
                DataEntry.builder().campaign("campaign1").datasource("datasource2").impressions(30L).build(),
                DataEntry.builder().campaign("campaign2").datasource("datasource2").impressions(40L).build(),
                DataEntry.builder().campaign("campaign1").datasource("datasource1").impressions(50L).build()
        );
        repository.saveAll(entries);

        DataEntrySearchCriteria criteria = DataEntrySearchCriteria.builder()
                .groupByDimensions(Set.of("datasource"))
                .aggregateOnMetrics(Set.of("impressions"))
                .build();

        // when
        List<DataEntrySearchItem> results = service.performSearch(criteria);

        // then
        assertNotNull(results);
        assertEquals(2, results.size());
        assertAggregatedSearchItem(results, DataEntrySearchItem::getDatasource, "datasource1", DataEntrySearchItem::getImpressions, 80L);
        assertAggregatedSearchItem(results, DataEntrySearchItem::getDatasource, "datasource2", DataEntrySearchItem::getImpressions, 70L);
    }

    @Test
    void shouldGroupByDimensions() {
        // given
        List<DataEntry> entries = List.of(
                DataEntry.builder().campaign("campaign1").datasource("datasource1").build(),
                DataEntry.builder().campaign("campaign1").datasource("datasource1").build(),
                DataEntry.builder().campaign("campaign1").datasource("datasource2").build(),
                DataEntry.builder().campaign("campaign2").datasource("datasource2").build(),
                DataEntry.builder().campaign("campaign2").datasource("datasource2").build()
        );
        repository.saveAll(entries);

        DataEntrySearchCriteria criteria = DataEntrySearchCriteria.builder()
                .groupByDimensions(Set.of("campaign", "datasource"))
                .build();

        // when
        List<DataEntrySearchItem> results = service.performSearch(criteria);

        // then
        assertNotNull(results);
        assertEquals(3, results.size());
    }

    @Test
    void shouldFilterByDimensions() {
        // given
        List<DataEntry> entries = List.of(
                DataEntry.builder().campaign("Adventmarkt Touristik").datasource("Google Ads").build(),
                DataEntry.builder().campaign("WRKS").datasource("Google Ads").build(),
                DataEntry.builder().campaign("WRKS").datasource("Twitter Ads").build(),
                DataEntry.builder().campaign("Rechtsschutz").datasource("Twitter Ads").build(),
                DataEntry.builder().campaign("Nothilfe").datasource("Facebook Ads").build()
        );
        repository.saveAll(entries);

        DataEntrySearchCriteria criteria = DataEntrySearchCriteria.builder()
                .filterByDimensions(Map.ofEntries(
                        Map.entry("datasource", Set.of("Google%", "Twitter%")),
                        Map.entry("campaign", Set.of("WRKS", "Rechtsschutz"))))
                .build();

        // when
        List<DataEntrySearchItem> results = service.performSearch(criteria);

        // then
        assertNotNull(results);
        assertEquals(3, results.size());
        List<DataEntrySearchItem> filteredOutEntries = results.stream()
                .filter(r -> r.getDatasource().equals("Facebook Ads") || r.getCampaign().equals("Adventmarkt Touristik"))
                .collect(Collectors.toList());
        assertTrue(filteredOutEntries.isEmpty());
    }

    private static void assertAggregatedSearchItem(List<DataEntrySearchItem> results,
                                                   Function<DataEntrySearchItem, String> dimensionFunc,
                                                   String expectedDimension,
                                                   Function<DataEntrySearchItem, Long> metricFunc,
                                                   Long expectedMetric) {
        results.stream().filter(r -> dimensionFunc.apply(r).equals(expectedDimension)).findFirst()
                .ifPresentOrElse(
                        r -> assertEquals(expectedMetric, metricFunc.apply(r)),
                        () -> fail("item not found")
                );
    }
}
