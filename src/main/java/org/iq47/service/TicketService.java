package org.iq47.service;

import org.iq47.model.entity.Ticket;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TicketService {
    Optional<Ticket> saveTicket(Ticket ticket);

    Optional<Ticket> getTicketById(long id);

    boolean deleteTicket(long id);

    Optional<Ticket> editTicket(Ticket ticket);

    List<Ticket> findTickets(String departureCity, String arrivalCity, Date departureDate, Date arrivalDate, ZoneId zoneId);

    Optional<Double> averageTicketsPrice(String departureCity, String arrivalCity, Date flightDate, ZoneId zoneId);
}
