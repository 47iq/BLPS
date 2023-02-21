package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.model.CityRepository;
import org.iq47.model.entity.City;
import org.iq47.model.entity.SellerTicket;
import org.iq47.model.entity.Ticket;
import org.iq47.model.TicketRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepo;
    private final CityRepository cityRepository;

    @Override
    public Optional<Ticket> saveTicket(Ticket ticket) {
        Ticket ticket1 = ticketRepo.save(ticket);
        return Optional.of(ticket1);
    }

    @Override
    public Optional<Ticket> editTicket(Ticket ticket) {
        if (ticketRepo.existsById(ticket.getId())) {
            return Optional.of(ticketRepo.save(ticket));
        }
        return Optional.empty();
    }

    @Override
    public List<Ticket> findTickets(String departureCity, String arrivalCity, LocalDateTime departureDate, LocalDateTime arrivalDate) {
        Optional<City> depCity = cityRepository.getCityByName(departureCity);
        Optional<City> arrCity = cityRepository.getCityByName(arrivalCity);

        if (!depCity.isPresent() || !arrCity.isPresent()) {
            return null;
        }

        if (departureDate == null) {
            Optional<List<Ticket>> tickets = ticketRepo.getTicketsByDepartureCityAndArrivalCityAndArrivalDate(
                    depCity.get(), arrCity.get(), arrivalDate
            );
            return tickets.orElse(null);
        }

        Optional<List<Ticket>> tickets = ticketRepo.getTicketsByDepartureCityAndArrivalCityAndArrivalDateAndDepartureDate(
                depCity.get(), arrCity.get(), arrivalDate, departureDate
        );
        return tickets.orElse(null);

    }

    @Override
    public List<Ticket> averageTicketsPrice(String departureCity, String arrivalCity, LocalDateTime flightDate) {
        return null;
    }

    @Override
    public Optional<Ticket> getTicketById(long id) {
        Ticket ticket = ticketRepo.getById(id);
        if (ticket == null) return Optional.empty();
        return Optional.of(ticket);
    }

    @Override
    public boolean deleteTicket(long id) {
        ticketRepo.deleteById(id);
        return true;
    }
}
