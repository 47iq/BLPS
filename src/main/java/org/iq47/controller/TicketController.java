package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.exception.TicketSaveException;
import org.iq47.message.TicketReportMessage;
import org.iq47.model.entity.City;
import org.iq47.model.entity.Ticket;
import org.iq47.network.request.TicketRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.producer.JMSMessageSender;
import org.iq47.service.CityService;
import org.iq47.service.TicketService;
import org.iq47.validate.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/tickets")
@Slf4j
public class TicketController {

    private final TicketService ticketService;
    private final TicketValidator ticketValidator;
    private final CityService cityService;

    private final JMSMessageSender sender;

    @Autowired
    public TicketController(TicketService ticketService, TicketValidator ticketValidator, CityService cityService, JMSMessageSender sender) {
        this.ticketService = ticketService;
        this.ticketValidator = ticketValidator;
        this.cityService = cityService;
        this.sender = sender;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            boolean isDeleted = ticketService.deleteTicket(id);
            if (!isDeleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable long id, @RequestBody TicketRequest req) {
        try {
            Optional<String> error = ticketValidator.getErrorMessage(req);
            if(error.isPresent())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper(error.get()));
            return edit(req, id);
        } catch (TicketSaveException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return reportError(req, e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicket(@PathVariable long id) {
        Optional<Ticket> item = ticketService.getTicketById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok().body(item.get());
        } else return ResponseEntity.notFound().build();
    }


    @PostMapping("generate_report/{airline_name}")
    public ResponseEntity<?> generateReport(@PathVariable String airline_name) {
        try {
            sender.sendTicketReportMessage(new TicketReportMessage(airline_name));
        } catch (JMSException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
        }
        return ResponseEntity.ok().body("Report generation has been started.");
    }


    @GetMapping("/find")
    public ResponseEntity<?> get(
            @RequestParam String departureCity,
            @RequestParam String arrivalCity,
            @DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam Date departureDate,
            @DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam(required = false) Date returnBackDate,
            @RequestParam int zoneOffsetHoursGMT,
            @RequestParam int pageNum
    ) {
        try {
            ZoneId zoneId = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(zoneOffsetHoursGMT));
            List<Ticket> tickets = ticketService.findTickets(departureCity, arrivalCity, departureDate, returnBackDate, zoneId, pageNum);
            return ResponseEntity.ok().body(tickets);
        } catch (Exception e) {
            return reportError(null, e);
        }
    }

    @GetMapping("/average_price")
    public ResponseEntity<?> get(
            @RequestParam String departureCity,
            @RequestParam String arrivalCity,
            @DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam Date flightDate,
            @RequestParam int zoneOffsetHoursGMT
    ) {
        try {
            ZoneId zoneId = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(zoneOffsetHoursGMT));
            Optional<Double> price = ticketService.averageTicketsPrice(departureCity, arrivalCity, flightDate, zoneId);
            if (!price.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok().body(new ResponseWrapper(price.get().toString()));
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
        Optional<Ticket> ticket = buildTicket(req, null);
        if (ticket.isPresent()) {
            Optional<Ticket> ticketOptional = ticketService.saveTicket(ticket.get());
            if (!ticketOptional.isPresent()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("Ticket has not been saved."));
            }
            return ResponseEntity.ok().body(ticketOptional.get());
        }
        return ResponseEntity.badRequest().body(new ResponseWrapper("City not found."));
    }

    private ResponseEntity<?> edit(TicketRequest req, Long id) throws TicketSaveException {
        Optional<Ticket> ticket = buildTicket(req, id);
        if (ticket.isPresent()) {
            Optional<Ticket> ticketOptional = ticketService.editTicket(ticket.get());
            if (!ticketOptional.isPresent()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper("Ticket has not been edited."));
            }
            return ResponseEntity.ok().body(ticketOptional.get());
        }

        return ResponseEntity.badRequest().body(new ResponseWrapper("Ticket/city not found."));
    }

    private Optional<Ticket> buildTicket(TicketRequest req, Long id) {
        Optional<City> depCity = cityService.getCityByName(req.getDepartureCity());
        if (!depCity.isPresent()) return Optional.empty();
        Optional<City> arrCity = cityService.getCityByName(req.getArrivalCity());
        if (!arrCity.isPresent()) return Optional.empty();

        return Optional.of(Ticket.newBuilder()
                .setId(id)
                .setDepartureCity(depCity.get())
                .setArrivalCity(arrCity.get())
                .setDepartureDate(req.getDepartureDate())
                .setArrivalDate(req.getArrivalDate())
                .setAirlineName(req.getAirlineName())
                .setFlightCode(req.getFlightCode())
                .build());
    }
}
