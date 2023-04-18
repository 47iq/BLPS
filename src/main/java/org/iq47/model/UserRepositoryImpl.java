package org.iq47.model;

import org.iq47.model.entity.User;
import org.iq47.model.entity.Users;
import org.iq47.utils.XMLUtils;

import java.util.List;


public class UserRepositoryImpl implements UserRepository{
    private final XMLUtils xmlUtil;
    private final String xmlPath = "C:\\Users\\user\\IdeaProjects\\BL_lab1\\src\\main\\resources\\users.xml";

    public UserRepositoryImpl(XMLUtils xmlUtil) {
        this.xmlUtil = xmlUtil;
    }

    @Override
    public User findByUsername(String username) {
        Users users = (Users)xmlUtil.getEntity(Users.class, "userList", xmlPath);
        if (users==null || users.getUser()==null) return null;
        List<User> userEntities = users.getUser();
        for (User cur: userEntities) {
            if (cur.getUsername().equals(username)) return cur;
        }
        return null;
    }

    @Override
    public void save(User user) {
        Users users = (Users)xmlUtil.getEntity(Users.class, "users", xmlPath);
        if (users==null) users = new Users();
        users.getUser().add(user);
        xmlUtil.saveEntity(users.getUser(), xmlPath);
    }
}
