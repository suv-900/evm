package com.project.evm.services;

import java.net.PasswordAuthentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.evm.dao.UserDAO;
import com.project.evm.exceptions.UserNotFoundException;
import com.project.evm.models.User;
import com.project.evm.models.UserLogin;
import com.project.evm.utils.PasswordHasher;

public class UserService {
    @Autowired
    private UserDAO userDao;

    @Autowired
    private PasswordHasher hasher;

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    public void addUser(User user)throws Exception{
        String hashedPassword = hasher.hashPassword(user.getPassword());
        
        user.setPassword(hashedPassword);

        userDao.addUser(user);
    }
    
    public boolean loginUser(UserLogin user)throws IllegalArgumentException,Exception{
        String dbPassword = userDao.getPasswordByUsername(user.getName());

        boolean matches = hasher.comparePassword(dbPassword,user.getPassword());

        return matches;
    }
    public User getUser(int userID)throws UserNotFoundException,Exception{
        return userDao.getUserById(userID);
    }

    public User updateUser(User user)throws Exception{
        return userDao.updateUser(user);
    }

    public void deleteUser(User user)throws Exception{
        userDao.deleteUser(user);
    }
}
