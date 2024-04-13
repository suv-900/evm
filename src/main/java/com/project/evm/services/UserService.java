package com.project.evm.services;

import java.net.PasswordAuthentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.evm.dao.UserDAO;
import com.project.evm.models.User;
import com.project.evm.utils.PasswordHasher;

public class UserService {
    @Autowired
    private UserDAO userDao;

    @Autowired
    private PasswordHasher hasher;

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    public void addUser(User user){
        String hashedPassword = hasher.hashPassword(user.getPassword());
        
        user.setPassword(hashedPassword);

        userDao.addUser(user);
    }

    public boolean loginUser(User user)throws IllegalArgumentException,Exception{
        String dbPassword = userDao.getPasswordByUsername(user.getName());

        boolean matches = hasher.comparePassword(dbPassword,user.getPassword());

        return matches;
    }
    public User getUser(int userID){

    }

    public User updateUser(User user){

    }

    public void deleteUser(int userID){

    }
}
