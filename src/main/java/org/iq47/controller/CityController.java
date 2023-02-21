package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.model.entity.Ticket;
import org.iq47.service.CityService;
import org.iq47.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/cities")
@Slf4j
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/autocomplete")
    private ResponseEntity<?> autocompleteCity(@RequestParam String query) {
        return ResponseEntity.ok().body(cityService.getAutocompleteEntries(query));
    }
}
