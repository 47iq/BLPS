package org.iq47.service;

import org.iq47.model.entity.SellerTicket;
import org.iq47.model.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface SellerTicketService {
    Optional<SellerTicket> saveSellerTicket(SellerTicket ticket);

    Optional<SellerTicket> getTicketById(long id);

    boolean deleteTicket(long id);

    Optional<SellerTicket> editSellerTicket(SellerTicket ticket);

    List<SellerTicket> getSellerTicketsByTicket(Ticket ticket);
}
