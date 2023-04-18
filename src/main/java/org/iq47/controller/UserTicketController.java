package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.exception.TicketSaveException;
import org.iq47.model.entity.Ticket;
import org.iq47.model.entity.User;
import org.iq47.model.entity.UserTicket;
import org.iq47.network.request.ExchangeTicketRequest;
import org.iq47.network.request.SellerTicketRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.service.AuthService;
import org.iq47.service.UserTicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/user_tickets")
@Slf4j
public class UserTicketController {

    UserTicketService userTicketService;

    AuthService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/exchange")
    public ResponseEntity<?> exchange(@RequestBody ExchangeTicketRequest req) {
        try {
            Optional<UserTicket> oldTicket = userTicketService.getUserTicketById(req.getOldTicketId());
            Optional<UserTicket> newTicket = userTicketService.getUserTicketById(req.getNewTicketId());
            UserDetails user = userService.loadUserByUsername(req.getUsername());
            if(user == null) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("User with the given username doesn't exist."));
            }
            if(!oldTicket.isPresent() || !newTicket.isPresent()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("User ticket with the given id doesn't exist."));
            }
            userTicketService.exchangeTickets(oldTicket.get(), newTicket.get(), req.getPrice(), (User) user);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            log.error(String.format("Got %s while processing %s", e.getClass(), req));
            return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
        }
    }
}
