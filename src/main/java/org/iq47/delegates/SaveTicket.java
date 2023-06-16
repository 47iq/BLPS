package org.iq47.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.iq47.model.entity.City;
import org.iq47.model.entity.Ticket;
import org.iq47.service.CityService;
import org.iq47.service.TicketService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component("saveTicket")
public class SaveTicket implements JavaDelegate {

    private TicketService ticketService;
    private CityService cityService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Ticket.Builder builder = Ticket.newBuilder();

        City arrivalCity = cityService.getCityByName((String) delegateExecution.getVariable("arrivalCity")).get();
        City departureCity = cityService.getCityByName((String) delegateExecution.getVariable("departureCity")).get();

        Ticket ticket = builder.setAirlineName((String) delegateExecution.getVariable("airlineName"))
                .setArrivalCity(arrivalCity)
                .setDepartureCity(departureCity)
                .setArrivalDate(LocalDateTime.parse((String) delegateExecution.getVariable("arrivalDate"), formatter))
                .setDepartureDate(LocalDateTime.parse((String) delegateExecution.getVariable("departureDate"), formatter))
                .setFlightCode((String) delegateExecution.getVariable("flightCode"))
                .build();

        ticketService.saveTicket(ticket);
    }
}
