package com.project.evm.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.project.evm.exceptions.UnauthorizedUserException;
import com.project.evm.exceptions.UserExistsException;
import com.project.evm.exceptions.UserNotFoundException;
import com.project.evm.models.dto.UserDTO;
import com.project.evm.models.dto.UserLogin;
import com.project.evm.models.entities.UserEntity;
import com.project.evm.services.TokenService;
import com.project.evm.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/create")
    public String createUser(@Valid @RequestBody UserEntity user,HttpServletResponse response)
        throws UserExistsException,JWTCreationException,Exception{
        
        if(userService.exists(user)){
            throw new UserExistsException("User already exists cannot create another.");
        }

        userService.addUser(user);

        String token = tokenService.generateToken(user.getName());
        response.addHeader("Token",token);

        return "User created.";
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public void loginUser(@Valid @RequestBody UserLogin user,HttpServletResponse response)throws Exception,
    UnauthorizedUserException,UserNotFoundException{
        boolean isOK = userService.loginUser(user);

        if(!isOK){
            throw new UnauthorizedUserException("Credentials dont match.");
        }

        String token = tokenService.generateToken(user.getName());
        response.addHeader("Token",token);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update")
    public void updateUser(@RequestHeader(value = "Token",required = true) String token,
            @RequestBody UserEntity userUpdates)throws Exception,TokenExpiredException,JWTVerificationException{
        
        String username = tokenService.extractUsername(token);

        userUpdates.setName(username);
            
        userService.updateUser(userUpdates);
    }
 
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/getUser/{id}")
    public UserDTO getUser(@PathVariable("id")Long id)throws Exception,UserNotFoundException{
        return userService.getUserById(id);
    }

    // @ResponseStatus(HttpStatus.OK)
    // @DeleteMapping("/delete")
    // public void deleteUser(@RequestHeader(value = "Token",required = true)String token)
    //     throws TokenExpiredException,JWTVerificationException,Exception{

    //     String username = tokenService.extractUsername(token);
        
    //     userService.deleteUserByUsername(username);
    // }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e){
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserExistsException.class)
    public String handleUserExistsException(UserExistsException e){
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e){
        log.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JWTCreationException.class)
    public void handleJWTCreationException(JWTCreationException e){
        log.error(e.getMessage());
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
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedUserException.class)
    public String handleUnauthorizedUserException(UnauthorizedUserException e){
        log.error(e.getMessage());
        return e.getMessage();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JWTVerificationException.class)
    public String handleJWTVerificationException(JWTVerificationException e){
        log.error(e.getMessage());
        return "Token malformed";
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenExpiredException.class)
    public String handleTokenExpiredException(TokenExpiredException e){
        log.error(e.getMessage());
        return "Token expired";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public String handleMissingHeaders(MissingRequestHeaderException e){
        return "Missing header: "+e.getHeaderName();
    }

 
}
