package org.iq47.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.model.entity.City;
import org.iq47.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named("loadCities")
@RequiredArgsConstructor
public class LoadCities implements JavaDelegate {

    @Autowired
    private CityService cityService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<City> cities = cityService.getAllCities();
        Map<String, String> map = cities.stream().collect(Collectors.toMap(City::getName, City::getName));
        delegateExecution.setVariable("cities", map);
    }
}
