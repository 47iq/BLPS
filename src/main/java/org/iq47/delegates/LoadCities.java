package org.iq47.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.model.entity.City;
import org.iq47.service.CityService;

import javax.inject.Named;
import java.util.List;

@Named("loadCities")
@RequiredArgsConstructor
public class LoadCities implements JavaDelegate {

    private CityService cityService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<City> cities = cityService.getAllCities();

        delegateExecution.setVariable("cities", cities);
    }
}
