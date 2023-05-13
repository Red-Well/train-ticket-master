package com.hhb.trainbookingsystem.entity.logResult;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "logResult", schema = "booking", catalog = "")
public class LogResultEntity {
    private String username;
    private String ops;
    private int id;

    @Basic
    @Column(name = "username", nullable = true, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "ops", nullable = true, length = 2555)
    public String getOps() {
        return ops;
    }

    public void setOps(String ops) {
        this.ops = ops;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogResultEntity that = (LogResultEntity) o;
        return id == that.id &&
                Objects.equals(username, that.username) &&
                Objects.equals(ops, that.ops);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, ops, id);
    }
}
