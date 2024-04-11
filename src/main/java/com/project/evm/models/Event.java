package com.project.evm.models;

import java.util.Date;
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
@Table(name="event")
public class Event {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="name",nullable=false,unique=true)
    private String name;

    @Column(name="description",nullable=false)
    private String description;

    @Column(name="totalTickets",nullable=false)
    private long totaltTickets;

    @Column(name="ticketsAvailable",nullable=false)
    private long ticketsAvailable;

    @Column(name="ticketsSold",nullable=false)
    private long ticketsSold;

    @Column(name="date",nullable=false)
    private Date date;

    @Column(name="hosts",nullable=false)
    private List<Host> hosts;

    public Event(){}
}
