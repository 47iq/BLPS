package org.iq47.network.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRequest {
    private String username;
    private String password;
}
