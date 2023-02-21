package org.iq47.validate;

import org.iq47.network.request.SellerTicketRequest;
import org.iq47.network.request.TicketRequest;

import java.util.Optional;

public interface SellerTicketValidator {
    Optional<String> getErrorMessage(SellerTicketRequest ticket);
}
