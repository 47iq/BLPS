package org.iq47.delegates;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.model.entity.Ticket;
import org.iq47.service.TicketService;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component("loadTicket")
@AllArgsConstructor
public class LoadTicket implements JavaDelegate {

    private TicketService ticketService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long ticketId = Long.parseLong((String) delegateExecution.getVariable("ticketId"));
        Ticket ticket = ticketService.getTicketById(ticketId).get();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        delegateExecution.setVariable("departureDate", ticket.getDepartureDate().format(formatter));
        delegateExecution.setVariable("arrivalDate", ticket.getArrivalDate().format(formatter));
        delegateExecution.setVariable("airlineName", ticket.getAirlineName());
        delegateExecution.setVariable("flightCode", ticket.getFlightCode());
        delegateExecution.setVariable("curDepartureCity", ticket.getDepartureCity().getName());
        delegateExecution.setVariable("curArrivalCity", ticket.getArrivalCity().getName());
    }
}
