package com.adverity.wka.codechallenge.domain;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author Wojciech Kaczmarek
 */
@Repository
public class DataEntryRepository extends SimpleJpaRepository<DataEntry, Long> {

    private final EntityManager em;

    @Autowired
    public DataEntryRepository(EntityManager em) {
        super(DataEntry.class, em);
        this.em = em;
    }

    @Transactional
    public List<DataEntrySearchItem> performSearch(DataEntrySearchCriteria searchCriteria) {
        Assert.notNull(searchCriteria, "criteria are required");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<DataEntry> root = cq.from(DataEntry.class);

        Selection<?>[] selections = prepareSelectClause(searchCriteria, root, cb).toArray(new Selection<?>[] {});
        cq.multiselect(selections);
        prepareWhereClause(searchCriteria).stream().reduce(Specification::and)
                .ifPresent(s -> cq.where(s.toPredicate(root, cq, cb)));
        Optional.ofNullable(searchCriteria.getGroupByDimensions())
                .filter(col -> !col.isEmpty())
                .ifPresent(col -> cq.groupBy(col.stream().map(root::get).collect(Collectors.toList())));
        return aliasToBeanTransformer(selections, em.createQuery(cq).getResultList());
    }

    private static List<Selection<?>> prepareSelectClause(DataEntrySearchCriteria criteria, Root<DataEntry> root, CriteriaBuilder cb) {
        Set<String> dimensions = Set.of("datasource", "campaign", "daily");
        Set<String> metrics = Set.of("clicks", "impressions");

        List<Selection<?>> dimensionSelects = Optional.ofNullable(criteria.getGroupByDimensions())
                .filter(col -> !col.isEmpty())
                .or(() -> Optional.of(dimensions))
                .map(col -> toExpressions(col, root))
                .orElse(Collections.emptyList());

        List<Selection<?>> aggregatesSelects = Optional.ofNullable(criteria.getAggregateOnMetrics())
                .filter(col -> !col.isEmpty())
                .map(col -> toAggregateExpressions(col, root, cb))
                .or(() -> Optional.of(metrics).map(col -> toExpressions(col, root)))
                .orElse(Collections.emptyList());

        return Stream.concat(dimensionSelects.stream(), aggregatesSelects.stream()).collect(Collectors.toList());
    }

    private static List<Specification<DataEntry>> prepareWhereClause(DataEntrySearchCriteria searchCriteria) {
        List<Specification<DataEntry>> aggregatedCriteria = new LinkedList<>();
        Optional.ofNullable(searchCriteria.getFrom()).map(DataEntrySpecification::withFrom).ifPresent(aggregatedCriteria::add);
        Optional.ofNullable(searchCriteria.getTo()).map(DataEntrySpecification::withTo).ifPresent(aggregatedCriteria::add);
        Optional.ofNullable(searchCriteria.getFilterByDimensions())
                .map(Map::entrySet)
                .map(entries -> entries.stream()
                        .map(entry -> DataEntrySpecification.withDimensionFilter(entry.getKey(), entry.getValue()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .ifPresent(aggregatedCriteria::addAll);
        return aggregatedCriteria;
    }

    private static List<DataEntrySearchItem> aliasToBeanTransformer(Selection<?>[] selections, List<Object[]> resultList) {
        BiFunction<Object[], String, Object> func = (arr, name) -> {
            for (int i = 0; i < selections.length; i++) {
                if (selections[i].getAlias().equals(name)) {
                    return arr[i];
                }
            }
            return null;
        };

        return resultList.stream().map(arr -> {
            String campaign = (String) func.apply(arr, "campaign");
            String datasource = (String) func.apply(arr, "datasource");
            LocalDate daily = (LocalDate) func.apply(arr, "daily");
            Long clicks = (Long) func.apply(arr, "clicks");
            Long impressions = (Long) func.apply(arr, "impressions");

            DataEntrySearchItem item = new DataEntrySearchItem();
            item.setCampaign(campaign);
            item.setDatasource(datasource);
            item.setDaily(daily);
            item.setClicks(clicks);
            item.setImpressions(impressions);
            return item;
        }).collect(Collectors.toList());
    }

    private static List<Selection<?>> toExpressions(Set<String> props, Root<DataEntry> root) {
        return props.stream().map(prop -> root.get(prop).alias(prop)).collect(Collectors.toList());
    }

    private static List<Selection<?>> toAggregateExpressions(Set<String> props, Root<DataEntry> root, CriteriaBuilder cb) {
        return props.stream().map(aom -> cb.sum(root.get(aom)).alias(aom)).collect(Collectors.toList());
    }

    private static class DataEntrySpecification {

        public static Specification<DataEntry> withFrom(LocalDate from) {
            return (entry, cq, cb) -> cb.greaterThanOrEqualTo(entry.get("daily"), from);
        }

        public static Specification<DataEntry> withTo(LocalDate to) {
            return (entry, cq, cb) -> cb.lessThanOrEqualTo(entry.get("daily"), to);
        }

        public static Specification<DataEntry> withDimensionFilter(String dimension, Collection<String> filters) {
            return Optional.ofNullable(filters)
                    .flatMap(col -> col.stream()
                            .filter(StringUtils::isNotBlank)
                            .map(pattern -> (Specification<DataEntry>) (entry, cq, cb) -> cb.like(entry.get(dimension), pattern))
                            .reduce(Specification::or))
                    .orElse(null);
        }
    }

}
