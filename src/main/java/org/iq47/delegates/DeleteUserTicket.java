package org.iq47.delegates;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.service.UserTicketService;
import org.springframework.stereotype.Component;

@Component("deleteUserTicket")
@AllArgsConstructor
public class DeleteUserTicket implements JavaDelegate {

    private UserTicketService userTicketService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long userTicketId = Long.parseLong((String) delegateExecution.getVariable("userTicketId"));
        userTicketService.delete(userTicketId);
    }
}
