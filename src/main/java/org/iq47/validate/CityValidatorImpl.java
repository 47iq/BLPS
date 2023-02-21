package org.iq47.validate;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityValidatorImpl implements CityValidator{

    @Override
    public Optional<String> getErrorMessage(String cityName) {

        return Optional.empty();
    }
}
