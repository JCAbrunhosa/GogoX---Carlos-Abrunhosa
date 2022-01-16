package com.example.GogoX_Challenge.Objects;

public class Participant {

    private String firstName;
    private String lastName;
    private String ticket;
    private int uuid;

    public Participant(String firstName, String lastName, String ticket, int uuid){
        this.firstName=firstName;
        this.lastName=lastName;
        this.ticket=ticket;
        this.uuid=uuid;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getTicket(){
        return this.ticket;
    }

    public int getUUID(){return this.uuid;}
}
