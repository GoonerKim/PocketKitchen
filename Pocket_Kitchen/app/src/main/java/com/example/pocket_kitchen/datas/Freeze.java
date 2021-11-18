package com.example.pocket_kitchen.datas;

public class Freeze {

    private Long registeredDate;
    private String freeze_title;
    private String freeze_count;
    private String freeze_layer;
    private String freeze_expiration;
    private String expirationStart;
    private String freeze_memo;
    private Long freeze_alarm;
    private int freeze_service;
    private String freeze_alarmTime;

    public void setRegisteredDate(Long registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Long getRegisteredDate() {
        return registeredDate;
    }

    public void setTitle(String freeze_title) {
        this.freeze_title = freeze_title;
    }

    public String getTitle() {
        return freeze_title;
    }

    public void setCount(String freeze_count) {
        this.freeze_count = freeze_count;
    }

    public String getCount() {
        return freeze_count;
    }

    public void setLayer(String freeze_layer) {
        this.freeze_layer = freeze_layer;
    }

    public String getLayer() {
        return freeze_layer;
    }

    public void setExpiration(String freeze_expiration) {
        this.freeze_expiration = freeze_expiration;
    }

    public String getExpiration() {
        return freeze_expiration;
    }

    public void setExpirationStart(String expirationStart) {
        this.expirationStart = expirationStart;
    }

    public String getExpirationStart() {
        return expirationStart;
    }

    public void setMemo(String freeze_memo) {
        this.freeze_memo = freeze_memo;
    }

    public String getMemo() {
        return freeze_memo;
    }

    public void setAlarm(Long freeze_alarm) {
        this.freeze_alarm = freeze_alarm;
    }

    public Long getAlarm() {
        return freeze_alarm;
    }

    public void setService(int freeze_service) {
        this.freeze_service = freeze_service;
    }

    public int getService() {
        return freeze_service;
    }

    public void setAlarmTime(String freeze_alarmTime) {
        this.freeze_alarmTime = freeze_alarmTime;
    }

    public String getAlarmTime() {
        return freeze_alarmTime;
    }
}