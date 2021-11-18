package com.example.pocket_kitchen.datas;

public class Freeze_SingleItemModel {


    private String freeze_title;
    private String freeze_D_day;
    private String freeze_description;




       public Freeze_SingleItemModel(String freeze_title, String freeze_D_day) {
        this.freeze_title = freeze_title;
        this.freeze_D_day = freeze_D_day;
    }




    public String getFreeze_D_day() {
        return freeze_D_day;
    }

    public void setFreeze_D_day(String freeze_D_day) {
        this.freeze_D_day = freeze_D_day;
    }

    public String getFreeze_title() {
        return freeze_title;
    }

    public void setFreeze_title(String freeze_title) {
        this.freeze_title = freeze_title;
    }

    public String getFreeze_description() {
        return freeze_description;
    }

    public void setFreeze_description(String freeze_description) {
        this.freeze_description = freeze_description;
    }


}

