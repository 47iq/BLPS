package org.iq47.model;

import org.iq47.model.entity.City;
import org.iq47.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> getTicketsByDepartureCityAndArrivalCityAndArrivalDate(
            City departureCity, City arrivalCity, LocalDateTime arrivalDate);

    List<Ticket> getTicketsByDepartureCityAndArrivalCityAndArrivalDateAndDepartureDate(
            City departureCity, City arrivalCity, LocalDateTime arrivalDate, LocalDateTime departureDate);

    List<Ticket> getTicketsByDepartureCityAndArrivalCity(City departureCity, City arrivalCity);

    @Query("select a from Ticket a where a.arrivalDate = :arrdate and a.departureDate = :depdate")
    List<Ticket> findAllWithSpecificArrivalDate(@Param("arrdate") Date arrDate, @Param("depdate") Date depDate);

}
