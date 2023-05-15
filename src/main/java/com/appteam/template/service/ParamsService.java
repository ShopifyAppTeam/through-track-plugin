package com.appteam.template.service;

import com.appteam.template.data.Provider;
import com.appteam.template.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class ParamsService {
    @Autowired
    private UserService userService;

    public void setParam(String paramName, int time) throws NoSuchFieldException, IllegalAccessException {
        UserData userData = new UserData();
        userService.saveUser(userData);

//        Long userId = 777L; // mysterious number
//        UserData user = userService.getUserById(userId);
//        Field param = user.getClass().getDeclaredField(paramName);
//        boolean accessible = param.isAccessible();
//        param.setAccessible(true);
//        param.set(this, time);
//        param.setAccessible(accessible);
//        userService.saveUser(user);
    }

//    public void setShipmentTimeParam(int time) {
//        int userId = 777; // mysterious number
//
//    }
//    public void setUpdateTimeParam(int time) {
//        int userId = 777; // mysterious number
//    }
}