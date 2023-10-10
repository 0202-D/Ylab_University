package io.ylab.dao.user;

import io.ylab.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserInMemoryRepository implements UserRepository{

    public List<User> users = new ArrayList<>();


    public void addUser(User user){
        users.add(user);
    }

    @Override
    public Optional<User> getByName(String userName) {
        return users.stream().filter(e -> e.getUserName().equals(userName)).findFirst();
    }


}
