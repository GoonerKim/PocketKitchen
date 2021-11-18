package com.example.pocket_kitchen.datas;

import java.util.ArrayList;


public class Cold_SectionDataModel {



    private String headerTitle;
    private ArrayList<Cold_SingleItemModel> allItemsInSection;


    public Cold_SectionDataModel() {

    }
    public Cold_SectionDataModel(String headerTitle, ArrayList<Cold_SingleItemModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Cold_SingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<Cold_SingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}

