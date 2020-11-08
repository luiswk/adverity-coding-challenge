package com.adverity.wka.codechallenge.extractors;

import java.util.List;

import com.adverity.wka.codechallenge.domain.DataEntry;

/**
 * @author Wojciech Kaczmarek
 */
public interface DataExtractor {

    List<DataEntry> extractData();

}
