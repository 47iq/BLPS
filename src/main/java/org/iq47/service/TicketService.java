package org.iq47.service;

import org.iq47.model.entity.Ticket;

import java.util.Optional;

public interface TicketService {
    Optional<Ticket> saveTicket(Ticket ticket);

    Optional<Ticket> getTicketById(long id);
}
