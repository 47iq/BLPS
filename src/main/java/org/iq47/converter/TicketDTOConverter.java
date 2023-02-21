package org.iq47.converter;

import org.iq47.model.entity.Ticket;
import org.iq47.network.TicketDTO;

public class TicketDTOConverter {
    public static Ticket dtoToEntity(TicketDTO ticketDto) {
        return new Ticket(ticketDto.getId(), ticketDto.getDepartureDate(),
                ticketDto.getArrivalDate(), ticketDto.getAirlineName(),
                ticketDto.getFlightCode(), ticketDto.getDepartureCity(),
                ticketDto.getArrivalCity());
    }

    public static TicketDTO entityToDto(Ticket ticketEntity) {
        return TicketDTO.newBuilder()
                .setId(ticketEntity.getId())
                .setDepartureDate(ticketEntity.getDepartureDate())
                .setArrivalDate(ticketEntity.getArrivalDate())
                .setAirlineName(ticketEntity.getAirlineName())
                .setFlightCode(ticketEntity.getFlightCode())
                .setDepartureCity(ticketEntity.getDepartureCity())
                .setArrivalCity(ticketEntity.getArrivalCity())
                .build();
    }
}
