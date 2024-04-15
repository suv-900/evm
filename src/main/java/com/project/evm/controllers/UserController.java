package com.project.evm.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.project.evm.exceptions.UserNotFoundException;
import com.project.evm.models.UserEntity;
import com.project.evm.models.UserLogin;
import com.project.evm.services.TokenService;
import com.project.evm.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RequestMapping("/users")
@RestController
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/create")
    public void createUser(@Valid @RequestBody UserEntity user,HttpServletResponse response)
        throws JWTCreationException,Exception{
        
        //Generic Ex
        userService.addUser(user);

        //JWTCreationEx,IllegalArgEx
        String token = tokenService.generateToken(user.getName());

        response.addHeader("Token",token);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e){
        log.error(e.getMessage());
        e.printStackTrace();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JWTCreationException.class)
    public void handleJWTCreationException(JWTCreationException e){
        log.error(e.getMessage());
        e.printStackTrace();
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

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public void loginUser(@Valid @RequestBody UserLogin user,HttpServletResponse response)throws Exception,UnauthorizedUserException{
        //IllegalArgsEx , GenericEx
        boolean isOK = userService.loginUser(user);

        if(!isOK){
            throw new UnauthorizedUserException("Credentials dont match.");
        }

        String token = tokenService.generateToken(user.getName());

        response.addHeader("Token",token);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedUserException.class)
    public String handleUnauthorizedUserException(UnauthorizedUserException e){
        log.error(e.getMessage());
        return e.getMessage();
    }


    // private String verifyTokenAndGetUsername(String token)throws TokenExpiredException,JWTVerificationException{
    //     return tokenService.extractUsername(token);
    // }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update")
    public void updateUser(@RequestHeader(value = "Token",required = true) String token,
            @RequestBody UserEntity userUpdates)throws Exception,TokenExpiredException,JWTVerificationException{
        
        String username = tokenService.extractUsername(token);

        userUpdates.setName(username);
            
        userService.updateUser(userUpdates);
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

 
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/getUser/{id}")
    public UserEntity getUser(@PathVariable("id")int id)throws Exception,UserNotFoundException{
        UserEntity user = userService.getUser(id);

        if( user == null ){
            throw new UserNotFoundException();
        }
        return user;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e){
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete")
    public void deleteUser(@RequestHeader(value = "Token",required = true)String token)
        throws TokenExpiredException,JWTVerificationException,Exception{

        String username = tokenService.extractUsername(token);
        
        userService.deleteUserByUsername(username);
    }
}
