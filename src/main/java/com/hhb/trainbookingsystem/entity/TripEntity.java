package com.hhb.trainbookingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "trip", schema = "booking", catalog = "")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TripEntity implements Serializable {
    private int id;
    private String trainNumber;//车次编号
    private String startStation;//起点
    private String endStation;//终点
    private Timestamp departureTime;//开始日期
    private String remainseatInfo;//剩余座位信息
    private Byte tripStatus;//该车次的状态 0表示停运  1表示正常运行
    private Integer trainId;//关联的列车
    private Collection<FareEntity> faresById;
    private Collection<RoutelineEntity> routelinesById;
    private Collection<SeatEntity> seatsById;//关联的座位信息
    private Collection<StationsEntity> stationsById;
    private TrainEntity trainByTrainId;
    private Collection<UserOrderEntity> userOrdersById;
    private String showTime;

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = String.valueOf(this.departureTime);
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)  // 自增
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "train_number")
    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    @Basic
    @Column(name = "start_station")
    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    @Basic
    @Column(name = "end_station")
    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    @Basic
    @Column(name = "departure_time")
    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    @Basic
    @Column(name = "remainseat_info")
    public String getRemainseatInfo() {
        return remainseatInfo;
    }

    public void setRemainseatInfo(String remainseatInfo) {
        this.remainseatInfo = remainseatInfo;
    }

    @Basic
    @Column(name = "trip_status")
    public Byte getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(Byte tripStatus) {
        this.tripStatus = tripStatus;
    }

    @Basic
    @Column(name = "train_id")
    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripEntity that = (TripEntity) o;
        return id == that.id &&
                Objects.equals(trainNumber, that.trainNumber) &&
                Objects.equals(startStation, that.startStation) &&
                Objects.equals(endStation, that.endStation) &&
                Objects.equals(departureTime, that.departureTime) &&
                Objects.equals(remainseatInfo, that.remainseatInfo) &&
                Objects.equals(tripStatus, that.tripStatus) &&
                Objects.equals(trainId, that.trainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainNumber, startStation, endStation, departureTime, remainseatInfo, tripStatus, trainId);
    }
    @JsonIgnore
    @OneToMany(mappedBy = "tripByTripId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    public Collection<FareEntity> getFaresById() {
        return faresById;
    }

    public void setFaresById(Collection<FareEntity> faresById) {
        this.faresById = faresById;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "tripByTripId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    public Collection<RoutelineEntity> getRoutelinesById() {
        return routelinesById;
    }

    public void setRoutelinesById(Collection<RoutelineEntity> routelinesById) {
        this.routelinesById = routelinesById;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "tripByTripId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    public Collection<SeatEntity> getSeatsById() {
        return seatsById;
    }

    public void setSeatsById(Collection<SeatEntity> seatsById) {
        this.seatsById = seatsById;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "tripByTripId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    public Collection<StationsEntity> getStationsById() {
        return stationsById;
    }

    public void setStationsById(Collection<StationsEntity> stationsById) {
        this.stationsById = stationsById;
    }

    @ManyToOne
    @JoinColumn(name = "train_id", referencedColumnName = "id",insertable = false,updatable = false)
    public TrainEntity getTrainByTrainId() {
        return trainByTrainId;
    }

    public void setTrainByTrainId(TrainEntity trainByTrainId) {
        this.trainByTrainId = trainByTrainId;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "tripByTripId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    public Collection<UserOrderEntity> getUserOrdersById() {
        return userOrdersById;
    }

    public void setUserOrdersById(Collection<UserOrderEntity> userOrdersById) {
        this.userOrdersById = userOrdersById;
    }
}
