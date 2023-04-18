package org.iq47.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_ticket")
public class UserTicket {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private SellerTicket sellerTicket;

    private Long seatNumber;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

}
