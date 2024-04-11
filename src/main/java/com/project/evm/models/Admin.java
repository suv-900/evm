package com.project.evm.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Admin {
    @Id
    private int id;

    private String name;

    private String email;

    private String password;

    public Admin(){}

    @Override
    public String toString(){
        return "Admin: id = "+this.id+" name = "+this.name+" email = "+this.email+" password = "+this.password;
    }
}
