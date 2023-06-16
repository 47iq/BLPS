package org.iq47.delegates;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.model.entity.UserTicket;
import org.iq47.service.UserTicketService;
import org.springframework.stereotype.Component;

@Component("loadUserTicket")
@AllArgsConstructor
public class LoadUserTicket implements JavaDelegate{

    private UserTicketService userTicketService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long userTicketId = Long.parseLong((String) delegateExecution.getVariable("userTicketId"));
        UserTicket userTicket = userTicketService.getUserTicketById(userTicketId).get();

        delegateExecution.setVariable("sellerTicketId", userTicket.getSellerTicket().getId());
        delegateExecution.setVariable("seatNumber", userTicket.getSeatNumber());
        delegateExecution.setVariable("username", userTicket.getUsername());
    }
}
