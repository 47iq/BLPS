package org.iq47.service;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;

public interface CSVGenerationService {
    CSVPrinter prepareAirlineReport(String airline_name) throws IOException;
}
