package com.project.evm.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.google.gson.Gson;
import com.project.evm.models.User;
import com.project.evm.models.UserLogin;
import com.project.evm.services.TokenService;
import com.project.evm.services.UserService;

import jakarta.validation.Valid;


@RequestMapping("/users")
@Controller
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user){
        try{
            //Generic Ex
            userService.addUser(user);

            //JWTCreationEx,IllegalArgEx
            String token = tokenService.generateToken(user.getName());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Token",token);

            return new ResponseEntity<>(headers,HttpStatus.OK);
        }catch(Exception e){
            log.info(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> sendValidationErrors(MethodArgumentNotValidException e){
        
        Map<String,String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });

        log.warn(errors.toString());
        return errors;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLogin user){
        try{
            //IllegalArgsEx , GenericEx
            boolean isOK = userService.loginUser(user);

            if(!isOK){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            String token = tokenService.generateToken(user.getName());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Token",token);

            return new ResponseEntity<>(headers,HttpStatus.OK);
        }catch(Exception e){
            log.info(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestHeader(value = "Token",required = true) String token,
            @RequestBody User userUpdates){
        try{
            String username = tokenService.extractUsername(token);
            
            userUpdates.setName(username);
            
            userService.updateUser(userUpdates);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch(TokenExpiredException e){
            log.error(e.getMessage());
            return new ResponseEntity<>("Token expired.",HttpStatus.UNAUTHORIZED);
        
        }catch(JWTVerificationException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("Token malformed.",HttpStatus.BAD_REQUEST); 
        
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public String handleMissingHeaders(MissingRequestHeaderException e){
        return "Missing header: "+e.getHeaderName();
    }

    @GetMapping("/getUser/{id}")
    public void getUser(){

    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(){

    }
}
