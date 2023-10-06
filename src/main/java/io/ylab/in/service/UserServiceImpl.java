package io.ylab.in.service;

import io.ylab.model.User;

public class UserServiceImpl implements UserService{
    @Override
    public void balance(User user) {
        System.out.println("Ваш баланс = "+user.getBalance());
    }
}
