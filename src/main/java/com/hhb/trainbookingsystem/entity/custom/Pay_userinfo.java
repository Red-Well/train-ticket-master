package com.hhb.trainbookingsystem.entity.custom;

import java.math.BigDecimal;

public class Pay_userinfo {
    private String name;
    private String idcardtype;
    private String personid;
    private String tickettype;
    private String seatkind;
    private String carriage;
    private String seat;
    private BigDecimal price;
    private String pricelist;


    public Pay_userinfo() {
    }

    public Pay_userinfo(String name, String idcardtype, String personid, String tickettype, String seatkind, String carriage, String seat, BigDecimal price, String pricelist) {
        this.name = name;
        this.idcardtype = idcardtype;
        this.personid = personid;
        this.tickettype = tickettype;
        this.seatkind = seatkind;
        this.carriage = carriage;
        this.seat = seat;
        this.price = price;
        this.pricelist=pricelist;
    }

    @Override
    public String toString() {
        return "Pay_userinfo{" +
                "name='" + name + '\'' +
                ", idcardtype='" + idcardtype + '\'' +
                ", personid='" + personid + '\'' +
                ", tickettype='" + tickettype + '\'' +
                ", seatkind='" + seatkind + '\'' +
                ", carriage='" + carriage + '\'' +
                ", seat='" + seat + '\'' +
                ", price='" + price + '\'' +
                ", pricelist='" + pricelist + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcardtype() {
        return idcardtype;
    }

    public void setIdcardtype(String idcardtype) {
        this.idcardtype = idcardtype;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getTickettype() {
        return tickettype;
    }

    public void setTickettype(String tickettype) {
        this.tickettype = tickettype;
    }

    public String getSeatkind() {
        return seatkind;
    }

    public void setSeatkind(String seatkind) {
        this.seatkind = seatkind;
    }

    public String getCarriage() {
        return carriage;
    }

    public void setCarriage(String carriage) {
        this.carriage = carriage;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPricelist() {
        return pricelist;
    }

    public void setPricelist(String pricelist) {
        this.pricelist = pricelist;
    }
}
