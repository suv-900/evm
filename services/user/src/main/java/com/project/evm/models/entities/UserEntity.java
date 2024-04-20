package com.project.evm.models.entities;

import java.io.Serializable;

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

@Getter
@Setter
@Entity(name="users")
@Table(name="users")
public class UserEntity implements Serializable{
   
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private long id;

    @NotBlank(message="user name cannot be blank.")
    @Column(name="name",nullable=false,unique=true)
    private String name;

    @NotBlank(message="email cannot be blank.")
    @Email(message = "email is not valid.")
    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @NotBlank(message="user password cannot be blank.")
    @Column(name="password",nullable=false)
    private String password;

    @Column(name="description",nullable=true)
    private String description;

}
