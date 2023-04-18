package com.appteam.template.service;

import com.appteam.template.data.User;
import com.appteam.template.dto.UserData;
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
    /*
        public void processOAuthPostLogin(String username) {
            User existUser = repo.getUserByUsername(username);

            if (existUser == null) {
                User newUser = new User();
                newUser.setUserName(username);
                newUser.setProvider(Provider.GOOGLE);
                //newUser.setEnabled(true);

                repo.save(newUser);
            }
        }
    */
    public UserService(UserRepository userRepositoryMock) {
        repo = userRepositoryMock;
    }

    public UserData saveUser(UserData userData) {
        User user = populateUserEntity(userData);
        return populateUserData(repo.save(user));
    }

    public Boolean deleteUser(Long orderId) {
        repo.deleteById(orderId);
        return true;
    }

    public List<UserData> getAllUsers() {
        List<User> users = repo.findAll();
        List<UserData> data = new ArrayList<>();
        users.forEach(order -> data.add(populateUserData(order)));
        return data;
    }

    public UserData getUserById(Long id) {
        return populateUserData(repo.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("User not found"))
        );
    }

    private User populateUserEntity(final UserData data) {
        User user = new User();
        user.setId(data.getId());
        user.setProvider(data.getProvider());
        user.setEmail(data.getEmail());
        user.setPassword(data.getPassword());
        user.setEnabled(data.isEnabled());
        user.setOrdersSendTime(data.getOrdersSendTime());
        user.setUpdateTime(data.getUpdateTime());
        return user;
    }

    private UserData populateUserData(final User user) {
        UserData data = new UserData();
        data.setId(user.getId());
        data.setEmail(user.getEmail());
        data.setPassword(user.getPassword());
        data.setEnabled(user.isEnabled());
        data.setProvider(user.getProvider());
        data.setOrdersSendTime(user.getOrdersSendTime());
        data.setUpdateTime(user.getUpdateTime());
        return data;
    }

}
