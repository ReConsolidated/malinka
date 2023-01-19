package io.github.reconsolidated.malinka.user;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service for users
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }

    /**
     * service for users
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
