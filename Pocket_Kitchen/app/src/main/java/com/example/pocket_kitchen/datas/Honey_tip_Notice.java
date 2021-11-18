package com.example.pocket_kitchen.datas;

public class Honey_tip_Notice extends Notice {

    private String honey_listId;
    private String honey_uid;
    private Long honey_registeredDate;

    private String honey_day;
    private String honey_time;
    private String honey_name;
    private String honey_title;
    private String honey_content;

    private Float honey_titleSize = 20f;
    private String honey_titleColor = "Black";
    private String honey_titleFont = "sans";

    private Float honey_textSize = 20f;
    private String honey_textColor = "Black";
    private String honey_textFont = "sans";

    public String getListId() { return honey_listId; }
    public void setListId(String listId) {
        this.honey_listId = listId;
    }

    public String getUid() {
        return honey_uid;
    }
    public void setUid(String uid) {
        this.honey_uid = uid;
    }

    public Long getRegisteredDate() { return honey_registeredDate; }
    public void setRegisteredDate(Long registeredDate) {
        this.honey_registeredDate = registeredDate;
    }

    public String getDay() {
        return honey_day;
    }
    public void setDay(String day) {
        this.honey_day = day;
    }

    public String getTime() {
        return honey_time;
    }
    public void setTime(String time) {
        this.honey_time = time;
    }

    public String getName() {
        return honey_name;
    }
    public void setName(String name) {
        this.honey_name = name;
    }

    public String getTitle() {
        return honey_title;
    }
    public void setTitle(String title) {
        this.honey_title = title;
    }

    public String getContent() {
        return honey_content;
    }
    public void setContent(String content) { this.honey_content = content; }

    public Float getTitleSize() { return honey_titleSize; }
    public void setTitleSize(Float titleSize) {
        this.honey_titleSize = titleSize;
    }

    public String getTitleColor() {
        return honey_titleColor;
    }
    public void setTitleColor(String titleColor) { this.honey_titleColor = titleColor; }

    public String getTitleFont() {
        return honey_titleFont;
    }
    public void setTitleFont(String titleFont) {
        this.honey_titleFont = titleFont;
    }

    public Float getTextSize() {
        return honey_textSize;
    }
    public void setTextSize(Float textSize) { this.honey_textSize = textSize; }

    public String getTextColor() {
        return honey_textColor;
    }
    public void setTextColor(String textColor) { this.honey_textColor = textColor; }

    public String getTextFont() {
        return honey_textFont;
    }
    public void setTextFont(String textFont) {
        this.honey_textFont = textFont;
    }
}
