package com.project.host.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "host")
public class HostEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    
    @Column(name="username",nullable = false)
    @NotBlank(message = "username cannot be blank")
    private String username;
   
    @Column(name="email",nullable = false)
    @NotBlank(message = "email cannot be blank")
    private String email;
    
    @Column(name="password",nullable = false)
    @NotBlank(message = "password cannot be blank")
    private String password;

    @Column(name="phone_number",nullable = false)
    @NotNull(message = "phone no cannot be null")
    private long phoneNumber;

    @Column(name="description",nullable = true)
    private String description;

    @Column(name="events_created",nullable = true)
    private List<Integer> eventsCreated;
}
