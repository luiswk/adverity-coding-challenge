package com.adverity.wka.codechallenge.presentation.rest.model;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DataEntrySearchItemPO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-08T18:17:47.379Z[GMT]")

public class DataEntrySearchItemPO {
    @JsonProperty("datasource")
    private String datasource = null;

    @JsonProperty("campaign")
    private String campaign = null;

    @JsonProperty("daily")
    private LocalDate daily = null;

    @JsonProperty("clicks")
    private Long clicks = null;

    @JsonProperty("impressions")
    private Long impressions = null;

    public DataEntrySearchItemPO datasource(String datasource) {
        this.datasource = datasource;
        return this;
    }

    /**
     * Datasource
     *
     * @return datasource
     **/
    @Schema(description = "Datasource")

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public DataEntrySearchItemPO campaign(String campaign) {
        this.campaign = campaign;
        return this;
    }

    /**
     * Campaign
     *
     * @return campaign
     **/
    @Schema(description = "Campaign")

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public DataEntrySearchItemPO daily(LocalDate daily) {
        this.daily = daily;
        return this;
    }

    /**
     * Daily
     *
     * @return daily
     **/
    @Schema(description = "Daily")

    @Valid
    public LocalDate getDaily() {
        return daily;
    }

    public void setDaily(LocalDate daily) {
        this.daily = daily;
    }

    public DataEntrySearchItemPO clicks(Long clicks) {
        this.clicks = clicks;
        return this;
    }

    /**
     * Clicks
     *
     * @return clicks
     **/
    @Schema(description = "Clicks")

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public DataEntrySearchItemPO impressions(Long impressions) {
        this.impressions = impressions;
        return this;
    }

    /**
     * Impressions
     *
     * @return impressions
     **/
    @Schema(description = "Impressions")

    public Long getImpressions() {
        return impressions;
    }

    public void setImpressions(Long impressions) {
        this.impressions = impressions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataEntrySearchItemPO dataEntrySearchItemPO = (DataEntrySearchItemPO) o;
        return Objects.equals(this.datasource, dataEntrySearchItemPO.datasource) &&
                Objects.equals(this.campaign, dataEntrySearchItemPO.campaign) &&
                Objects.equals(this.daily, dataEntrySearchItemPO.daily) &&
                Objects.equals(this.clicks, dataEntrySearchItemPO.clicks) &&
                Objects.equals(this.impressions, dataEntrySearchItemPO.impressions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datasource, campaign, daily, clicks, impressions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DataEntrySearchItemPO {\n");

        sb.append("    datasource: ").append(toIndentedString(datasource)).append("\n");
        sb.append("    campaign: ").append(toIndentedString(campaign)).append("\n");
        sb.append("    daily: ").append(toIndentedString(daily)).append("\n");
        sb.append("    clicks: ").append(toIndentedString(clicks)).append("\n");
        sb.append("    impressions: ").append(toIndentedString(impressions)).append("\n");
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
