package org.iq47.delegates;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.model.entity.SellerTicket;
import org.iq47.service.SellerTicketService;
import org.iq47.service.TicketService;
import org.springframework.stereotype.Component;

@Component("saveSellerTicket")
@AllArgsConstructor
public class SaveSellerTicket implements JavaDelegate {

    private SellerTicketService sellerTicketService;

    private TicketService ticketService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long ticketId = Long.parseLong((String) delegateExecution.getVariable("ticketId"));
        int price = Integer.parseInt((String) delegateExecution.getVariable("price"));
        String link = (String) delegateExecution.getVariable("link");

        SellerTicket.Builder builder = SellerTicket.newBuilder();
        SellerTicket sellerTicket = builder.setTicket(ticketService.getTicketById(ticketId).get())
                .setLink(link)
                .setPrice(price)
                .build();

        sellerTicketService.saveSellerTicket(sellerTicket);
    }
}
