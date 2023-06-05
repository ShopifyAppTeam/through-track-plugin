package com.appteam.template.service;

//import com.appteam.template.data.Provider;
import com.appteam.template.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class ParamsService {
    @Autowired
    private UserService userService;

    public void setShipmentTimeParam(int time) {
        Long userId = 777L; // mysterious number
        UserData user = userService.getUserById(userId);
        user.setOrdersSendTime(time);
        userService.saveUser(user);

    }
    public void setUpdateTimeParam(int time) {
        Long userId = 777L; // mysterious number
        UserData user = userService.getUserById(userId);
        user.setUpdateTime(time);
        userService.saveUser(user);
    }
}