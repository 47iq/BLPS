package org.iq47.model;

import org.iq47.model.entity.City;
import org.iq47.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<List<Ticket>> getTicketsByDepartureCityAndArrivalCityAndArrivalDate(
            City departureCity, City arrivalCity, LocalDateTime arrivalDate);

    Optional<List<Ticket>> getTicketsByDepartureCityAndArrivalCityAndArrivalDateAndDepartureDate(
            City departureCity, City arrivalCity, LocalDateTime arrivalDate, LocalDateTime departureDate);
}
