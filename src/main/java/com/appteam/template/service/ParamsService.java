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
        String email = "aboba@gmail.com";
        UserData user = userService.getUserByEmail(email);
        user.setOrdersSendTime(time);
        userService.saveUser(user);

    }
    public void setUpdateTimeParam(int time) {
        String email = "aboba@gmail.com";
        UserData user = userService.getUserByEmail(email);
        user.setUpdateTime(time);
        userService.saveUser(user);
    }
}