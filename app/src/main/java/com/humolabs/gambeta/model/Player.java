package com.humolabs.gambeta.model;

import java.io.Serializable;

public class Player implements Serializable{

    private String name;
    private String nickName;
    private Integer contactNumber;

    public Player(){
        // Default constructor required for calls to DataSnapshot.getValue(Player.class)
    }

    public Player(String name, String nickName, Integer contactNumber) {
        this.name = name;
        this.nickName = nickName;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Integer contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", contactNumber=" + contactNumber +
                '}';
    }
}
