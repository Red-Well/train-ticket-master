package com.hhb.trainbookingsystem.entity.custom;

import java.math.BigDecimal;

public class Selectcontactor {
    private String name;
    private String personidtype;
    private String personid;
    private String phonenum;
    private BigDecimal price;
    private int tripid;
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonidtype() {
        return personidtype;
    }

    public void setPersonidtype(String personidtype) {
        this.personidtype = personidtype;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public int getTripid() {
        return tripid;
    }

    public void setTripid(int tripid) {
        this.tripid = tripid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
