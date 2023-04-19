package org.iq47.model.repo1;

import org.iq47.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User findByUsername(String username);
    void save(User user);
}
