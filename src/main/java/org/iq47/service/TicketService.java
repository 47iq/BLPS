package org.iq47.service;

import org.iq47.network.TicketDTO;

import java.util.Optional;

public interface TicketService {
    Optional<TicketDTO> savePoint(TicketDTO point);
}
