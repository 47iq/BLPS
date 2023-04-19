package org.iq47.service;

import org.iq47.model.entity.User;
import org.iq47.model.entity.UserBalance;
import org.iq47.model.repo2.UserBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserBalanceServiceImpl implements UserBalanceService {

    UserBalanceRepository userBalanceRepository;

    @Autowired
    public UserBalanceServiceImpl(UserBalanceRepository userBalanceRepository) {
        this.userBalanceRepository = userBalanceRepository;
    }

    @Override
    public Optional<UserBalance> editUserBalance(UserBalance userBalance) {
        if (userBalanceRepository.existsById(userBalance.getId())) {
            return Optional.of(userBalanceRepository.save(userBalance));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserBalance> getByUsername(String username) {
        UserBalance userBalance = userBalanceRepository.getByUsername(username);
        if (userBalance == null)
            return Optional.empty();
        else
            return Optional.of(userBalance);
    }
}
