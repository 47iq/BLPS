package org.iq47.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.model.entity.SellerTicket;
import org.iq47.model.entity.Ticket;
import org.iq47.service.SellerTicketService;
import org.iq47.service.TicketService;

import java.util.List;

public class LoadSellerTicketsByTicket implements JavaDelegate {

    private SellerTicketService sellerTicketService;

    private TicketService ticketService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long ticketId = Long.parseLong((String) delegateExecution.getVariable("selectedTicketId"));

        Ticket ticket = ticketService.getTicketById(ticketId).get();

        List<SellerTicket> sellerTickets = sellerTicketService.getSellerTicketsByTicket(ticket);

        delegateExecution.setVariable("sellerTickets", sellerTickets);
    }
}
