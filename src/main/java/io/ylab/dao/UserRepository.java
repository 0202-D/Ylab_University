package io.ylab.dao;

import io.ylab.model.Action;
import io.ylab.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public List<User> users = new ArrayList<>();

    public List<Action> actions = new ArrayList<>();
    public void addUser(User user){
        users.add(user);
    }

    public void addAction(Action action){
        actions.add(action);
    }
}
