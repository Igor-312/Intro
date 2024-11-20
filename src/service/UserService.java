package service;

import models.User;
import repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    // Метод для получения всех пользователей
    public List<User> getAllUsers() {
        return userRepository.allUsers();
    }

    // Метод для получения всех пользователей в виде Map (например, с использованием ID как ключа)
    public Map<Integer, User> getAllUsersAsMap() {
        List<User> userList = userRepository.allUsers();
        Map<Integer, User> userMap = new HashMap<>();

        // Преобразуем список в Map
        for (User user : userList) {
            userMap.put(user.getId(), user);  // Предполагаем, что у класса User есть метод getId()
        }

        return userMap;
    }

    // Метод для добавления пользователя
    public void addUser(User user) {
        userRepository.addUser(user);
    }

    // Метод для создания аккаунта
    public void createAccount(String currencyCode, double initialBalance) {
        userRepository.createAccount(currencyCode, initialBalance);
    }
}
