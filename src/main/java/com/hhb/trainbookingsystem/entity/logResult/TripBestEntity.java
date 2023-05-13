package com.hhb.trainbookingsystem.entity.logResult;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tripBest", schema = "booking", catalog = "")
public class TripBestEntity {
    private int id;
    private String trip;
    private Integer times;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "trip", nullable = true, length = 255)
    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    @Basic
    @Column(name = "times", nullable = true)
    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripBestEntity that = (TripBestEntity) o;
        return id == that.id &&
                Objects.equals(trip, that.trip) &&
                Objects.equals(times, that.times);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trip, times);
    }
}
