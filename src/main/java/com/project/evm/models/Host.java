package com.project.evm.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Host {
    
    @Id
    private long id;
    
    private String name;
    
    private String email;
   
    private String password;
    
    private String phone;
    
    private String description;
    
    private List<Event> events;
    public Host(){}

    @Override
    public String toString(){
        return "Host: id = "+this.id+" name = "+this.name+" email = "+this.email+" password = "+
        this.password+" phone = "+this.phone+" description = "+this.description;
    }
    
}
