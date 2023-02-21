package org.iq47.model;

import org.iq47.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
