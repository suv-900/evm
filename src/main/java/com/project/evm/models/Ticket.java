package com.project.evm.models;

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
@Table(name="ticket")
public class Ticket {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="customer",nullable=false)
    private Customer customer;

    @Column(name="event",nullable=false)
    private Event event;
    
    @Column(name="expired")
    private boolean expired;

    public Ticket(){}

    @Override
    public String toString(){
        return "Ticket: id = "+this.id+" Customer = "+this.customer.getName()+" Event = "+this.event.getName();
    }
}
