package org.iq47.service;

import org.iq47.exception.ExchangeException;
import org.iq47.model.entity.SellerTicket;
import org.iq47.model.repo1.UserTicketRepository;
import org.iq47.model.entity.User;
import org.iq47.model.entity.UserBalance;
import org.iq47.model.entity.UserTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserTicketServiceImpl implements UserTicketService {

    private final UserTicketRepository userTicketRepository;

    private final UserBalanceService userBalanceService;

    @Autowired
    public UserTicketServiceImpl(UserTicketRepository userTicketRepository, UserBalanceService userBalanceService) {
        this.userTicketRepository = userTicketRepository;
        this.userBalanceService = userBalanceService;
    }

    @Transactional
    public void exchangeTickets(UserTicket prevUserTicket, UserTicket newUserTicket, int price, User current) throws ExchangeException {
        prevUserTicket.setUsername(null);
        userTicketRepository.save(prevUserTicket);
        newUserTicket.setUsername(current.getUsername());
        userTicketRepository.save(newUserTicket);
        Optional<UserBalance> userBalanceOpt = userBalanceService.getByUsername(current.getUsername());
        if (!userBalanceOpt.isPresent())
            throw new ExchangeException("User balance doesn't exist");
        UserBalance userBalance = userBalanceOpt.get();
        if (userBalance.getBalance() < price)
            throw new ExchangeException("User doesn't have enough money for this operation");
        userBalance.setBalance(userBalance.getBalance() - price);
        userBalanceService.editUserBalance(userBalance);
    }

    @Transactional
    public void exchangeTickets(UserTicket prevUserTicket, UserTicket newUserTicket, int price, String username) throws ExchangeException {
        prevUserTicket.setUsername(null);
        userTicketRepository.save(prevUserTicket);
        newUserTicket.setUsername(username);
        userTicketRepository.save(newUserTicket);
        Optional<UserBalance> userBalanceOpt = userBalanceService.getByUsername(username);
        if (!userBalanceOpt.isPresent())
            throw new ExchangeException("User balance doesn't exist");
        UserBalance userBalance = userBalanceOpt.get();
        if (userBalance.getBalance() < price)
            throw new ExchangeException("User doesn't have enough money for this operation");
        userBalance.setBalance(userBalance.getBalance() - price);
        userBalanceService.editUserBalance(userBalance);
    }

    @Override
    public Optional<UserTicket> save(UserTicket userTicket) {
        UserTicket ticket1 = userTicketRepository.save(userTicket);
        return Optional.of(ticket1);
    }

    @Override
    public Optional<UserTicket> edit(UserTicket userTicket) {
        if (userTicketRepository.existsById(userTicket.getId())) {
            return Optional.of(userTicketRepository.save(userTicket));
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(long id) {
        if (userTicketRepository.existsById(id)) {
            userTicketRepository.deleteById(id);
        }
        return true;
    }

    @Override
    public Optional<UserTicket> getTicketById(long id) {
        return userTicketRepository.findById(id);
    }

    @Override
    public Optional<UserTicket> getUserTicketById(long ticketId) {
        return userTicketRepository.findById(ticketId);
    }

    @Override
    public List<UserTicket> collectAirlineTicketsData(String airline) {
//        return userTicketRepository.findAll().stream().filter(x -> x.getSellerTicket().getTicket().getAirlineName().equals(airline)).collect(Collectors.toList());
        return userTicketRepository.collectAirlineTicketsData(airline);
    }
}
