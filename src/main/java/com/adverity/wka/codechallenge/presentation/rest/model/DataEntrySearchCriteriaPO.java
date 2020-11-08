package com.adverity.wka.codechallenge.presentation.rest.model;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DataEntrySearchCriteriaPO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-08T19:13:10.500Z[GMT]")

public class DataEntrySearchCriteriaPO {
    @JsonProperty("fromDate")
    private LocalDate fromDate = null;

    @JsonProperty("toDate")
    private LocalDate toDate = null;

    /**
     * Gets or Sets aggregateOn
     */
    public enum AggregateOnEnum {
        CLICKS("clicks"),

        IMPRESSIONS("impressions");

        private String value;

        AggregateOnEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static AggregateOnEnum fromValue(String text) {
            for (AggregateOnEnum b : AggregateOnEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("aggregateOn")
    @Valid
    private List<AggregateOnEnum> aggregateOn = null;

    /**
     * Gets or Sets groupBy
     */
    public enum GroupByEnum {
        DATASOURCE("datasource"),

        CAMPAIGN("campaign");

        private String value;

        GroupByEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static GroupByEnum fromValue(String text) {
            for (GroupByEnum b : GroupByEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonProperty("groupBy")
    @Valid
    private List<GroupByEnum> groupBy = null;

    @JsonProperty("filterBy")
    @Valid
    private List<DimensionFilterPO> filterBy = null;

    @JsonProperty("pageNumber")
    private Integer pageNumber = null;

    @JsonProperty("pageSize")
    private Integer pageSize = null;

    public DataEntrySearchCriteriaPO fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    /**
     * From date (YYYY-MM-DD)
     *
     * @return fromDate
     **/
    @Schema(description = "From date (YYYY-MM-DD)")

    @Valid
    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public DataEntrySearchCriteriaPO toDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    /**
     * To date (YYYY-MM-DD)
     *
     * @return toDate
     **/
    @Schema(description = "To date (YYYY-MM-DD)")

    @Valid
    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public DataEntrySearchCriteriaPO aggregateOn(List<AggregateOnEnum> aggregateOn) {
        this.aggregateOn = aggregateOn;
        return this;
    }

    public DataEntrySearchCriteriaPO addAggregateOnItem(AggregateOnEnum aggregateOnItem) {
        if (this.aggregateOn == null) {
            this.aggregateOn = new ArrayList<AggregateOnEnum>();
        }
        this.aggregateOn.add(aggregateOnItem);
        return this;
    }

    /**
     * List of metrics to aggregate on
     *
     * @return aggregateOn
     **/
    @Schema(description = "List of metrics to aggregate on")

    public List<AggregateOnEnum> getAggregateOn() {
        return aggregateOn;
    }

    public void setAggregateOn(List<AggregateOnEnum> aggregateOn) {
        this.aggregateOn = aggregateOn;
    }

    public DataEntrySearchCriteriaPO groupBy(List<GroupByEnum> groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    public DataEntrySearchCriteriaPO addGroupByItem(GroupByEnum groupByItem) {
        if (this.groupBy == null) {
            this.groupBy = new ArrayList<GroupByEnum>();
        }
        this.groupBy.add(groupByItem);
        return this;
    }

    /**
     * List of dimensions to group by
     *
     * @return groupBy
     **/
    @Schema(description = "List of dimensions to group by")

    public List<GroupByEnum> getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(List<GroupByEnum> groupBy) {
        this.groupBy = groupBy;
    }

    public DataEntrySearchCriteriaPO filterBy(List<DimensionFilterPO> filterBy) {
        this.filterBy = filterBy;
        return this;
    }

    public DataEntrySearchCriteriaPO addFilterByItem(DimensionFilterPO filterByItem) {
        if (this.filterBy == null) {
            this.filterBy = new ArrayList<DimensionFilterPO>();
        }
        this.filterBy.add(filterByItem);
        return this;
    }

    /**
     * Get filterBy
     *
     * @return filterBy
     **/
    @Schema(description = "")
    @Valid
    public List<DimensionFilterPO> getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(List<DimensionFilterPO> filterBy) {
        this.filterBy = filterBy;
    }

    public DataEntrySearchCriteriaPO pageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    /**
     * Page number
     *
     * @return pageNumber
     **/
    @Schema(description = "Page number")

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public DataEntrySearchCriteriaPO pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * Page size
     *
     * @return pageSize
     **/
    @Schema(description = "Page size")

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataEntrySearchCriteriaPO dataEntrySearchCriteriaPO = (DataEntrySearchCriteriaPO) o;
        return Objects.equals(this.fromDate, dataEntrySearchCriteriaPO.fromDate) &&
                Objects.equals(this.toDate, dataEntrySearchCriteriaPO.toDate) &&
                Objects.equals(this.aggregateOn, dataEntrySearchCriteriaPO.aggregateOn) &&
                Objects.equals(this.groupBy, dataEntrySearchCriteriaPO.groupBy) &&
                Objects.equals(this.filterBy, dataEntrySearchCriteriaPO.filterBy) &&
                Objects.equals(this.pageNumber, dataEntrySearchCriteriaPO.pageNumber) &&
                Objects.equals(this.pageSize, dataEntrySearchCriteriaPO.pageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromDate, toDate, aggregateOn, groupBy, filterBy, pageNumber, pageSize);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DataEntrySearchCriteriaPO {\n");

        sb.append("    fromDate: ").append(toIndentedString(fromDate)).append("\n");
        sb.append("    toDate: ").append(toIndentedString(toDate)).append("\n");
        sb.append("    aggregateOn: ").append(toIndentedString(aggregateOn)).append("\n");
        sb.append("    groupBy: ").append(toIndentedString(groupBy)).append("\n");
        sb.append("    filterBy: ").append(toIndentedString(filterBy)).append("\n");
        sb.append("    pageNumber: ").append(toIndentedString(pageNumber)).append("\n");
        sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
