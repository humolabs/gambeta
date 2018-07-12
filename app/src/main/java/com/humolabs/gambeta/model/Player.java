package com.humolabs.gambeta.model;

public class Player {

    private String name;
    private String nickName;
    private Integer contactNumber;

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
}
