package com.adverity.wka.codechallenge.domain;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Wojciech Kaczmarek
 */
@Getter
@Setter
public class DataEntrySearchItem {

    private String datasource;
    private String campaign;
    private LocalDate daily;
    private Long clicks;
    private Long impressions;

}
