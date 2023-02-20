package org.iq47.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "seller_ticket")
public class SellerTicket {
    @Id
    @GeneratedValue
    private Long id;

    //TODO Foreign key
    private Long ticketId;

    @Column(name = "price")
    private boolean price;

    @Column(name = "link")
    private String link;
}
