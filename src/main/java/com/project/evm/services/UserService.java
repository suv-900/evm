package com.project.evm.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.evm.dao.UserDAO;
import com.project.evm.models.UserEntity;
import com.project.evm.models.UserLogin;
import com.project.evm.repository.UserRepository;
import com.project.evm.utils.PasswordHasher;

public class UserService {
    @Autowired
    private UserDAO userDao;

    @Autowired
    private PasswordHasher hasher;

    @Autowired
    private UserRepository userRepository;

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    public void addUser(UserEntity user)throws Exception{
        String hashedPassword = hasher.hashPassword(user.getPassword());
        
        user.setPassword(hashedPassword);

        userDao.addUser(user);
    }
    
    public boolean loginUser(UserLogin user)throws IllegalArgumentException,Exception{
        String dbPassword = userDao.getPasswordByUsername(user.getName());

        boolean matches = hasher.comparePassword(dbPassword,user.getPassword());

        return matches;
    }
    public UserEntity getUser(int userID)throws Exception{
        return userDao.findById(userID);
    }

    public void updateUser(UserEntity user)throws Exception{
        userDao.updateUser(user);
    }

    public void deleteUser(UserEntity user)throws Exception{
        userDao.deleteUser(user);
    }

    public void deleteUserByUsername(String username)throws Exception{
        userRepository.deleteUserByUsername(username);
    }

    public boolean existsByUsername(String username)throws Exception{
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email)throws Exception{
        return userRepository.existsByEmail(email);
    }

    public boolean exists(UserEntity user)throws Exception{
        return userDao.exists(user);
    }

    //for retriving users associated with a event
    public List<UserEntity> findAllById(Iterable<Integer> idList)throws Exception{
        return userDao.findAllById(idList);    
    }
}
