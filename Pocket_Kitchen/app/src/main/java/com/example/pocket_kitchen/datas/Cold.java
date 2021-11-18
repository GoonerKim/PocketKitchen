package com.example.pocket_kitchen.datas;

public class Cold {

    private Long registeredDate;
    private String title;
    private String count;
    private String layer;
    private String expiration;
    private String expirationStart;
    private String memo;
    private Long alarm;
    private int service;
    private String alarmTime;

    public void setRegisteredDate(Long registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Long getRegisteredDate() {
        return registeredDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getLayer() {
        return layer;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpirationStart(String expirationStart) {
        this.expirationStart = expirationStart;
    }

    public String getExpirationStart() {
        return expirationStart;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public void setAlarm(Long alarm) {
        this.alarm = alarm;
    }

    public Long getAlarm() {
        return alarm;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getService() {
        return service;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmTime() {
        return alarmTime;
    }
}