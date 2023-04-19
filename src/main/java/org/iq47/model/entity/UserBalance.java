package org.iq47.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class UserBalance {

    @Unique
    private String username;

    private int balance;
    @Id
    @GeneratedValue
    private Long id;
}
