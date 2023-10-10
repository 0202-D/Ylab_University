package io.ylab.dao.action;

import io.ylab.model.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionInMemoryRepository implements ActionRepository {
    public List<Action> actions = new ArrayList<>();

    @Override
    public void addAction(Action action) {
        actions.add(action);
    }

    @Override
    public List<Action> getAllByUserName(String userName) {
        return actions
                .stream().filter(el -> el.getUser().getUserName().equals(userName))
                .collect(Collectors.toList());
    }
}
