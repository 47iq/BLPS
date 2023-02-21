package org.iq47.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "city")
public class City {
    @Id
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "arrivalCity")
    private List<Ticket> arrivalTickets;

    @OneToMany(mappedBy = "departureCity")
    private List<Ticket> departureTickets;

    public City(String name) {
        this.name = name;
    }
}
