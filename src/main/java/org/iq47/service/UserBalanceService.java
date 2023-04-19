package org.iq47.service;

import org.iq47.model.entity.UserBalance;
import org.iq47.model.entity.UserTicket;

import java.util.Optional;

public interface UserBalanceService {
    Optional<UserBalance> editUserBalance(UserBalance userBalance);
    Optional<UserBalance> getByUsername(String username);

    boolean delete(long id);

    Optional<UserBalance> save(UserBalance userBalance);

    Optional<UserBalance> getById(long id);

    Optional<UserBalance> edit(UserBalance userBalance);
}
