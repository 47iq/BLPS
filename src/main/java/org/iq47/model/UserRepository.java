package org.iq47.model;

import org.iq47.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User findByUsername(String username);
    void save(User user);
}
