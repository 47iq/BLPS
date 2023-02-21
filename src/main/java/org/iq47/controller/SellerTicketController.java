package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.exception.TicketSaveException;
import org.iq47.model.entity.City;
import org.iq47.model.entity.SellerTicket;
import org.iq47.model.entity.Ticket;
import org.iq47.network.request.SellerTicketRequest;
import org.iq47.network.request.TicketRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.service.SellerTicketService;
import org.iq47.service.TicketService;
import org.iq47.validate.SellerTicketValidator;
import org.iq47.validate.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/seller_ticket")
@Slf4j
public class SellerTicketController {

    private final SellerTicketService ticketService;
    private final SellerTicketValidator ticketValidator;

    @Autowired
    public SellerTicketController(SellerTicketService ticketService, SellerTicketValidator ticketValidator) {
        this.ticketService = ticketService;
        this.ticketValidator = ticketValidator;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SellerTicketRequest req) {
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

    private ResponseEntity<ResponseWrapper> reportError(Object req, Exception e) {
        if(req != null)
            log.error(String.format("Got %s while processing %s", e.getClass(), req));
        else
            log.error(String.format("Got %s while processing request", e.getClass()));
        return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getTicket(@PathVariable long id) {
        Optional<SellerTicket> item = ticketService.getTicketById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok().body(item.get());
        } else return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> save(SellerTicketRequest req) throws TicketSaveException {
        SellerTicket ticket = SellerTicket.newBuilder()
                .setLink(req.getLink())
                .setPrice(req.getPrice())
                .setTicketId(req.getTicketId())
                .build();
        Optional<SellerTicket> ticketOptional = ticketService.saveSellerTicket(ticket);
        if (!ticketOptional.isPresent()) {
            throw new TicketSaveException("Ticket has not been saved.");
        }
        return ResponseEntity.ok().body(ticketOptional.get());
    }

}
