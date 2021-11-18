package com.example.pocket_kitchen.datas;

public class Comment {

    private Long registeredDate;
    private String writer;
    private String uid;
    private String comment;

    public Long getRegisteredDate() { return registeredDate; }
    public void setRegisteredDate(Long registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
