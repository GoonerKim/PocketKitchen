package com.example.pocket_kitchen.datas;


import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.Date;

public class Honey_tip_Item implements Comparable<Honey_tip_Item> {

    private String honey_title;
    private String honey_day;
    private String honey_time;
    private String honey_name;
    private String honey_content;

    private float honey_textsize = 20;
    private String honey_textcolor = "Black";
    private String honey_textfont = "sans";
    private float honey_titlesize = 20;
    private String honey_titlecolor = "Black";
    private String honey_titlefont = "sans";
    private float honey_namesize = 18;
    private String honey_namecolor = "Black";
    private String honey_namefont = "sans";

    public String getTitle() {
        return this.honey_title;
    }

    public String getDay() {
        return this.honey_day;
    }

    public String getTime() {
        return this.honey_time;
    }

    public String getName() {
        return this.honey_name;
    }

    public String getContent() {
        return this.honey_content;
    }

    public float getTextsize() {
        return honey_textsize;
    }

    public void setTextsize(float textsize) {
        this.honey_textsize = textsize;
    }

    public String getTextcolor() {
        return honey_textcolor;
    }

    public void setTextcolor(String textcolor) {
        this.honey_textcolor = textcolor;
    }

    public float getTitlesize() {
        return honey_titlesize;
    }

    public void setTitlesize(float titlesize) {
        this.honey_titlesize = titlesize;
    }

    public String getTitlecolor() {
        return honey_titlecolor;
    }

    public void setTitlecolor(String titlecolor) {
        this.honey_titlecolor = titlecolor;
    }

    public float getNamesize() {
        return honey_namesize;
    }

    public void setNamesize(float namesize) {
        this.honey_namesize = honey_namesize;
    }

    public String getNamecolor() {
        return honey_namecolor;
    }

    public void setNamecolor(String namecolor) {
        this.honey_namecolor = namecolor;
    }

    public String getTextfont() {
        return honey_textfont;
    }

    public void setTextfont(String textfont) {
        this.honey_textfont = textfont;
    }

    public String getTitlefont() {
        return honey_titlefont;
    }

    public void setTitlefont(String titlefont) {
        this.honey_titlefont = titlefont;
    }

    public String getNamefont() {
        return honey_namefont;
    }

    public void setNamefont(String namefont) {
        this.honey_namefont = namefont;
    }

    public Honey_tip_Item(String honey_day, String honey_time, String honey_name, String honey_title, String honey_content) {
        this.honey_day = honey_day;
        this.honey_time = honey_time;
        this.honey_name = honey_name;
        this.honey_title = honey_title;
        this.honey_content = honey_content;
    }

    public Honey_tip_Item(String honey_day, String honey_time, String honey_title, String honey_content) {
        this.honey_day = honey_day;
        this.honey_time = honey_time;
        this.honey_title = honey_title;
        this.honey_content = honey_content;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int compareTo(Honey_tip_Item o) {

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date origin = null;
        try {
            origin = transFormat.parse(honey_day + " " + honey_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date compare = null;
        try {
            compare = transFormat.parse(o.getDay() + " " + o.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (origin.getTime() > compare.getTime()) {
            return -1;
        } else if (origin.getTime() < compare.getTime()) {
            return 1;
        }
        return 0;
    }


    //  public static List<My_Recipe_Item> createContactsList(int numContacts) {
    //   List<My_Recipe_Item> items = new ArrayList<My_Recipe_Item>();
    //     My_Recipe_Item[] item = new My_Recipe_Item[5];
    //   item[0] = new My_Recipe_Item("2019-08-24", "19:21", "김규나", "기호7번입니다.", "열심히하겠습니다.");
    //  item[1] = new My_Recipe_Item("2019-08-24", "15:24", "이호상", "일해라 개미들아", "얄짤없다.");
    //   item[2] = new My_Recipe_Item("2019-08-24", "10:28", "한동운", "저는 동철이가 아닌데요", "ㅎㅎㅎㅎㅎ");
    //    item[3] = new My_Recipe_Item("2019-08-23", "17:29", "이성현", "김규나 퇴출각이다.", "30일까지 해라");
    //  item[4] = new My_Recipe_Item("2019-08-23", "12:00", "도경진", "당신은 우리와...", "함께 가실수...");
    // for (int i = 0; i < 5; i++) {
    //        items.add(item[i]);
    //   }
    //    return items;
    // }
}




