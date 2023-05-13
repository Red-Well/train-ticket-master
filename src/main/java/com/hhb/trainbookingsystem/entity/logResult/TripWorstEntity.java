package com.hhb.trainbookingsystem.entity.logResult;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tripWorst", schema = "booking", catalog = "")
public class TripWorstEntity {
    private String trip;
    private int id;
    private Integer times;

    @Basic
    @Column(name = "trip", nullable = true, length = 255)
    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        TripWorstEntity that = (TripWorstEntity) o;
        return id == that.id &&
                Objects.equals(trip, that.trip) &&
                Objects.equals(times, that.times);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trip, id, times);
    }
}
