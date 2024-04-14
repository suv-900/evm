package com.project.evm.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogin {
    
    @NotBlank(message = "Username cannot be blank.")
    private String name;

    @NotBlank(message = "Password cannot be blank.")
    private String password;

    public UserLogin(){}

    @Override
    public String toString(){
        return "UserLogin: "+this.name+" "+this.password;
    }
}
