package org.iq47.service;

import org.iq47.model.entity.UserBalance;

import java.util.Optional;

public interface UserBalanceService {
    Optional<UserBalance> editUserBalance(UserBalance userBalance);
    Optional<UserBalance> getByUsername(String username);
}
