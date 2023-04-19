package org.iq47.service;

import org.iq47.exception.ExchangeException;
import org.iq47.model.entity.Ticket;
import org.iq47.model.entity.User;
import org.iq47.model.entity.UserTicket;

import java.rmi.NoSuchObjectException;
import java.util.Optional;

public interface UserTicketService {
    void exchangeTickets(UserTicket prevUserTicket, UserTicket newUserTicket, int price, User current) throws ExchangeException, ExchangeException;

    Optional<UserTicket> getUserTicketById(long ticketId);
}
