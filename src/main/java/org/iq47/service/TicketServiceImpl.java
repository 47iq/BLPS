package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.model.repo1.CityRepository;
import org.iq47.model.entity.City;
import org.iq47.model.entity.SellerTicket;
import org.iq47.model.entity.Ticket;
import org.iq47.model.repo1.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Ticket> findTickets(String departureCity, String arrivalCity, Date departureDate, Date arrivalDate, ZoneId zoneId, int pageNum) {
        Optional<City> depCity = cityRepository.getCityByName(departureCity);
        Optional<City> arrCity = cityRepository.getCityByName(arrivalCity);

        if (!depCity.isPresent() || !arrCity.isPresent()) {
            return null;
        }

        LocalDateTime departureDateTime = departureDate.toInstant()
                .atZone(zoneId)
                .toLocalDateTime();

        if (arrivalDate == null) {
            List<Ticket> tickets = ticketRepo.getTicketsByDepartureCityAndArrivalCity(PageRequest.of(pageNum, 20), depCity.get(), arrCity.get(), departureDateTime);
            return tickets;
        }

        LocalDateTime arrivalDateTime = arrivalDate.toInstant()
                .atZone(zoneId)
                .toLocalDateTime();

        Page<Ticket> tickets = ticketRepo.getTicketsByDepartureCityAndArrivalCity(PageRequest.of(pageNum, 20), depCity.get(), arrCity.get(), departureDateTime, arrivalDateTime);
        return tickets.toList();

    }

    @Override
    public Optional<Double> averageTicketsPrice(String departureCity, String arrivalCity, Date flightDate, ZoneId zoneId) {
        if (departureCity != null && arrivalCity != null) {
            Optional<City> arrCity = cityRepository.getCityByName(arrivalCity);
            Optional<City> depCity = cityRepository.getCityByName(departureCity);

            if (arrCity.isPresent() && depCity.isPresent()) {
                List<Ticket> tickets = ticketRepo.getTicketsByDepartureCityAndArrivalCity(depCity.get(), arrCity.get());
                tickets = tickets.stream()
                        //.filter(t -> Date.from(t.getDepartureDate().toInstant(ZoneOffset.ofHours(0))).equals(flightDate))
                        .filter(t ->  t.getDepartureDate().toLocalDate().equals(flightDate.toInstant().atZone(zoneId).toLocalDate()))
                        .collect(Collectors.toList());

                int sum = 0;
                int count = 0;

                for (Ticket t : tickets) {
                    for (SellerTicket st : t.getSellerTickets()) {
                        sum += st.getPrice();
                        count++;
                    }
                }
                if (count == 0)
                    return Optional.empty();

                return Optional.of((double) sum / count);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Ticket> getTicketById(long id) {
        return ticketRepo.findById(id);
    }

    @Override
    public boolean deleteTicket(long id) {
        if (!ticketRepo.existsById(id))
            return false;
        ticketRepo.deleteById(id);
        return true;
    }
}
