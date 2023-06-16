package org.iq47.delegates;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.service.SellerTicketService;
import org.iq47.service.TicketService;
import org.springframework.stereotype.Component;

@Component("deleteSellerTicket")
@AllArgsConstructor
public class DeleteSellerTicket implements JavaDelegate {

    private SellerTicketService sellerTicketService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long sellerTicketId = Long.parseLong((String) delegateExecution.getVariable("sellerTicketId"));
        sellerTicketService.deleteTicket(sellerTicketId);
    }
}
