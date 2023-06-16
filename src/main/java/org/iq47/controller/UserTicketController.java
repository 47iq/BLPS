/*
package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.exception.ExchangeException;
import org.iq47.exception.TicketSaveException;
import org.iq47.model.entity.SellerTicket;
import org.iq47.model.entity.Ticket;
import org.iq47.model.entity.User;
import org.iq47.model.entity.UserTicket;
import org.iq47.network.request.ExchangeTicketRequest;
import org.iq47.network.request.SellerTicketRequest;
import org.iq47.network.request.UserTicketRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.service.AuthService;
import org.iq47.service.SellerTicketService;
import org.iq47.service.UserTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.rmi.NoSuchObjectException;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user_tickets")
@Slf4j
public class UserTicketController {

    private UserTicketService userTicketService;

    private SellerTicketService sellerTicketService;

    private AuthService userService;

    @Autowired
    public UserTicketController(UserTicketService userTicketService, AuthService userService, SellerTicketService sellerTicketService) {
        this.userTicketService = userTicketService;
        this.sellerTicketService = sellerTicketService;
        this.userService = userService;
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/exchange")
    public ResponseEntity<?> exchange(@RequestBody ExchangeTicketRequest req) {
        try {
            Optional<UserTicket> oldTicket = userTicketService.getUserTicketById(req.getOldTicketId());
            Optional<UserTicket> newTicket = userTicketService.getUserTicketById(req.getNewTicketId());
            UserDetails user = userService.loadUserByUsername(req.getUsername());
            if (user == null) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("User with the given username doesn't exist."));
            }
            if (!oldTicket.isPresent() || !newTicket.isPresent()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("User ticket with the given id doesn't exist."));
            }
            userTicketService.exchangeTickets(oldTicket.get(), newTicket.get(), req.getPrice(), (User) user);
            return ResponseEntity.ok().body(null);
        } catch (ExchangeException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(e.getMessage()));
        } catch (Exception e) {
            log.error(String.format("Got %s while processing %s", e.getClass(), req));
            return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
        }
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> edit(@RequestBody UserTicketRequest req, @PathVariable long id) {
        try {
            Optional<SellerTicket> ticket = sellerTicketService.getTicketById(req.getSellerTicketId());
            if(!ticket.isPresent()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("Seller ticket with the given id doesn't exist."));
            }
            User user = (User) userService.loadUserByUsername(req.getUsername());
            if(user == null) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("User with the given username doesn't exist."));
            }
            UserTicket userTicket = new UserTicket();
            userTicket.setSellerTicket(ticket.get());
            userTicket.setUsername(req.getUsername());
            userTicket.setSeatNumber(req.getSeatNumber());
            userTicket.setId(id);
            Optional<UserTicket> ticketOptional = userTicketService.edit(userTicket);
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

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            boolean isDeleted = userTicketService.delete(id);
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

    @PostMapping()
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> create(@RequestBody UserTicketRequest req) {
//        try {
            Optional<SellerTicket> ticket = sellerTicketService.getTicketById(req.getSellerTicketId());
            if(!ticket.isPresent()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("Seller ticket with the given id doesn't exist."));
            }
            User user = (User) userService.loadUserByUsername(req.getUsername());
            if(user == null) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("User with the given username doesn't exist."));
            }
            UserTicket userTicket = new UserTicket();
            userTicket.setSellerTicket(ticket.get());
            userTicket.setUsername(req.getUsername());
            userTicket.setSeatNumber(req.getSeatNumber());
            Optional<UserTicket> ticketOptional = userTicketService.save(userTicket);
            if (!ticketOptional.isPresent()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("Ticket has not been saved."));
            }
            return ResponseEntity.ok().body(ticketOptional.get());
//        } catch (TicketSaveException ex) {
//            return ResponseEntity.badRequest().body(new ResponseWrapper(ex.getMessage()));
//        } catch (Exception e) {
//            return reportError(req, e);
//        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicket(@PathVariable long id) {
        Optional<UserTicket> item = userTicketService.getTicketById(id);
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
}
*/
