package org.iq47.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ticket_stats")
public class TicketStats {

    @Id
    @GeneratedValue
    private Long id;

    //TODO Foreign key
    private Long sellerTicketId;

    @Column(name = "clicks")
    private int clickCount;

    public void incrementClickCountByOne() {
        clickCount++;
    }
}
