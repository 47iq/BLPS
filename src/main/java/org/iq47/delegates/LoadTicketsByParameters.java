package org.iq47.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.model.entity.Ticket;
import org.iq47.service.TicketService;

import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Named("loadTicketsByParameters")
public class LoadTicketsByParameters implements JavaDelegate {

    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

    private TicketService ticketService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String departureCity = (String) delegateExecution.getVariable("departureCity");
        String arrivalCity = (String) delegateExecution.getVariable("arrivalCity");
        Date departueDate = formatter.parse((String) delegateExecution.getVariable("departureDate"));
        ZoneId zoneId = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(3));

        //remove paging?
        List<Ticket> tickets = ticketService.findTickets(departureCity, arrivalCity, departueDate, null, zoneId, 1);

        delegateExecution.setVariable("foundTickets", tickets);
    }
}
