package org.iq47.network.request;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
public class TicketRequest implements Serializable {
    LocalDateTime departureDate;
    LocalDateTime arrivalDate;
    String airlineName;
    String flightCode;
    String departureCity;
    String arrivalCity;
    Long id;
}
