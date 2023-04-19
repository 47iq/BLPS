package org.iq47.model.repo1;

import org.iq47.model.entity.City;
import org.iq47.model.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface CustomTicketRepository {

    Page<Ticket> getTicketsByDepartureCityAndArrivalCity(Pageable pageable, City departure, City arrival, LocalDateTime depDate, LocalDateTime arrDate);

}
