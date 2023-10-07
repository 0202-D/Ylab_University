package io.ylab.in.service;

import io.ylab.in.dao.UserRepository;
import io.ylab.model.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Scanner scanner = new Scanner(System.in);
    private final UserRepository userRepository;

    public boolean addUser() {
        String [] nameAndPassword = passwordRequest();
        String userName = nameAndPassword[0];
        String password = nameAndPassword[1];
        Optional<User> user = userRepository.list.stream().filter(e->e.getUserName().equals(userName)).findFirst();
        if(user.isPresent()){ System.out.println("Пользователь с таким именем уже существует!");
            return false;}

        else {
            User user1 = new User();
            user1.setUserName(userName);
            user1.setPassword(password);
            user1.setBalance(new BigDecimal(0));
            userRepository.list.add(user1);
            System.out.println("Вы зарегестрированы!");
            System.out.println("**********************");
            return true;
        }
    }

    public User authenticateUser() {
        String [] nameAndPassword = passwordRequest();
        String userName = nameAndPassword[0];
        String password = nameAndPassword[1];
        Optional<User> user = userRepository.list.stream().filter(e->e.getUserName().equals(userName)).findFirst();
        if(user.isEmpty()){
            System.out.println("Такого пользователя не существует!");
            System.out.println("**********************");
            return null;
        }
        if(!user.get().getPassword().equals(password)){
            System.out.println("Не верный пароль");
            System.out.println("**********************");
            return null;
        }

        System.out.println("Вы вошли");
        System.out.println("**********************");
        return user.get();
    }
    public static String [] passwordRequest(){
        System.out.println("Введите через пробел ваш логин и пароль");
        String input = scanner.nextLine();
        String [] nameAndPassword = input.split(" ");
        if(nameAndPassword.length!=2){
            throw new RuntimeException("Введены не вернуе данные!");
        }
        return nameAndPassword;
    }
}
