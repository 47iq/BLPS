package org.iq47.service;

import org.iq47.model.entity.Ticket;
import org.iq47.model.entity.TicketStats;

import java.util.Optional;

public interface StatsService {
    Optional<TicketStats> addClickByTicketId(long id);
}
