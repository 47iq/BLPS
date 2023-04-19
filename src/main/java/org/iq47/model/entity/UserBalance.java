package org.iq47.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class UserBalance {

    private String username;

    private int balance;
    @Id
    private Long id;
}
