package org.iq47.service;

import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.iq47.model.entity.Ticket;
import org.iq47.model.entity.UserTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CSVGenerationServiceImpl implements CSVGenerationService {

    UserTicketService userTicketService;

    @Data
    private static class TicketData {
        int free = 0;
        int occupied = 0;
    }

    @Autowired
    public CSVGenerationServiceImpl(UserTicketService userTicketService) {
        this.userTicketService = userTicketService;
    }

    @Override
    public void generateAirlineReport(String airline_name) throws IOException {
        List<UserTicket> userTickets = userTicketService.collectAirlineTicketsData(airline_name);
        Map<Ticket, TicketData> tickets = new HashMap<>();

        for(UserTicket userTicket: userTickets) {
            TicketData ticketData;
            Ticket ticket = userTicket.getSellerTicket().getTicket();
            if(tickets.containsKey(ticket)) {
                ticketData = tickets.get(ticket);
            } else {
                ticketData = new TicketData();
            }
            if(userTicket.getUsername() == null) {
                ticketData.setFree(ticketData.getFree() + 1);
            } else {
                ticketData.setOccupied(ticketData.getOccupied() + 1);
            }
            tickets.put(ticket, ticketData);
        }

        File file = new File(String.format("%s-%s.csv", airline_name, LocalDate.now().toString()));
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(String.format("%s-%s.csv", airline_name, LocalDate.now().toString())));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("id", "departureDate", "arrivalDate", "flightCode", "departureCity", "arrivalCity", "sold_quantity", "left_quantity"));
        for(Ticket ticket: tickets.keySet()) {
            csvPrinter.printRecord(ticket.getId(), ticket.getDepartureDate(), ticket.getArrivalDate(), ticket.getFlightCode(),
                    ticket.getDepartureCity(), ticket.getArrivalCity(), tickets.get(ticket).getOccupied(), tickets.get(ticket).getFree());
        }
        csvPrinter.flush();
    }
}
