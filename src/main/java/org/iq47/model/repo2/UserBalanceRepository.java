package org.iq47.model.repo2;

import org.iq47.model.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {
    UserBalance getByUserId(Long id);
    void editUserBalance(UserBalance userBalance);
}
