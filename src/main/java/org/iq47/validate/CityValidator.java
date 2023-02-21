package org.iq47.validate;

import org.iq47.network.request.TicketRequest;

import java.util.Optional;

public interface CityValidator {

    

    Optional<String> getErrorMessage(String cityName);
}
