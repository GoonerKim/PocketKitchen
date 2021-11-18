package com.example.pocket_kitchen.datas;

public class Cold_SingleItemModel {


    private String cold_title;
    private String cold_D_day;
    private String description;




       public Cold_SingleItemModel(String cold_title, String cold_D_day) {
        this.cold_title = cold_title;
        this.cold_D_day = cold_D_day;
    }




    public String getCold_D_day() {
        return cold_D_day;
    }

    public void setCold_D_day(String cold_D_day) {
        this.cold_D_day = cold_D_day;
    }

    public String getCold_title() {
        return cold_title;
    }

    public void setCold_title(String cold_title) {
        this.cold_title = cold_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

