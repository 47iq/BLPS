package org.iq47.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "departure")
    private LocalDateTime departureDate;

    @Column(name = "arrival")
    private LocalDateTime arrivalDate;

    @Column(name = "code")
    private boolean flightCode;

    @Column(name = "departure_city")
    private boolean departureCity;

    @Column(name = "arrival_city")
    private boolean arrivalCity;
}