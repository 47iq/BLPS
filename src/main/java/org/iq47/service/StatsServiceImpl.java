package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.model.TicketStatsRepository;
import org.iq47.model.entity.TicketStats;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    TicketStatsRepository ticketStatsRepository;
    @Override
    public Optional<TicketStats> addClickByTicketId(long id) {
        TicketStats stats = ticketStatsRepository.getById(id);
        stats.incrementClickCountByOne();
        stats = ticketStatsRepository.save(stats);
        return Optional.of(stats);
    }
}
