package org.iq47.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;
import org.iq47.model.entity.SellerTicket;
import org.iq47.model.entity.UserTicket;
import org.iq47.model.repo1.SellerTicketRepository;
import org.iq47.model.repo1.UserTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

import static org.camunda.spin.Spin.JSON;

@Named("loadAllSellerTickets")
@RequiredArgsConstructor
public class LoadAllSellerTickets implements JavaDelegate {

    @Autowired
    private SellerTicketRepository ticketRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<SellerTicket> availableList = new ArrayList<>(ticketRepository.findAll());
        SpinJsonNode json = JSON("[]");
        for (SellerTicket ticket : availableList) {
            SpinJsonNode elem = JSON("{\"label\": \"" + ticket.getId() + "\",\"value\": \"" + ticket.getId() + "\"}");
            json.append(elem);
        }
        delegateExecution.setVariable("sellerTicketsSelect", json);
    }
}
