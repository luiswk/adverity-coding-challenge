package com.adverity.wka.codechallenge.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wojciech Kaczmarek
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataEntry {

    @Id
    @GeneratedValue
    private Long id;

    private String datasource;

    private String campaign;

    private LocalDate daily;

    private Long clicks;

    private Long impressions;

}
