package com.project.evm.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="users")
public class UserEntity implements Serializable{
   
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private long id;

    @NotBlank(message="user name cannot be blank.")
    @Column(name="name",nullable=false,unique=true)
    private String name;

    @Email(message = "email is not valid.")
    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @NotBlank(message="user password cannot be blank.")
    @Column(name="password",nullable=false)
    private String password;

    @Column(name="description",nullable=true)
    private String description;

    public UserEntity(){}

    @Override
    public String toString(){
        return "Host: id = "+this.id+" name = "+this.name+" email = "+this.email+" password = "+
        this.password+"description = "+this.description;
    }
}
