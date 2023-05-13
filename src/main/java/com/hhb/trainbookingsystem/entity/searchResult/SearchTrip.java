package com.hhb.trainbookingsystem.entity.searchResult;

import java.io.Serializable;
import java.util.Objects;

public class SearchTrip implements Serializable {
    //搜索的车次ID
    public int tripId;
    //搜索到的车次编号
    public String tripNumber;
    //起始站
    public String startStation;
    //终点站
    public String endStation;
    //起发时间
    public String startTime;
    //到达时间
    public String endTime;
    //历时
    public String spendTime;
    //经过路线
    public String routeLine;
    //一等座剩余
    public int seatFirstRemain;
    //二等座剩余
    public int seatSecondRemain;
    //一等座票价
    public String fareFirst;
    //二等座票价
    public String fareSecond;
    //车次状态
    public String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(String spendTime) {
        this.spendTime = spendTime;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getTripNumber() {
        return tripNumber;
    }

    public void setTripNumber(String tripNumber) {
        this.tripNumber = tripNumber;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRouteLine() {
        return routeLine;
    }

    public void setRouteLine(String routeLine) {
        this.routeLine = routeLine;
    }

    public int getSeatFirstRemain() {
        return seatFirstRemain;
    }

    public void setSeatFirstRemain(int seatFirstRemain) {
        this.seatFirstRemain = seatFirstRemain;
    }

    public int getSeatSecondRemain() {
        return seatSecondRemain;
    }

    public void setSeatSecondRemain(int seatSecondRemain) {
        this.seatSecondRemain = seatSecondRemain;
    }

    public String getFareFirst() {
        return fareFirst;
    }

    public void setFareFirst(String fareFirst) {
        this.fareFirst = fareFirst;
    }

    public String getFareSecond() {
        return fareSecond;
    }

    public void setFareSecond(String fareSecond) {
        this.fareSecond = fareSecond;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchTrip that = (SearchTrip) o;
        return Objects.equals(tripId, that.tripId) &&
                Objects.equals(tripNumber, that.tripNumber) &&
                Objects.equals(startStation, that.startStation) &&
                Objects.equals(endStation, that.endStation) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(routeLine, that.routeLine) &&
                Objects.equals(seatFirstRemain, that.seatFirstRemain) &&
                Objects.equals(seatSecondRemain, that.seatSecondRemain) &&
                Objects.equals(fareFirst, that.fareFirst) &&
                Objects.equals(fareSecond, that.fareSecond);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, tripNumber, startStation, endStation, startTime, endTime, routeLine, seatFirstRemain, seatSecondRemain, fareFirst, fareSecond);
    }
}
