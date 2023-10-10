package io.ylab.dao.action;

import io.ylab.model.Action;

import java.util.List;


public interface ActionRepository {

    void addAction(Action action);

    List<Action>getAllByUserName(String userName);
}
