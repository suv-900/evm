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
@Table(name="customer")
public class Customer {
   
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private long id;

    @Column(name="name",nullable=false,unique=true)
    private String name;

    @Column(name="email",nullable=false)
    private String email;

    @Column(name="password",nullable=false)
    private String password;

    @Column(name="description",nullable=true)
    private String description;

    @Column(name="events_subscribed",nullable=true)
    private List<Event> eventsSubscribed;

    public Customer(){}

    @Override
    public String toString(){
        return "Host: id = "+this.id+" name = "+this.name+" email = "+this.email+" password = "+
        this.password+"description = "+this.description+" eventsSubscribed = "+this.eventsSubscribed;
    }
}
