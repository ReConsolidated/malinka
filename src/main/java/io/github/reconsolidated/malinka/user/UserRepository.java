package io.github.reconsolidated.malinka.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * repository for user
 */
@Repository
public class UserRepository {

    private final List<User> users;

    public UserRepository() {
       users = new ArrayList<>();

       users.add(new User(1L, "jkowal", "Jan", "Kowalski", "someHashedPassword", 120));
    }

    public List<User> getUsers() {
        return users;
    }

    /**
     * get usert by username
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }
}
