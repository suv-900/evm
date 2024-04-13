package com.project.evm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/users")
@Controller
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/create")
    public void createUser(){

    }

    @PostMapping("/update/{id}")
    public void updateUser(){

    }

    @GetMapping("/getUser/{id}")
    public void getUser(){

    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(){

    }
}
