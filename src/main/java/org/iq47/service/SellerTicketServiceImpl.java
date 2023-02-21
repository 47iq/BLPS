package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.model.SellerTicketRepository;
import org.iq47.model.entity.SellerTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        SellerTicket sellerTicket = sellerTicketRepository.getById(id);
        if (sellerTicket == null) return Optional.empty();
        return Optional.of(sellerTicket);
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
}
