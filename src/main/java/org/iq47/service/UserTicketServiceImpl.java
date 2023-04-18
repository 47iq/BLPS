package org.iq47.service;

import org.iq47.model.UserBalanceRepository;
import org.iq47.model.UserTicketRepository;
import org.iq47.model.entity.User;
import org.iq47.model.entity.UserBalance;
import org.iq47.model.entity.UserTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserTicketServiceImpl implements UserTicketService {

    private final UserTicketRepository userTicketRepository;

    private final UserBalanceRepository userBalanceRepository;

    @Autowired
    public UserTicketServiceImpl(UserTicketRepository userTicketRepository, UserBalanceRepository userBalanceRepository) {
        this.userTicketRepository = userTicketRepository;
        this.userBalanceRepository = userBalanceRepository;
    }

    @Transactional
    public void exchangeTickets(UserTicket prevUserTicket, UserTicket newUserTicket, int price, User current) {
        prevUserTicket.setUser(null);
        userTicketRepository.save(prevUserTicket);
        newUserTicket.setUser(current);
        userTicketRepository.save(newUserTicket);
        UserBalance userBalance = userBalanceRepository.getByUserId(current.getId());
        userBalance.setBalance(userBalance.getBalance() + price);
        userBalanceRepository.editUserBalance(userBalance);
    }

    @Override
    public Optional<UserTicket> getUserTicketById(long ticketId) {
        return Optional.empty();
    }
}
