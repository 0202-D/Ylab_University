package io.ylab.in.dao;

import io.ylab.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    public final Map<String, String> users = new HashMap<String, String>();

    public  final List<User> list = new ArrayList<>();
}
