package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.model.entity.Ticket;
import org.iq47.model.repo1.SellerTicketRepository;
import org.iq47.model.entity.SellerTicket;
import org.iq47.model.repo1.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerTicketServiceImpl implements SellerTicketService {

    @Autowired
    SellerTicketRepository sellerTicketRepository;

    @Override
    public Optional<SellerTicket> saveSellerTicket(SellerTicket ticket) {
        SellerTicket ticket1 = sellerTicketRepository.save(ticket);
        return Optional.of(ticket1);
    }

    @Override
    public Optional<SellerTicket> getTicketById(long id) {
        return sellerTicketRepository.findById(id);
    }

    @Override
    public boolean deleteTicket(long id) {
        if (sellerTicketRepository.existsById(id)) {
            sellerTicketRepository.deleteById(id);
        }
        return true;
    }

    @Override
    public Optional<SellerTicket> editSellerTicket(SellerTicket ticket) {
        if (sellerTicketRepository.existsById(ticket.getId())) {
            return Optional.of(sellerTicketRepository.save(ticket));
        }
        return Optional.empty();
    }

    @Override
    public List<SellerTicket> getSellerTicketsByTicket(Ticket ticket) {
        return sellerTicketRepository.getAllByTicket(ticket);
    }
}
