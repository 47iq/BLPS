package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.TicketDTOConverter;
import org.iq47.model.entity.Ticket;
import org.iq47.model.TicketRepository;
import org.iq47.network.TicketDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository pointRepo;

    @Override
    public Optional<TicketDTO> savePoint(TicketDTO point) {
        //validation
        Ticket ticketEntity = TicketDTOConverter.dtoToEntity(point);
        return Optional.of(TicketDTOConverter.entityToDto(pointRepo.save(ticketEntity)));
    }
}