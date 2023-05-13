package com.hhb.trainbookingsystem.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "contact", schema = "booking", catalog = "")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContactEntity implements Serializable {
    private int id;
    private String name;
    private String personId;
    private String phonenum;
    private Integer ordineryUserId;
    private OrdinaryUserEntity ordinaryUserByOrdineryUserId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)  // 自增
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "person_id", nullable = true, length = 100)
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "person_phonenum")
    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    @Basic
    @Column(name = "ordinery_user_id", nullable = true)
    public Integer getOrdineryUserId() {
        return ordineryUserId;
    }

    public void setOrdineryUserId(Integer ordineryUserId) {
        this.ordineryUserId = ordineryUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactEntity that = (ContactEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(ordineryUserId, that.ordineryUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, personId, ordineryUserId);
    }

    @ManyToOne
    @JoinColumn(name = "ordinery_user_id",referencedColumnName = "id",insertable = false,updatable = false)
    public OrdinaryUserEntity getOrdinaryUserByOrdineryUserId() {
        return ordinaryUserByOrdineryUserId;
    }

    public void setOrdinaryUserByOrdineryUserId(OrdinaryUserEntity ordinaryUserByOrdineryUserId) {
        this.ordinaryUserByOrdineryUserId = ordinaryUserByOrdineryUserId;
    }
}
