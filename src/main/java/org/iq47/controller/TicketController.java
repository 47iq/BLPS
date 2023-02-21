package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.exception.TicketSaveException;
import org.iq47.model.entity.City;
import org.iq47.model.entity.Ticket;
import org.iq47.network.request.TicketRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.service.TicketService;
import org.iq47.validate.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/tickets")
@Slf4j
public class TicketController {

    private final TicketService ticketService;
    private final TicketValidator ticketValidator;

    @Autowired
    public TicketController(TicketService ticketService, TicketValidator ticketValidator) {
        this.ticketService = ticketService;
        this.ticketValidator = ticketValidator;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TicketRequest req) {
        try {
            Optional<String> error = ticketValidator.getErrorMessage(req);
            if(error.isPresent())
                throw new InvalidRequestException(error.get());
            return save(req);
        } catch (TicketSaveException | InvalidRequestException ex) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(ex.getMessage()));
        } catch (Exception e) {
            return reportError(req, e);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getTicket(@PathVariable long id) {
        Optional<Ticket> item = ticketService.getTicketById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok().body(item.get());
        } else return ResponseEntity.notFound().build();
    }

    private ResponseEntity<ResponseWrapper> reportError(Object req, Exception e) {
        if(req != null)
            log.error(String.format("Got %s while processing %s", e.getClass(), req));
        else
            log.error(String.format("Got %s while processing request", e.getClass()));
        return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
    }

    private ResponseEntity<?> save(TicketRequest req) throws TicketSaveException {
        Ticket ticket = Ticket.newBuilder()
                .setDepartureCity(new City(req.getDepartureCity()))
                .setArrivalCity(new City(req.getArrivalCity()))
                .setDepartureDate(req.getDepartureDate())
                .setArrivalDate(req.getArrivalDate())
                .setAirlineName(req.getAirlineName())
                .setFlightCode(req.getFlightCode())
                .build();
        Optional<Ticket> ticketOptional = ticketService.savePoint(ticket);
        if (!ticketOptional.isPresent()) {
            throw new TicketSaveException("Ticket has not been saved.");
        }
        return ResponseEntity.ok().body(ticketOptional.get());
    }

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        try {
            //TODO
            return ResponseEntity.ok().body(null);
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return reportError(null, e);
        }
    }
}
