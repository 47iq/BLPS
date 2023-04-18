package org.iq47.model.entity;

import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
public class UserBalance {

    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    private int balance;
}
