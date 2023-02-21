package org.iq47.network;

import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class TicketDTO {

    private Long id;

    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;

    private String airlineName;

    private String flightCode;

    private String departureCity;

    private String arrivalCity;

    public static Builder newBuilder() {
        return new TicketDTO().new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDTO ticketDTO = (TicketDTO) o;
        return flightCode == ticketDTO.flightCode && Objects.equals(id, ticketDTO.id) && Objects.equals(departureDate, ticketDTO.departureDate) && Objects.equals(arrivalDate, ticketDTO.arrivalDate) && Objects.equals(departureCity, ticketDTO.departureCity) && Objects.equals(arrivalCity, ticketDTO.arrivalCity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departureDate, arrivalDate, flightCode, departureCity, arrivalCity);
    }

    //builder
    public class Builder {
        private Builder() {
        }

        public Builder setDepartureDate(LocalDateTime departureDate) {
            TicketDTO.this.departureDate = departureDate;
            return this;
        }

        public Builder setArrivalDate(LocalDateTime arrivalDate) {
            TicketDTO.this.arrivalDate = arrivalDate;
            return this;
        }

        public Builder setAirlineName(String airlineName) {
            TicketDTO.this.airlineName = airlineName;
            return this;
        }

        public Builder setFlightCode(String flightCode) {
            TicketDTO.this.flightCode = flightCode;
            return this;
        }

        public Builder setDepartureCity(String departureCity) {
            TicketDTO.this.departureCity = departureCity;
            return this;
        }

        public Builder setArrivalCity(String arrivalCity) {
            TicketDTO.this.arrivalCity = arrivalCity;
            return this;
        }

        public Builder setId(Long id) {
            TicketDTO.this.id = id;
            return this;
        }


        public TicketDTO build() {
            return TicketDTO.this;
        }
    }
}
