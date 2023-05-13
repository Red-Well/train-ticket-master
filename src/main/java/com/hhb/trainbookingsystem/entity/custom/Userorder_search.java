package com.hhb.trainbookingsystem.entity.custom;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Userorder_search {
    private  int id;
    private  String trainnum;
    private  String namelist;
    private  String seatlist;

    private  BigDecimal price;
    private  String startstation;
    private  String endstation;
    private  Timestamp starttime;
    private  Timestamp endtime;
    private  String pricelist;

    public Userorder_search(){
        super();
    }

    public Userorder_search(int id, String trainnum, String namelist, String seatlist, BigDecimal price, String startstation, String endstation, Timestamp starttime, Timestamp endtime) {
        this.id = id;
        this.trainnum = trainnum;
        this.namelist = namelist;
        this.seatlist = seatlist;
        this.price = price;
        this.startstation = startstation;
        this.endstation = endstation;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrainnum() {
        return trainnum;
    }

    public void setTrainnum(String trainnum) {
        this.trainnum = trainnum;
    }

    public String getNamelist() { return namelist; }

    public void setNamelist(String namelist) { this.namelist = namelist; }

    public String getSeatlist() {
        return seatlist;
    }

    public void setSeatlist(String seat) {
        this.seatlist = seat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStartstation() {
        return startstation;
    }

    public void setStartstation(String startstation) {
        this.startstation = startstation;
    }

    public String getEndstation() {
        return endstation;
    }

    public void setEndstation(String endstation) {
        this.endstation = endstation;
    }




    public String getPricelist() {
        return pricelist;
    }

    public void setPricelist(String pricelist) {
        this.pricelist = pricelist;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }
}
