package com.humolabs.gambeta.model;

import java.io.Serializable;

public class Player implements Serializable {

    private String name;
    private String nickName;
    private Integer contactPhone;

    public Player() {
        // Default constructor required for calls to DataSnapshot.getValue(Player.class)
    }

    public Player(String name, String nickName, Integer contactPhone) {
        this.name = name;
        this.nickName = nickName;
        this.contactPhone = contactPhone;
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

    public Integer getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(Integer contactPhone) {
        this.contactPhone = contactPhone;
    }
}
