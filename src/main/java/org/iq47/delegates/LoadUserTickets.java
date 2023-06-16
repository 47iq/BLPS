package org.iq47.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;
import org.iq47.model.entity.City;
import org.iq47.model.entity.UserBalance;
import org.iq47.model.entity.UserTicket;
import org.iq47.model.repo1.UserTicketRepository;
import org.iq47.service.UserBalanceService;
import org.iq47.service.UserTicketService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.camunda.spin.Spin.JSON;


@Named("loadUserTickets")
@RequiredArgsConstructor
public class LoadUserTickets implements JavaDelegate {

    @Autowired
    private UserTicketRepository ticketRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = (String) delegateExecution.getVariable("username");
        List<UserTicket> userTicketList = ticketRepository.findAll().stream().filter(x -> Objects.equals(x.getUsername(), username)).collect(Collectors.toList());
        List<UserTicket> availableList = ticketRepository.findAll().stream().filter(x -> x.getUsername() == null).collect(Collectors.toList());
        SpinJsonNode json = JSON("[]");
        for(UserTicket ticket: userTicketList) {
            SpinJsonNode elem = JSON("{\"label\": \"" + ticket.getId() + "\",\"value\": \"" + ticket.getId() + "\"}");
            json.append(elem);
        }
        delegateExecution.setVariable("userTicketsSelect", json);
        json = JSON("[]");
        for(UserTicket ticket: availableList) {
            SpinJsonNode elem = JSON("{\"label\": \"" + ticket.getId() + "\",\"value\": \"" + ticket.getId() + "\"}");
            json.append(elem);
        }
        delegateExecution.setVariable("newTicketsSelect", json);
    }
}
