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
@Table(name="admin")
public class Admin {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="name",nullable=false,unique=true)
    private String name;

    @Column(name="email",nullable=false)
    private String email;

    @Column(name="password",nullable=false)
    private String password;

    public Admin(){}

    @Override
    public String toString(){
        return "Admin: id = "+this.id+" name = "+this.name+" email = "+this.email+" password = "+this.password;
    }
}
