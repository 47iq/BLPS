package org.iq47.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;
import org.iq47.model.entity.User;
import org.iq47.model.entity.UserTicket;
import org.iq47.model.repo1.UserTicketRepository;
import org.iq47.service.UserTicketService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.camunda.spin.Spin.JSON;

@Named("exchangeTickets")
@RequiredArgsConstructor
public class ExchangeTickets implements JavaDelegate {

    @Autowired
    private UserTicketRepository ticketRepository;
    @Autowired
    private UserTicketService userTicketService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = (String) delegateExecution.getVariable("username");
        long oldTicketId = Long.parseLong((String) delegateExecution.getVariable("oldTicketId"));
        long newTicketId = Long.parseLong((String) delegateExecution.getVariable("newTicketId"));
        int cost = (Integer) delegateExecution.getVariable("cost");
        UserTicket oldTicket = ticketRepository.getById(oldTicketId);
        UserTicket newTicket = ticketRepository.getById(newTicketId);
        userTicketService.exchangeTickets(oldTicket, newTicket, cost, username);
    }
}
