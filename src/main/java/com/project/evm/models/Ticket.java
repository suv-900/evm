package com.project.evm.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ticket {
   
    private long id;

    private Customer customer;

    private Event event;
    
    private boolean expired;

    public Ticket(){}

    @Override
    public String toString(){
        return "Ticket: id = "+this.id+" Customer = "+this.customer.getName()+" Event = "+this.event.getName();
    }
}
