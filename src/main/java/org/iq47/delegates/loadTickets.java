package org.iq47.delegates;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;
import org.iq47.model.entity.Ticket;
import org.iq47.service.TicketService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.camunda.spin.Spin.JSON;

@Component("loadTickets")
@AllArgsConstructor
public class loadTickets implements JavaDelegate {

    private TicketService ticketService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<Ticket> tickets = ticketService.findAllTickets();

        SpinJsonNode json = JSON("[]");
        for(Ticket ticket: tickets) {
            SpinJsonNode elem = JSON("{\"label\": \"" + ticket.getId()+ "\",\"value\": \"" + ticket.getId() + "\"}");
            json.append(elem);
        }

        delegateExecution.setVariable("ticketsSelect", json);
    }
}
