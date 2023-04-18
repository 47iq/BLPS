package org.iq47.model;

import org.iq47.model.entity.User;

public interface UserRepository {
    User findByUsername(String username);
    void save(User user);
}
