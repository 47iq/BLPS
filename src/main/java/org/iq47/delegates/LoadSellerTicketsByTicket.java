package org.iq47.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;
import org.iq47.model.entity.SellerTicket;
import org.iq47.model.entity.Ticket;
import org.iq47.service.SellerTicketService;
import org.iq47.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.List;

import static org.camunda.spin.Spin.JSON;

@Named("loadSellerTickets")
@RequiredArgsConstructor
public class LoadSellerTicketsByTicket implements JavaDelegate {

    @Autowired
    private SellerTicketService sellerTicketService;

    @Autowired
    private TicketService ticketService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long ticketId = Long.parseLong((String) delegateExecution.getVariable("selectedTicketId"));

        Ticket ticket = ticketService.getTicketById(ticketId).get();

        List<SellerTicket> sellerTickets = sellerTicketService.getSellerTicketsByTicket(ticket);

        SpinJsonNode json = JSON("[]");
        for(SellerTicket sellerTicket: sellerTickets) {
            SpinJsonNode elem = JSON("{\"label\": \"" + sellerTicket.getId()+ "\",\"value\": \"" + sellerTicket.getId() + "\"}");
            json.append(elem);
        }

        delegateExecution.setVariable("sellerTickets", json);
        delegateExecution.setVariable("sellerTicketsText", sellerTickets.stream().peek(Object::toString));
    }
}
