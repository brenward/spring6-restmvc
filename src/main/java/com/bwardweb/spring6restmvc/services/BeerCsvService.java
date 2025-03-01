package com.bwardweb.spring6restmvc.services;

import com.bwardweb.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(File csvFile);
}
