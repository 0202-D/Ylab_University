package io.ylab.dao.transaction.action;

import io.ylab.model.Action;

import java.util.ArrayList;
import java.util.List;

public class ActionInMemoryRepository implements ActionRepository {
    public List<Action> actions = new ArrayList<>();

    @Override
    public void addAction(Action action) {
        actions.add(action);
    }
}
