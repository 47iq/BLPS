package org.iq47.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.model.entity.SellerTicket;
import org.iq47.service.SellerTicketService;

import javax.inject.Named;

@Named("showTicketInfo")
public class ShowTicketInfo implements JavaDelegate {

    private SellerTicketService sellerTicketService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long sellerTicketId = Long.parseLong((String) delegateExecution.getVariable("selectedSellerTicket"));

        SellerTicket sellerTicket = sellerTicketService.getTicketById(sellerTicketId).get();

        delegateExecution.setVariable("sellerTicket", sellerTicket);
    }
}
