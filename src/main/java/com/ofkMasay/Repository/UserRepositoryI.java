package com.ofkMasay.Repository;

import com.ofkMasay.Entity.User;
import java.util.List;

public interface UserRepositoryI {

    List<User> getUsers();
    boolean createUser(User user);
    User getUserById(Long userId);
    boolean updateUser(Long id, User user);
    boolean deleteUser(Long id);
    User findByUsername(String username);

}
