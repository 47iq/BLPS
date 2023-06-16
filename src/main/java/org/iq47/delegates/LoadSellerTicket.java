package org.iq47.delegates;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.model.entity.SellerTicket;
import org.iq47.service.SellerTicketService;
import org.springframework.stereotype.Component;

@Component("loadSellerTicket")
@AllArgsConstructor
public class LoadSellerTicket implements JavaDelegate {

    private SellerTicketService sellerTicketService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long sellerTicketId = Long.parseLong((String) delegateExecution.getVariable("sellerTicketId"));
        SellerTicket sellerTicket = sellerTicketService.getTicketById(sellerTicketId).get();

        delegateExecution.setVariable("ticketId", sellerTicket.getTicket().getId());
        delegateExecution.setVariable("price", sellerTicket.getPrice());
        delegateExecution.setVariable("link", sellerTicket.getLink());
    }
}
