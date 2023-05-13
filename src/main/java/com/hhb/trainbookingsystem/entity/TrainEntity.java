package com.hhb.trainbookingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "train", schema = "booking", catalog = "")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TrainEntity implements Serializable {
    private int id;
    private String trainType;
    private String seatInfo;
    private Collection<TripEntity> tripsById;

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
    @Column(name = "train_type")
    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    @Basic
    @Column(name = "seat_info")
    public String getSeatInfo() {
        return seatInfo;
    }

    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainEntity that = (TrainEntity) o;
        return id == that.id &&
                Objects.equals(trainType, that.trainType) &&
                Objects.equals(seatInfo, that.seatInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainType, seatInfo);
    }
    @JsonIgnore
    @OneToMany(mappedBy = "trainByTrainId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    public Collection<TripEntity> getTripsById() {
        return tripsById;
    }

    public void setTripsById(Collection<TripEntity> tripsById) {
        this.tripsById = tripsById;
    }
}
