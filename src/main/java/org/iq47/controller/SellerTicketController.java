package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.exception.TicketSaveException;
import org.iq47.model.entity.SellerTicket;
import org.iq47.model.entity.Ticket;
import org.iq47.network.request.SellerTicketRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.service.SellerTicketService;
import org.iq47.service.TicketService;
import org.iq47.validate.SellerTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/seller_tickets")
@Slf4j
public class SellerTicketController {

    @Autowired
    private final SellerTicketService sellerTicketService;
    private final SellerTicketValidator ticketValidator;
    private final TicketService ticketService;

    @Autowired
    public SellerTicketController(SellerTicketService sellerTicketService, SellerTicketValidator ticketValidator, TicketService ticketService) {
        this.sellerTicketService = sellerTicketService;
        this.ticketValidator = ticketValidator;
        this.ticketService = ticketService;
    }

    @PostMapping()
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> create(@RequestBody SellerTicketRequest req) {
        try {
            Optional<String> error = ticketValidator.getErrorMessage(req);
            if(error.isPresent())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper(error.get()));
            return save(req);
        } catch (TicketSaveException ex) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(ex.getMessage()));
        } catch (Exception e) {
            return reportError(req, e);
        }
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            boolean isDeleted = sellerTicketService.deleteTicket(id);
            if (!isDeleted) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("Ticket has not been deleted."));
            }
            return ResponseEntity.ok().body(null);
        } catch (TicketSaveException ex) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(ex.getMessage()));
        } catch (Exception e) {
            log.error(String.format("Got %s while deleting seller ticket %s", e.getClass(), id));
            return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
        }
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> edit(@RequestBody SellerTicketRequest req, @PathVariable long id) {
        try {
            Optional<String> error = ticketValidator.getErrorMessage(req);
            if(error.isPresent())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper(error.get()));
            Optional<Ticket> ticket = ticketService.getTicketById(req.getTicketId());
            if(!ticket.isPresent()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("Ticket with the given id doesn't exist."));
            }
            SellerTicket sellerTicket = SellerTicket.newBuilder()
                    .setId(id)
                    .setLink(req.getLink())
                    .setPrice(req.getPrice())
                    .setTicket(ticket.get())
                    .build();
            Optional<SellerTicket> ticketOptional = sellerTicketService.editSellerTicket(sellerTicket);
            if (!ticketOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok().body(ticketOptional.get());
        } catch (TicketSaveException ex) {
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
    public ResponseEntity<?> getTicket(@PathVariable long id) {
        Optional<SellerTicket> item = sellerTicketService.getTicketById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok().body(item.get());
        } else return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> save(SellerTicketRequest req) throws TicketSaveException {
        Optional<Ticket> ticket = ticketService.getTicketById(req.getTicketId());
        if(!ticket.isPresent()) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Ticket with the given id doesn't exist."));
        }
        SellerTicket sellerTicket = SellerTicket.newBuilder()
                .setLink(req.getLink())
                .setPrice(req.getPrice())
                .setTicket(ticket.get())
                .build();
        Optional<SellerTicket> ticketOptional = sellerTicketService.saveSellerTicket(sellerTicket);
        if (!ticketOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Ticket has not been saved."));
        }
        return ResponseEntity.ok().body(ticketOptional.get());
    }

}
