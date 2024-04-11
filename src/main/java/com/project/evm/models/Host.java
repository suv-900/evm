package com.project.evm.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="host")
public class Host {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    @Column(name="name",nullable=false)
    private String name;
    
    @Column(name="email",nullable=false)
    private String email;
    
    @Column(name="password",nullable=false)
    private String password;
    
    @Column(name="phone",nullable=true)
    private String phone;
    
    @Column(name="description",nullable=true)
    private String description;
    
    @Column(name="events",nullable=true)
    private List<Event> events;
    
    public Host(){}

    @Override
    public String toString(){
        return "Host: id = "+this.id+" name = "+this.name+" email = "+this.email+" password = "+
        this.password+" phone = "+this.phone+" description = "+this.description;
    }
    
}
