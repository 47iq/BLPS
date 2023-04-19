package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.exception.TicketSaveException;
import org.iq47.model.entity.SellerTicket;
import org.iq47.model.entity.User;
import org.iq47.model.entity.UserBalance;
import org.iq47.model.entity.UserTicket;
import org.iq47.network.request.UserBalanceRequest;
import org.iq47.network.request.UserTicketRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.service.AuthService;
import org.iq47.service.UserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/user_balance")
@Slf4j
public class UserBalanceController {

    UserBalanceService userBalanceService;
    private AuthService userService;

    @Autowired
    public UserBalanceController(UserBalanceService userBalanceService, AuthService userService) {
        this.userBalanceService = userBalanceService;
        this.userService = userService;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            boolean isDeleted = userBalanceService.delete(id);
            if (!isDeleted) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("User balance has not been deleted."));
            }
            return ResponseEntity.ok().body(null);
        } catch (TicketSaveException ex) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(ex.getMessage()));
        } catch (Exception e) {
            log.error(String.format("Got %s while deleting user balance %s", e.getClass(), id));
            return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
        }
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> create(@RequestBody UserBalanceRequest req) {
        try {
            User user = (User) userService.loadUserByUsername(req.getUsername());
            if(user == null) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("User with the given username doesn't exist."));
            }
            Optional<UserBalance> userBalanceOptional = userBalanceService.getByUsername(user.getUsername());
            if(userBalanceOptional.isPresent()){
                return ResponseEntity.badRequest().body(new ResponseWrapper("User balance for this user already exists."));
            }
            UserBalance userBalance = new UserBalance();
            userBalance.setUsername(req.getUsername());
            userBalance.setBalance(req.getBalance());
            userBalanceOptional = userBalanceService.save(userBalance);
            if (!userBalanceOptional.isPresent()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("User balance has not been saved."));
            }
            return ResponseEntity.ok().body(userBalanceOptional.get());
        } catch (TicketSaveException ex) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(ex.getMessage()));
        } catch (Exception e) {
            return reportError(req, e);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getBalance(@PathVariable long id) {
        Optional<UserBalance> item = userBalanceService.getById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok().body(item.get());
        } else return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> edit(@RequestBody UserBalanceRequest req, @PathVariable long id) {
        try {
            User user = (User) userService.loadUserByUsername(req.getUsername());
            if(user == null) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("User with the given username doesn't exist."));
            }
            UserBalance userBalance = new UserBalance();
            userBalance.setUsername(req.getUsername());
            userBalance.setBalance(req.getBalance());
            userBalance.setId(id);
            Optional<UserBalance> balanceOptional = userBalanceService.edit(userBalance);
            if (!balanceOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok().body(balanceOptional.get());
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
}
