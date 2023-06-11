package com.appteam.template.service;

import com.appteam.template.data.User;
import com.appteam.template.dto.UserData;
import com.appteam.template.exception.ResourceNotFoundException;
import com.appteam.template.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserService {
    @Autowired
    private UserRepository repo;

    public UserService(UserRepository userRepository) {
        repo = userRepository;
    }

    public UserData saveUser(UserData userData) {
        User user = populateUserEntity(userData);
        return populateUserData(repo.save(user));
    }

    public Boolean deleteUser(String email) {
        User user = repo.getUserByEmail(email);
        if (user != null) {
            repo.delete(user);
            return true;
        }
        return false;
    }

    public List<UserData> getAllUsers() {
        List<User> users = repo.findAll();
        List<UserData> data = new ArrayList<>();
        users.forEach(user -> data.add(populateUserData(user)));
        return data;
    }

    public UserData getUserByEmail(String email) throws EntityNotFoundException {
        User user = repo.getUserByEmail(email);
        if (user != null) {
            return populateUserData(user);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    private User populateUserEntity(final UserData data) {
        User user = new User();
        user.setIdShopify(data.getIdShopify());
        user.setProvider(data.getAuthorizationMethod());
        user.setEmail(data.getEmail());
        user.setPassword(data.getPassword());
        user.setEnabled(data.isEnabled());
        user.setOrdersSendTime(data.getOrdersSendTime());
        user.setUpdateTime(data.getUpdateTime());
        user.setShops(data.getShops());
        return user;
    }

    private UserData populateUserData(final User user) {
        return new UserData(user);
    }
}
