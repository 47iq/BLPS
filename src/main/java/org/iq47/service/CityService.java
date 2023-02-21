package org.iq47.service;

import org.iq47.model.entity.City;
import org.iq47.model.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface CityService {
    List<City> getAutocompleteEntries(String query);

    Optional<City> saveCity(City city);

    boolean deleteCity(String name);

    Optional<City> getCityByName(String cityName);
}
