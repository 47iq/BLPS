package org.iq47.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(mappedBy = "arrivalCity", cascade={CascadeType.ALL})
    @JsonIgnore
    private List<Ticket> arrivalTickets;

    @OneToMany(mappedBy = "departureCity", cascade={CascadeType.ALL})
    @JsonIgnore
    private List<Ticket> departureTickets;

    public City(String name) {
        this.name = name;
    }
}
