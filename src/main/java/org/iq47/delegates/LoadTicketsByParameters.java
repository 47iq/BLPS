package org.iq47.delegates;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;
import org.iq47.model.entity.City;
import org.iq47.model.entity.Ticket;
import org.iq47.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.camunda.spin.Spin.JSON;

@Named("loadTicketsByParameters")
@RequiredArgsConstructor
public class LoadTicketsByParameters implements JavaDelegate {

    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    @Autowired
    private TicketService ticketService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String departureCity = (String) delegateExecution.getVariable("departureCity");
        String arrivalCity = (String) delegateExecution.getVariable("arrivalCity");
        Date departueDate = formatter.parse((String) delegateExecution.getVariable("departureDate"));
        ZoneId zoneId = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(3));

        //remove paging?
        List<Ticket> tickets = ticketService.findTickets(departureCity, arrivalCity, departueDate, null, zoneId, 1);

        SpinJsonNode json = JSON("[]");
        for(Ticket ticket: tickets) {
            SpinJsonNode elem = JSON("{\"label\": \"" + ticket.getId()+ "\",\"value\": \"" + ticket.getId() + "\"}");
            json.append(elem);
        }

        delegateExecution.setVariable("foundTicketsText", tickets.stream().peek(Object::toString));
        delegateExecution.setVariable("foundTickets", json);
    }
}
