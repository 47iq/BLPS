package org.iq47.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;
import org.iq47.model.entity.City;
import org.iq47.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.camunda.spin.Spin.JSON;

@Named("loadCities")
@RequiredArgsConstructor
public class LoadCities implements JavaDelegate {

    @Autowired
    private CityService cityService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<City> cities = cityService.getAllCities();
        SpinJsonNode json = JSON("[]");
        for(City city: cities) {
            json.append("\"label\": \"" + city.getName() + "\",\"value\": \"" + city.getName() + "\"");
        }
        Map<String, String> map = cities.stream().collect(Collectors.toMap(City::getName, City::getName));
        delegateExecution.setVariable("cities", map);
    }
}
