package org.iq47.service;

import org.iq47.model.entity.City;

import java.util.List;

public interface CityService {
    List<City> getAutocompleteEntries(String query);
}
