package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.iq47.exception.TicketSaveException;
import org.iq47.model.entity.City;
import org.iq47.model.entity.Ticket;
import org.iq47.network.request.CityRequest;
import org.iq47.network.request.TicketRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.service.CityService;
import org.iq47.validate.CityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/cities")
@Slf4j
public class CityController {

    private final CityService cityService;
    private final CityValidator cityValidator;

    @Autowired
    public CityController(CityService cityService, CityValidator cityValidator) {
        this.cityService = cityService;
        this.cityValidator = cityValidator;
    }

    @GetMapping("/autocomplete")
    private ResponseEntity<?> autocompleteCity(@RequestParam String query) {
        return ResponseEntity.ok().body(cityService.getAutocompleteEntries(query));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CityRequest req) {
        try {
            Optional<String> error = cityValidator.getErrorMessage(req.getName());
            if(error.isPresent())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper(error.get()));
            City city = new City(req.getName());
            Optional<City> cityOptional = cityService.saveCity(city);
            if (!cityOptional.isPresent()) {
                throw new TicketSaveException("City has not been saved.");
            }
            return ResponseEntity.ok().body(cityOptional.get());
        } catch (TicketSaveException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(e.getMessage()));
        } catch (Exception e) {
            return reportError(req, e);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            boolean isDeleted = cityService.deleteCity(id);
            if (!isDeleted) {
                throw new TicketSaveException("City has not been saved.");
            }
            return ResponseEntity.ok().body(null);
        } catch (TicketSaveException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(e.getMessage()));
        } catch (Exception e) {
            log.error(String.format("Got %s while deleting seller ticket %s", e.getClass(), id));
            return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
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
