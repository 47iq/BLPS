package org.iq47.validate;

import org.iq47.network.request.TicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketValidatorImpl implements TicketValidator {

    private final CityValidator cityValidator;

    @Autowired
    public TicketValidatorImpl(CityValidator cityValidator) {
        this.cityValidator = cityValidator;
    }

    @Override
    public Optional<String> getErrorMessage(TicketRequest ticket) {
        Optional<String> arrivalCityError = cityValidator.getErrorMessage(ticket.getArrivalCity());
        Optional<String> departureCityError = cityValidator.getErrorMessage(ticket.getDepartureCity());
        if(arrivalCityError.isPresent())
            return arrivalCityError;
        if(departureCityError.isPresent())
            return departureCityError;
        if(ticket.getAirlineName() == null)
            return Optional.of("Airline name must be set");
        if(ticket.getArrivalDate() == null)
            return Optional.of("Arrival time must be set");
        if(ticket.getDepartureDate() == null)
            return Optional.of("Departure time must be set");
        if(ticket.getFlightCode() == null)
            return Optional.of("Flight code must be set");
        return Optional.empty();
    }
}
