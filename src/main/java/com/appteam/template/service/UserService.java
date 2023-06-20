package com.appteam.template.service;

import com.appteam.template.data.AuthorizationMethod;
import com.appteam.template.data.Role;
import com.appteam.template.data.User;
import com.appteam.template.dto.UserData;
import com.appteam.template.repository.RoleRepository;
import com.appteam.template.exception.ResourceNotFoundException;
import com.appteam.template.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service("userService")
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public void processOAuthPostLogin(String email) {
        System.out.println("processOAuth2Login invoked");
        User existUser = userRepository.getUserByEmail(email);
        if (existUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setProvider(AuthorizationMethod.GOOGLE);
            newUser.setEnabled(true);

            userRepository.save(newUser);
            System.out.println("added new user");
        }
    }
    public UserService(){}

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserData saveUser(UserData userData) {
        User user = populateUserEntity(userData);
        return populateUserData(userRepository.save(user));
    }

    public Boolean deleteUser(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public List<UserData> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserData> data = new ArrayList<>();
        users.forEach(user -> data.add(populateUserData(user)));
        return data;
    }

    public UserData getUserByEmail(String email) throws EntityNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if (user != null) {
            return populateUserData(user);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    private User populateUserEntity(final UserData data) {
        return new User(data);
    }

    private UserData populateUserData(final User user) {
        return new UserData(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }
}
