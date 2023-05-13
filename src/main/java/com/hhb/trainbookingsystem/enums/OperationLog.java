package com.hhb.trainbookingsystem.enums;

import java.sql.Timestamp;


public class OperationLog {
    private String id;
    private Timestamp createTime;
    /**
     * 日志等级
     */
    private Integer level;
    /**
     * 被操作的对象
     */
    private String operationUnit;
    /**
     * 方法名
     */
    private String method;
    /**
     * 参数
     */
    private String args;
    /**
     * 操作人id
     */
    private int userId;
    /**
     * 操作人
     */
    private String userName;
    /**
     * 日志描述
     */
    private String describe;
    /**
     * 操作类型
     */
    private String operationType;
    /**
     * 方法运行时间
     */
    private Long runTime;
    /**
     * 方法返回值
     */
    private String returnValue;

    private String clientIp;

    @Override
    public String toString() {
        return "OperationLog{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", level=" + level +
                ", operationUnit='" + operationUnit + '\'' +
                ", method='" + method + '\'' +
                ", args='" + args + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", describe='" + describe + '\'' +
                ", operationType='" + operationType + '\'' +
                ", runTime=" + runTime +
                ", returnValue='" + returnValue + '\'' +
                ", clientIp='" + clientIp + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Integer getLevel() {
        return level;
    }

    public String getClientIp() {
        return clientIp;
    }

    public String getOperationUnit() {
        return operationUnit;
    }

    public String getMethod() {
        return method;
    }

    public String getArgs() {
        return args;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getDescribe() {
        return describe;
    }

    public String getOperationType() {
        return operationType;
    }

    public Long getRunTime() {
        return runTime;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setOperationUnit(String operationUnit) {
        this.operationUnit = operationUnit;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

}
