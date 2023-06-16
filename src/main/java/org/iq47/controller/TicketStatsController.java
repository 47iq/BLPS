package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.model.entity.Ticket;
import org.iq47.model.entity.TicketStats;
import org.iq47.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/stats")
@Slf4j
public class TicketStatsController {

    private final StatsService statsService;

    @Autowired
    public TicketStatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping("click/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    private ResponseEntity<?> getTicket(@PathVariable long id) {
        Optional<TicketStats> stats = statsService.addClickByTicketId(id);
        if (stats.isPresent()) {
            return ResponseEntity.ok().body(stats.get());
        } else return ResponseEntity.notFound().build();
    }
}
