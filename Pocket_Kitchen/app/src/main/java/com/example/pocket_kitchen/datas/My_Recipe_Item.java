package com.example.pocket_kitchen.datas;


import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.Date;

public class My_Recipe_Item implements Comparable<My_Recipe_Item> {

    private String title;
    private String day;
    private String time;
    private String name;
    private String content;

    private float textsize = 20;
    private String textcolor = "Black";
    private String textfont = "sans";
    private float titlesize = 20;
    private String titlecolor = "Black";
    private String titlefont = "sans";
    private float namesize = 18;
    private String namecolor = "Black";
    private String namefont = "sans";

    public String getTitle() {
        return this.title;
    }

    public String getDay() {
        return this.day;
    }

    public String getTime() {
        return this.time;
    }

    public String getName() {
        return this.name;
    }

    public String getContent() {
        return this.content;
    }

    public float getTextsize() {
        return textsize;
    }

    public void setTextsize(float textsize) {
        this.textsize = textsize;
    }

    public String getTextcolor() {
        return textcolor;
    }

    public void setTextcolor(String textcolor) {
        this.textcolor = textcolor;
    }

    public float getTitlesize() {
        return titlesize;
    }

    public void setTitlesize(float titlesize) {
        this.titlesize = titlesize;
    }

    public String getTitlecolor() {
        return titlecolor;
    }

    public void setTitlecolor(String titlecolor) {
        this.titlecolor = titlecolor;
    }

    public float getNamesize() {
        return namesize;
    }

    public void setNamesize(float namesize) {
        this.namesize = namesize;
    }

    public String getNamecolor() {
        return namecolor;
    }

    public void setNamecolor(String namecolor) {
        this.namecolor = namecolor;
    }

    public String getTextfont() {
        return textfont;
    }

    public void setTextfont(String textfont) {
        this.textfont = textfont;
    }

    public String getTitlefont() {
        return titlefont;
    }

    public void setTitlefont(String titlefont) {
        this.titlefont = titlefont;
    }

    public String getNamefont() {
        return namefont;
    }

    public void setNamefont(String namefont) {
        this.namefont = namefont;
    }

    public My_Recipe_Item(String day, String time, String name, String title, String content) {
        this.day = day;
        this.time = time;
        this.name = name;
        this.title = title;
        this.content = content;
    }

    public My_Recipe_Item(String day, String time, String title, String content) {
        this.day = day;
        this.time = time;
        this.title = title;
        this.content = content;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int compareTo(My_Recipe_Item o) {

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date origin = null;
        try {
            origin = transFormat.parse(day + " " + time);
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




