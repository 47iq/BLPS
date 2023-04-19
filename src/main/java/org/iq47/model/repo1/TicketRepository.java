package org.iq47.model.repo1;

import org.iq47.model.entity.City;
import org.iq47.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, CustomTicketRepository{
    List<Ticket> getTicketsByDepartureCityAndArrivalCityAndArrivalDate(
            City departureCity, City arrivalCity, LocalDateTime arrivalDate);

    List<Ticket> getTicketsByDepartureCityAndArrivalCityAndArrivalDateAndDepartureDate(
            City departureCity, City arrivalCity, LocalDateTime arrivalDate, LocalDateTime departureDate);

    List<Ticket> getTicketsByDepartureCityAndArrivalCity(City departureCity, City arrivalCity);

    @Query("select a from Ticket a where a.arrivalDate = :arrdate and a.departureDate = :depdate")
    List<Ticket> findAllWithSpecificArrivalDate(@Param("arrdate") Date arrDate, @Param("depdate") Date depDate);

}
