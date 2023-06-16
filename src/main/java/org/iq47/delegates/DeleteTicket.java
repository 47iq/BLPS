package org.iq47.delegates;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.service.TicketService;
import org.springframework.stereotype.Component;

@Component("deleteTicket")
@AllArgsConstructor
public class DeleteTicket implements JavaDelegate {

    private TicketService ticketService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long ticketId = Long.parseLong((String) delegateExecution.getVariable("ticketId"));
        ticketService.deleteTicket(ticketId);
    }
}
