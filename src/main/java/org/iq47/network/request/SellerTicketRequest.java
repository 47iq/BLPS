package org.iq47.network.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SellerTicketRequest {
    Long ticketId;
    Integer price;
    String link;
}
