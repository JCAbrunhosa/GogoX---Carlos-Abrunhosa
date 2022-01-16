package com.example.GogoX_Challenge.Objects;

public class Participant {

    private String firstName;
    private String lastName;
    private String key;
    private int uuid;

    public Participant(String firstName, String lastName, String key, int uuid){
        this.firstName=firstName;
        this.lastName=lastName;
        this.key=key;
        this.uuid=uuid;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getKey(){
        return this.key;
    }

    public int getUUID(){return this.uuid;}
}
