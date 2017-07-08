package com.zeroisbiggerthanone.pcs.entities;


public class RandomNumber {

    private String id;
    private int number;

    public RandomNumber() {
    }

    public RandomNumber(String id, int number) {
        this.id = id;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
