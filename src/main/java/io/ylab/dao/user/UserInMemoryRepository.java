package io.ylab.dao.user;

import io.ylab.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserInMemoryRepository implements UserRepository{

    public List<User> users = new ArrayList<>();


    public void addUser(User user){
        users.add(user);
    }


}
