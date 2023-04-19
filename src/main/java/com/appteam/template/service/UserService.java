package com.appteam.template.service;

import com.appteam.template.data.Provider;
import com.appteam.template.data.Role;
import com.appteam.template.data.User;
import com.appteam.template.dto.UserData;
import com.appteam.template.repository.RoleRepository;
import com.appteam.template.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("userService")
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

        public void processOAuthPostLogin(String email) {
            User existUser = userRepository.getUserByEmail(email);
            if (existUser == null) {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setProvider(Provider.GOOGLE);
                newUser.setEnabled(true);
                userRepository.save(newUser);
            }
        }
    public UserService(UserRepository userRepositoryMock) {
        userRepository = userRepositoryMock;
    }

    public UserData saveUser(UserData userData) {
        User user = populateUserEntity(userData);
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        return populateUserData(userRepository.save(user));
    }

    public Boolean deleteUser(Long orderId) {
        userRepository.deleteById(orderId);
        return true;
    }

    public List<UserData> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserData> data = new ArrayList<>();
        users.forEach(order -> data.add(populateUserData(order)));
        return data;
    }

    public UserData getUserById(Long id) {
        return populateUserData(userRepository.findById(id)
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
        user.setRoles(data.getRoles());
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
        data.setRoles(user.getRoles());
        return data;
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
