package com.hhb.trainbookingsystem.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "loginfo", schema = "booking", catalog = "")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LoginfoEntity {
    private String id;
    private Timestamp createtime;
    private Integer level;
    private String operationunit;
    private String method;
    private String username;
    private String describe;
    private String returnvalue;
    private String operationtype;
    private Long runtime;
    private String clientip;
    private String args;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "createtime")
    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }



    @Basic
    @Column(name = "args")
    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    @Basic
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "operationunit")
    public String getOperationunit() {
        return operationunit;
    }

    public void setOperationunit(String operationunit) {
        this.operationunit = operationunit;
    }

    @Basic
    @Column(name = "methods")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "describes")
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Basic
    @Column(name = "returnvalue")
    public String getReturnvalue() {
        return returnvalue;
    }

    public void setReturnvalue(String returnvalue) {
        this.returnvalue = returnvalue;
    }

    @Basic
    @Column(name = "operationtype")
    public String getOperationtype() {
        return operationtype;
    }

    public void setOperationtype(String operationtype) {
        this.operationtype = operationtype;
    }

    @Basic
    @Column(name = "runtimes")
    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    @Basic
    @Column(name = "clientip")
    public String getClientip() {
        return clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginfoEntity that = (LoginfoEntity) o;
        return id == that.id &&
                Objects.equals(createtime, that.createtime) &&
                Objects.equals(level, that.level) &&
                Objects.equals(operationunit, that.operationunit) &&
                Objects.equals(method, that.method) &&
                Objects.equals(username, that.username) &&
                Objects.equals(describe, that.describe) &&
                Objects.equals(returnvalue, that.returnvalue) &&
                Objects.equals(operationtype, that.operationtype) &&
                Objects.equals(runtime, that.runtime) &&
                Objects.equals(clientip, that.clientip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createtime, level, operationunit, method, username, describe, returnvalue, operationtype, runtime, clientip);
    }

    @Override
    public String toString() {
        return "LoginfoEntity{" +
                "id='" + id + '\'' +
                ", createtime=" + createtime +
                ", level=" + level +
                ", operationunit='" + operationunit + '\'' +
                ", method='" + method + '\'' +
                ", username='" + username + '\'' +
                ", describe='" + describe + '\'' +
                ", returnvalue='" + returnvalue + '\'' +
                ", operationtype='" + operationtype + '\'' +
                ", runtime=" + runtime +
                ", clientip='" + clientip + '\'' +
                '}';
    }
}
