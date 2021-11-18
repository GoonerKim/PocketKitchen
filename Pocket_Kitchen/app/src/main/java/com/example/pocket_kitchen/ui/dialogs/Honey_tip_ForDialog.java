package com.example.pocket_kitchen.ui.dialogs;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocket_kitchen.R;

public class Honey_tip_ForDialog extends AppCompatActivity {

    TextView honey_day;
    TextView honey_content;
    TextView honey_time;
    TextView honey_name;
    TextView honey_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_honey_tip_for_dialog);

        honey_time = (TextView) findViewById(R.id.honey_time);
        honey_day = (TextView) findViewById(R.id.honey_day);
        honey_name = (TextView) findViewById(R.id.honey_name);
        honey_content = (TextView) findViewById(R.id.honey_content);
        honey_title = (TextView) findViewById(R.id.honey_title);

        Intent intent = getIntent();

        honey_time.setText(intent.getExtras().getString("time"));
        honey_day.setText(intent.getExtras().getString("day"));
        honey_name.setText(intent.getExtras().getString("name"));
        honey_content.setText(intent.getExtras().getString("content"));
        honey_title.setText(intent.getExtras().getString("title"));

        switch (intent.getExtras().getString("textcolor")){
            case "Black":
                honey_content.setTextColor(Color.BLACK);
                break;
            case "Red":
                honey_content.setTextColor(Color.RED);
                break;
            case "Blue":
                honey_content.setTextColor(Color.BLUE);
                break;
            case "Green":
                honey_content.setTextColor(Color.GREEN);
                break;
        }

        Typeface typeface;
        switch (intent.getExtras().getString("textfont")){
            case "sans":
                honey_content.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                break;
            case "serif":
                typeface = Typeface.createFromAsset(getAssets(), "fonts/batang.ttc");
                honey_content.setTypeface(typeface);
                break;
            case "casual":
                typeface = Typeface.createFromAsset(getAssets(), "fonts/nanumpen.ttf");
                honey_content.setTypeface(typeface);
                break;
        }
        honey_content.setTextSize(intent.getExtras().getFloat("textsize"));



        switch (intent.getExtras().getString("namecolor")){
            case "Black":
                honey_name.setTextColor(Color.BLACK);
                break;
            case "Red":
                honey_name.setTextColor(Color.RED);
                break;
            case "Blue":
                honey_name.setTextColor(Color.BLUE);
                break;
            case "Green":
                honey_name.setTextColor(Color.GREEN);
                break;
        }

        switch (intent.getExtras().getString("namefont")){
            case "sans":
                honey_name.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                break;
            case "serif":
                typeface = Typeface.createFromAsset(getAssets(), "fonts/batang.ttc");
                honey_name.setTypeface(typeface);
                break;
            case "casual":
                typeface = Typeface.createFromAsset(getAssets(), "fonts/nanumpen.ttf");
                honey_name.setTypeface(typeface);
                break;
        }
        honey_name.setTextSize(intent.getExtras().getFloat("namesize"));


        switch (intent.getExtras().getString("titlecolor")){
            case "Black":
                honey_title.setTextColor(Color.BLACK);
                break;
            case "Red":
                honey_title.setTextColor(Color.RED);
                break;
            case "Blue":
                honey_title.setTextColor(Color.BLUE);
                break;
            case "Green":
                honey_title.setTextColor(Color.GREEN);
                break;
        }

        switch (intent.getExtras().getString("titlefont")){
            case "sans":
                honey_title.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                honey_title.setTypeface(null,Typeface.BOLD);
                break;
            case "serif":
                typeface = Typeface.createFromAsset(getAssets(), "fonts/batang.ttc");
                honey_title.setTypeface(typeface);
                break;
            case "casual":
                typeface = Typeface.createFromAsset(getAssets(), "fonts/nanumpen.ttf");
                honey_title.setTypeface(typeface);
                break;
        }
        honey_title.setTextSize(intent.getExtras().getFloat("titlesize"));


    }
}

