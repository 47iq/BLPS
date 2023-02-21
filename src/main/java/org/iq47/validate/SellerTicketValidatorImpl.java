package org.iq47.validate;

import org.iq47.network.request.SellerTicketRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerTicketValidatorImpl implements SellerTicketValidator {


    @Override
    public Optional<String> getErrorMessage(SellerTicketRequest ticket) {
        if(ticket.getLink() == null)
            return Optional.of("Link to ticket seller must be set");
        if(ticket.getTicketId() == null)
            return Optional.of("Ticket id must be set");
        if(ticket.getPrice() == null)
            return Optional.of("Ticket price must be set");
        if(ticket.getPrice() <= 0)
            return Optional.of("Ticket price must be positive");
        return Optional.empty();
    }
}
