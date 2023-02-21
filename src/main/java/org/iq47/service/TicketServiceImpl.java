package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.model.entity.SellerTicket;
import org.iq47.model.entity.Ticket;
import org.iq47.model.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepo;

    @Override
    public Optional<Ticket> saveTicket(Ticket ticket) {
        Ticket ticket1 = ticketRepo.save(ticket);
        return Optional.of(ticket1);
    }

    @Override
    public Optional<Ticket> getTicketById(long id) {
        Ticket ticket = ticketRepo.getById(id);
        if (ticket == null) return Optional.empty();
        return Optional.of(ticket);
    }

    @Override
    public boolean deleteTicket(long id) {
        return false;
    }
}
