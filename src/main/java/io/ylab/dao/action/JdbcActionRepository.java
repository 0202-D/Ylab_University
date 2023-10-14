package io.ylab.dao.action;

import io.ylab.dao.user.JdbcUserRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.model.Action;
import io.ylab.model.Activity;
import io.ylab.model.User;
import io.ylab.utils.DataBaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcActionRepository implements ActionRepository {
    private final UserRepository userRepository = new JdbcUserRepository();
    Connection connection = DataBaseConnector.getConnection();

    @Override
    public void addAction(Action action) {
        String query = "INSERT INTO domain.action (user_id,activity) VALUES(?, ?)";
        try (PreparedStatement statement =
                     connection.prepareStatement(query)) {
            statement.setLong(1, action.getUser().getUserId());
            statement.setString(2, action.getActivity().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Action> getAllByUserName(String userName) {
        List<Action> actions = new ArrayList<>();
        String query = "SELECT a.action_id, a.activity" +
                " FROM domain.action a" +
                " INNER JOIN domain.user u ON a.user_id = u.user_id" +
                " WHERE u.user_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long actionId = resultSet.getLong("action_id");
                Activity activity = Activity.valueOf(resultSet.getString("activity"));
                User user = userRepository.getByName(userName)
                        .orElseThrow(() -> new RuntimeException("Такого пользователя не существует"));
                Action action = new Action();
                action.setActionId(actionId);
                action.setUser(user);
                action.setActivity(activity);
                actions.add(action);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actions;
    }
}
