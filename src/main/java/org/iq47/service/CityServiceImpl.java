package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.model.repo1.CityRepository;
import org.iq47.model.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> getAutocompleteEntries(String query) {
        return cityRepository.getTop5ItemsByNameContainsIgnoreCase(query);
    }

    @Override
    public Optional<City> getCityByName(String cityName) {
        return cityRepository.getCityByName(cityName);
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<City> saveCity(City city) {
        City cityRes = cityRepository.save(city);
        return Optional.of(cityRes);
    }

    @Override
    public boolean deleteCity(String id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
