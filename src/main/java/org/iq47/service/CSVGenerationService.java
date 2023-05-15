package org.iq47.service;

import java.io.IOException;

public interface CSVGenerationService {
    void generateAirlineReport(String airline_name) throws IOException;
}
