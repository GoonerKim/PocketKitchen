package com.example.pocket_kitchen.datas;

import java.util.ArrayList;


public class Freeze_SectionDataModel {



    private String freeze_headerTitle;
    private ArrayList<Freeze_SingleItemModel> freeze_allItemsInSection;


    public Freeze_SectionDataModel() {

    }
    public Freeze_SectionDataModel(String freeze_headerTitle, ArrayList<Freeze_SingleItemModel> freeze_allItemsInSection) {
        this.freeze_headerTitle = freeze_headerTitle;
        this.freeze_allItemsInSection = freeze_allItemsInSection;
    }



    public String getFreeze_headerTitle() {
        return freeze_headerTitle;
    }

    public void setFreeze_headerTitle(String freeze_headerTitle) {
        this.freeze_headerTitle = freeze_headerTitle;
    }

    public ArrayList<Freeze_SingleItemModel> getFreeze_allItemsInSection() {
        return freeze_allItemsInSection;
    }

    public void setFreeze_allItemsInSection(ArrayList<Freeze_SingleItemModel> freeze_allItemsInSection) {
        this.freeze_allItemsInSection = freeze_allItemsInSection;
    }


}

