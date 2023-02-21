package org.iq47.model;

import org.iq47.model.entity.Ticket;
import org.iq47.model.entity.TicketStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketStatsRepository extends JpaRepository<TicketStats, Long> {

}
