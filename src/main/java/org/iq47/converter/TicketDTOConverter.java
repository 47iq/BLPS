package org.iq47.converter;

import org.iq47.model.entity.Ticket;
import org.iq47.network.TicketDTO;

public class TicketDTOConverter {
    public static Ticket dtoToEntity(TicketDTO ticketDto) {
        return new Ticket(ticketDto.getCoordinateX(), ticketDto.getCoordinateY(), ticketDto.getRadius());
    }

    public static TicketDTO entityToDto(Ticket ticketEntity) {
        return TicketDTO.newBuilder()
                .setCoordinateX(ticketEntity.getDepartureDate())
                .setCoordinateY(ticketEntity.getArrivalDate())
                .setLocalTime(ticketEntity.getLdt())
                .setHit(ticketEntity.getPrice())
                .setRadius(ticketEntity.getLink())
                .setPointId(ticketEntity.getId()).build();
    }
}
