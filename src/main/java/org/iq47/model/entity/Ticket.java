package org.iq47.model.entity;

import lombok.AllArgsConstructor;
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
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "ticket")
    private List<SellerTicket> sellerTickets;

    @Column(name = "departure")
    private LocalDateTime departureDate;

    @Column(name = "arrival")
    private LocalDateTime arrivalDate;

    @Column(name = "airline_name")
    private String airlineName;

    @Column(name = "code")
    private String flightCode;

    @ManyToOne
    @JoinColumn(name = "departure_city", nullable = false)
    private City departureCity;

    @ManyToOne
    @JoinColumn(name = "arrival_city", nullable = false)
    private City arrivalCity;

    public static Builder newBuilder() {
        return new Ticket().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(Long id) {
            Ticket.this.id = id;
            return this;
        }

        public Builder setDepartureDate(LocalDateTime departureDate) {
            Ticket.this.departureDate = departureDate;
            return this;
        }

        public Builder setAirlineName(String airlineName) {
            Ticket.this.airlineName = airlineName;
            return this;
        }

        public Builder setArrivalDate(LocalDateTime arrivalDate) {
            Ticket.this.arrivalDate = arrivalDate;
            return this;
        }

        public Builder setFlightCode(String flightCode) {
            Ticket.this.flightCode = flightCode;
            return this;
        }

        public Builder setDepartureCity(City departureCity) {
            Ticket.this.departureCity = departureCity;
            return this;
        }

        public Builder setArrivalCity(City arrivalCity) {
            Ticket.this.arrivalCity = arrivalCity;
            return this;
        }

        public Ticket build() {
            return Ticket.this;
        }

    }
}