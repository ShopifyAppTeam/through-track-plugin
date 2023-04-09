
package com.appteam.template.service;

import com.appteam.template.data.Provider;
import com.appteam.template.data.User;
import com.appteam.template.dto.UserData;
import com.appteam.template.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public void processOAuthPostLogin(String username) {
        User existUser = repo.getUserByUsername(username);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUserName(username);
            newUser.setProvider(Provider.GOOGLE);
            //newUser.setEnabled(true);

            repo.save(newUser);
        }

    }

}
