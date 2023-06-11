package com.appteam.template.controller;

import com.appteam.template.dto.UserData;
import com.appteam.template.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<UserData>> allUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserData> addUser(final @RequestBody UserData data) {
        return new ResponseEntity<>(userService.saveUser(data), HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserData> getUser(final @PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Boolean> deleteUser(final @PathVariable String email) {
        return new ResponseEntity<>(userService.deleteUser(email), HttpStatus.OK);
    }
}
