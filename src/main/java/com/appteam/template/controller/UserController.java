package com.appteam.template.controller;

import com.appteam.template.dto.UserData;
import com.appteam.template.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/allUsers")
    public List<UserData> allUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserData addUser(final @RequestBody UserData data) {
        return userService.saveUser(data);
    }

    @GetMapping("/{id}")
    public UserData getUser(final @PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteUser(final @PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
