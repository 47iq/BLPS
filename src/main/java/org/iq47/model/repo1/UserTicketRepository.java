package org.iq47.model.repo1;

import org.iq47.model.entity.Ticket;
import org.iq47.model.entity.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {

    @Query(value = "SELECT u FROM UserTicket as u JOIN SellerTicket as s ON u.sellerTicket.id=s.id JOIN Ticket as t ON t.id=s.ticket.id where t.airlineName = :airline_name")
    List<UserTicket> collectAirlineTicketsData(@Param("airline_name") String airline_name);

}
