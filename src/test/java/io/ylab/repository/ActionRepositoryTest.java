package io.ylab.repository;

import io.ylab.Utils;
import io.ylab.dao.action.JdbcActionRepository;
import io.ylab.dao.user.JdbcUserRepository;
import io.ylab.model.Action;
import io.ylab.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ActionRepositoryTest {
    @Autowired
    private JdbcActionRepository actionRepository;

    @Autowired
    private JdbcUserRepository userRepository;

    @Test
    @DisplayName("Транзакция должна успешно добавиться")
    void testAddAction() {
        User user = Utils.getUser();
        Action action = Utils.getHistoryAction(user);
        actionRepository.addAction(action);
        List<Action> actions = actionRepository.getAllByUserName(user.getUserName());
        Assertions.assertFalse(actions.isEmpty());
    }

    @Test
    @DisplayName("Должна быть корректно найдены транзакции по имени пользователя")
    void testGetAllByUserName() {
        User user = Utils.getUser();
        String userName = user.getUserName();
        List<Action> actions = actionRepository.getAllByUserName(userName);
        Assertions.assertFalse(actions.isEmpty());
        for (Action action : actions) {
            Assertions.assertEquals(userName, action.getUser().getUserName());
        }
    }
}
