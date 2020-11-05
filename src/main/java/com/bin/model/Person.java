package com.bin.model;

public class Person {

    private String firstName;
    private String lastName;

    public Person(){

    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName +"  "+ lastName;
    }
}
