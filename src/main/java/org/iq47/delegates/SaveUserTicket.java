package org.iq47.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.model.entity.SellerTicket;
import org.iq47.model.entity.UserTicket;
import org.iq47.model.repo1.SellerTicketRepository;
import org.iq47.model.repo1.UserTicketRepository;
import org.iq47.service.UserTicketService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

@Named("saveUserTicket")
@RequiredArgsConstructor
public class SaveUserTicket implements JavaDelegate {

    @Autowired
    private UserTicketRepository ticketRepository;

    @Autowired
    private UserTicketService userTicketService;

    @Autowired
    private SellerTicketRepository sellerTicketRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long userTicketId = Long.parseLong((String) delegateExecution.getVariable("userTicketId"));
        long sellerTicketId = Long.parseLong((String) delegateExecution.getVariable("sellerTicketId"));
        long seatNumber = (Integer) delegateExecution.getVariable("seatNumber");
        String username = (String) delegateExecution.getVariable("username");
        Object action = delegateExecution.getVariable("action");
        UserTicket userTicket;
        if (action != null) {
            userTicket = userTicketService.getTicketById(userTicketId).get();
            userTicket.setUsername(username);
            userTicket.setSeatNumber(seatNumber);
        } else {
            userTicket = new UserTicket();
            SellerTicket s = sellerTicketRepository.getById(sellerTicketId);
            userTicket.setSellerTicket(s);
            userTicket.setSeatNumber(seatNumber);
            userTicket.setUsername(username);
        }
        userTicketService.save(userTicket);
    }
}