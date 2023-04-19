package org.iq47.network.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserTicketRequest {
    Long sellerTicketId;
    Long seatNumber;
    String username;
    Long id;
}
