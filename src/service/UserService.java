package service;

import repository.UserRepository;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
