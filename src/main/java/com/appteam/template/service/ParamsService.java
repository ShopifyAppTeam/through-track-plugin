package com.appteam.template.service;

import com.appteam.template.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParamsService {
    @Autowired
    private UserService userService;
    @Autowired
    private DHLService dhlService;

    public void setShipmentTimeParam(int time, String email) {
        UserData user = userService.getUserByEmail(email);
        user.setOrdersSendTime(time);
        userService.saveUser(user);

    }
    public void setUpdateTimeParam(int time, String email) {
        UserData user = userService.getUserByEmail(email);
        user.setUpdateTime(time);
        userService.saveUser(user);
        dhlService.setUserUpdateTimer(userService.getUserByEmail(email));
    }
}