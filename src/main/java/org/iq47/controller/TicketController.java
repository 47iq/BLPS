package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.exception.TicketSaveException;
import org.iq47.model.entity.City;
import org.iq47.model.entity.Ticket;
import org.iq47.network.request.TicketRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.service.CityService;
import org.iq47.service.TicketService;
import org.iq47.validate.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/tickets")
@Slf4j
public class TicketController {

    private final TicketService ticketService;
    private final TicketValidator ticketValidator;
    private final CityService cityService;

    @Autowired
    public TicketController(TicketService ticketService, TicketValidator ticketValidator, CityService cityService) {
        this.ticketService = ticketService;
        this.ticketValidator = ticketValidator;
        this.cityService = cityService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TicketRequest req) {
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            boolean isDeleted = ticketService.deleteTicket(id);
            if (!isDeleted) {
                throw new TicketSaveException("Ticket has not been deleted.");
            }
            return ResponseEntity.ok().body(null);
        } catch (TicketSaveException ex) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(ex.getMessage()));
        } catch (Exception e) {
            log.error(String.format("Got %s while deleting seller ticket %s", e.getClass(), id));
            return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> delete(@RequestBody TicketRequest req) {
        try {
            Optional<String> error = ticketValidator.getErrorMessage(req);
            if(error.isPresent())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper(error.get()));
            return edit(req);
        } catch (TicketSaveException ex) {
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


    @GetMapping("/find")
    public ResponseEntity<?> get(
            @RequestParam String departureCity,
            @RequestParam String arrivalCity,
            @DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam Date departureDate,
            @DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam Date arrivalDate
    ) {
        try {
            List<Ticket> tickets = ticketService.findTickets(departureCity, arrivalCity, departureDate, arrivalDate);
            return ResponseEntity.ok().body(tickets);
        } catch (Exception e) {
            return reportError(null, e);
        }
    }

    @GetMapping("/average_price")
    public ResponseEntity<?> get(
            @RequestParam String departureCity,
            @RequestParam String arrivalCity,
            @DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam Date flightDate
    ) {
        try {
            Double price = ticketService.averageTicketsPrice(departureCity, arrivalCity, flightDate);
            return ResponseEntity.ok().body(price);
        } catch (Exception e) {
            return reportError(null, e);
        }
    }

    private ResponseEntity<ResponseWrapper> reportError(Object req, Exception e) {
        if(req != null)
            log.error(String.format("Got %s while processing %s", e.getClass(), req));
        else
            log.error(String.format("Got %s while processing request", e.getClass()));
        return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
    }

    private ResponseEntity<?> save(TicketRequest req) throws TicketSaveException {
        Optional<Ticket> ticket = buildTicket(req);
        if (ticket.isPresent()) {
            Optional<Ticket> ticketOptional = ticketService.saveTicket(ticket.get());
            if (!ticketOptional.isPresent()) {
                throw new TicketSaveException("Ticket has not been saved.");
            }
            return ResponseEntity.ok().body(ticketOptional.get());
        }
        return ResponseEntity.badRequest().body("City not found.");
    }

    private ResponseEntity<?> edit(TicketRequest req) throws TicketSaveException {
        Optional<Ticket> ticket = buildTicket(req);
        if (ticket.isPresent()) {
            Optional<Ticket> ticketOptional = ticketService.editTicket(ticket.get());
            if (!ticketOptional.isPresent()) {
                throw new TicketSaveException("Ticket has not been edited.");
            }
            return ResponseEntity.ok().body(ticketOptional.get());
        }

        return ResponseEntity.badRequest().body("Ticket/city not found.");
    }

    private Optional<Ticket> buildTicket(TicketRequest req) {
        Optional<City> depCity = cityService.getCityByName(req.getDepartureCity());
        if (!depCity.isPresent()) return Optional.empty();
        Optional<City> arrCity = cityService.getCityByName(req.getArrivalCity());
        if (!arrCity.isPresent()) return Optional.empty();

        return Optional.of(Ticket.newBuilder()
                .setId(req.getId())
                .setDepartureCity(depCity.get())
                .setArrivalCity(arrCity.get())
                .setDepartureDate(req.getDepartureDate())
                .setArrivalDate(req.getArrivalDate())
                .setAirlineName(req.getAirlineName())
                .setFlightCode(req.getFlightCode())
                .build());
    }
}
