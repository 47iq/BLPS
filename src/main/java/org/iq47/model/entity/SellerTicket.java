package org.iq47.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    @JsonBackReference
    private Ticket ticket;

    @Column(name = "price")
    private Integer price;

    @Column(name = "link")
    private String link;

    @OneToOne(mappedBy = "sellerTicket")
    private TicketStats ticketStats;


    public static SellerTicket.Builder newBuilder() {
        return new SellerTicket().new Builder();
    }

    @Override
    public String toString() {
        return "SellerTicket{" +
                "id=" + id +
                ", ticket=" + ticket +
                ", price=" + price +
                ", link='" + link + '\'' +
                ", ticketStats=" + ticketStats +
                '}';
    }

    public class Builder {

        private Builder() {
        }

        public SellerTicket.Builder setId(Long id) {
            SellerTicket.this.id = id;
            return this;
        }

        public SellerTicket.Builder setPrice(Integer price) {
            SellerTicket.this.price = price;
            return this;
        }

        public SellerTicket.Builder setLink(String link) {
            SellerTicket.this.link = link;
            return this;
        }

        public SellerTicket.Builder setTicket(Ticket ticket) {
            SellerTicket.this.ticket = ticket;
            return this;
        }

        public SellerTicket build() {
            return SellerTicket.this;
        }

    }
}
