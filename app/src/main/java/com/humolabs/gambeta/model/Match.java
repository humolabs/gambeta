package com.humolabs.gambeta.model;

public class Match {

    private String address;
    private String diahora;

    public Match(String address, String diahora) {
        this.address = address;
        this.diahora = diahora;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDiahora() {
        return diahora;
    }

    public void setDiahora(String diahora) {
        this.diahora = diahora;
    }
}
