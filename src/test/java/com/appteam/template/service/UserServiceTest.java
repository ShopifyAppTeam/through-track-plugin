package com.appteam.template.service;

import com.appteam.template.data.Provider;
import com.appteam.template.data.User;
import com.appteam.template.dto.OrderData;
import com.appteam.template.dto.UserData;
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
        Mockito.when(userRepositoryMock.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());
        for (int i = 0; i < 100; i++) {
            Long id = gen.nextLong();

            UserData userData = new UserData(id, id, id.toString(), "", Provider.GOOGLE, 0, 0, new HashSet<>());

            User user = new User(userData);
            userList.add(user);
            Mockito.when(userRepositoryMock.save(user)).thenReturn(user);
            Mockito.when(userRepositoryMock.findById(user.getId())).thenReturn(Optional.of(user));
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
            assertEquals(true, userService.deleteUser(data.getId()));
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
    void getUserByIdTest() {
        Map<Long, Long> orderMap = new HashMap<>();
        for (User user : userList) {
            orderMap.put(user.getId(), user.getId());
        }
        for (int i = 0; i < 50; i++) {
            Long id = gen.nextLong();
            if (orderMap.containsKey(id)) {
                OrderData data = new OrderData(id, "");
                assertEquals(data, userService.getUserById(id));
            } else {
                assertThrows(EntityNotFoundException.class,
                        () -> userService.getUserById(id),
                        "User not found");
            }
        }
    }
}
