package com.appteam.template.service;

import com.appteam.template.data.AuthorizationMethod;
import com.appteam.template.data.User;
import com.appteam.template.dto.OrderData;
import com.appteam.template.dto.UserData;
import com.appteam.template.exception.ResourceNotFoundException;
import com.appteam.template.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
    UserService userService = new UserService(userRepositoryMock);
    Random gen = new Random();
    List<User> userList = new ArrayList<>();

    {
        Mockito.when(userRepositoryMock.getUserByEmail(Mockito.any(String.class))).thenReturn(null);
        for (int i = 0; i < 100; i++) {
            Long id = gen.nextLong();
<<<<<<< HEAD
            UserData userData = new UserData(id, id.toString(), "", AuthorizationMethod.GOOGLE, 0, 0, new HashSet<>());
=======
            UserData userData = new UserData(id, id.toString(), "", AuthorizationMethod.GOOGLE, 0, 0, new ArrayList<>());
>>>>>>> user-database
            User user = new User(userData);
            userList.add(user);
            Mockito.when(userRepositoryMock.save(user)).thenReturn(user);
            Mockito.when(userRepositoryMock.getUserByEmail(user.getEmail())).thenReturn(user);
        }
        Mockito.when(userRepositoryMock.findAll()).thenReturn(userList);
    }

    @Test
    void saveUserTest() {
        for (User user : userList) {
            UserData data = new UserData(user);
            assertEquals(data, userService.saveUser(data));
        }
    }

    @Test
    void deleteUserTest() {
        for (User user : userList) {
            UserData data = new UserData(user);
            assertEquals(true, userService.deleteUser(data.getEmail()));
        }
    }

    @Test
    void getAllUsersTest() {
        List<UserData> users = userService.getAllUsers();
        assertEquals(userList.size(), users.size());
        for (int i = 0; i < userList.size(); i++) {
            assertEquals(userList.get(i), new User(users.get(i)));
        }
    }

    @Test
    void getUserByEmailTest() {
        Map<String, String> userMap = new HashMap<>();
        for (User user : userList) {
            userMap.put(user.getEmail(), user.getEmail());
        }
        for (int i = 0; i < 50; i++) {
            String email = Long.toString(gen.nextLong());
            if (userMap.containsKey(email)) {
                UserData data = new UserData();
                data.setEmail(email);
                assertEquals(data, userService.getUserByEmail(email));
            } else {
                assertThrows(ResourceNotFoundException.class,
                        () -> userService.getUserByEmail(email),
                        "User not found");
            }
        }
    }
}
