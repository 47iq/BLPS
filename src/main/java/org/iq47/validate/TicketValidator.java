package org.iq47.validate;

import org.iq47.network.request.TicketRequest;

import java.util.Optional;

public interface TicketValidator {
    Optional<String> getErrorMessage(TicketRequest point);
}
