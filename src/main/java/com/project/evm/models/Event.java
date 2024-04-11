package com.project.evm.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Event {
    @Id
    private long id;

    private String name;

    private String description;

    private long totaltTickets;

    private long ticketsAvailable;

    private long ticketsSold;

    private Date date;

    private List<Host> hosts;

    public Event(){}
}
