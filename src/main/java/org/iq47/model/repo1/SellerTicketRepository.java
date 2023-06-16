package org.iq47.model.repo1;

import org.iq47.model.entity.SellerTicket;
import org.iq47.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerTicketRepository extends JpaRepository<SellerTicket, Long> {
    List<SellerTicket> getAllByTicket(Ticket ticket);
}
