package com.example.pocket_kitchen.datas;

public class Notice {

    private String listId;
    private String uid;
    private Long registeredDate;

    private String day;
    private String time;
    private String name;
    private String title;
    private String content;

    private Float titleSize = 20f;
    private String titleColor = "Black";
    private String titleFont = "sans";

    private Float textSize = 20f;
    private String textColor = "Black";
    private String textFont = "sans";

    public String getListId() { return listId; }
    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getRegisteredDate() { return registeredDate; }
    public void setRegisteredDate(Long registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) { this.content = content; }

    public Float getTitleSize() { return titleSize; }
    public void setTitleSize(Float titleSize) {
        this.titleSize = titleSize;
    }

    public String getTitleColor() {
        return titleColor;
    }
    public void setTitleColor(String titleColor) { this.titleColor = titleColor; }

    public String getTitleFont() {
        return titleFont;
    }
    public void setTitleFont(String titleFont) {
        this.titleFont = titleFont;
    }

    public Float getTextSize() {
        return textSize;
    }
    public void setTextSize(Float textSize) { this.textSize = textSize; }

    public String getTextColor() {
        return textColor;
    }
    public void setTextColor(String textColor) { this.textColor = textColor; }

    public String getTextFont() {
        return textFont;
    }
    public void setTextFont(String textFont) {
        this.textFont = textFont;
    }
}
