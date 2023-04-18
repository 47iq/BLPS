package org.iq47.network.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExchangeTicketRequest {
    long oldTicketId;
    long newTicketId;
    String username;
    int price;
}
