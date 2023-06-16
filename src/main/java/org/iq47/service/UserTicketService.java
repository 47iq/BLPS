package org.iq47.service;

import org.iq47.exception.ExchangeException;
import org.iq47.model.entity.Ticket;
import org.iq47.model.entity.User;
import org.iq47.model.entity.UserTicket;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.Optional;

public interface UserTicketService {
    void exchangeTickets(UserTicket prevUserTicket, UserTicket newUserTicket, int price, User current) throws ExchangeException, ExchangeException;

    void exchangeTickets(UserTicket prevUserTicket, UserTicket newUserTicket, int price, String current) throws ExchangeException, ExchangeException;


    Optional<UserTicket> getUserTicketById(long ticketId);

    Optional<UserTicket> save(UserTicket userTicket);

    Optional<UserTicket> edit(UserTicket userTicket);

    boolean delete(long id);

    Optional<UserTicket> getTicketById(long id);

    List<UserTicket> collectAirlineTicketsData(String airline);
}
