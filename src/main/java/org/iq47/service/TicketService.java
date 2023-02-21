package org.iq47.service;

import java.util.Optional;

public interface TicketService {
    Optional<TicketDTO> savePoint(TicketDTO point);
}
