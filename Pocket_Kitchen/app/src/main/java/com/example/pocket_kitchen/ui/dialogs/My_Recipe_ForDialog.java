package com.example.pocket_kitchen.ui.dialogs;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocket_kitchen.R;

public class My_Recipe_ForDialog extends AppCompatActivity {

    TextView day;
    TextView content;
    TextView time;
    TextView name;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_recipe_for_dialog);

        time = (TextView) findViewById(R.id.time);
        day = (TextView) findViewById(R.id.day);
        name = (TextView) findViewById(R.id.name);
        content = (TextView) findViewById(R.id.content);
        title = (TextView) findViewById(R.id.title);

        Intent intent = getIntent();

        time.setText(intent.getExtras().getString("time"));
        day.setText(intent.getExtras().getString("day"));
        name.setText(intent.getExtras().getString("name"));
        content.setText(intent.getExtras().getString("content"));
        title.setText(intent.getExtras().getString("title"));

        switch (intent.getExtras().getString("textcolor")){
            case "Black":
                content.setTextColor(Color.BLACK);
                break;
            case "Red":
                content.setTextColor(Color.RED);
                break;
            case "Blue":
                content.setTextColor(Color.BLUE);
                break;
            case "Green":
                content.setTextColor(Color.GREEN);
                break;
        }

        Typeface typeface;
        switch (intent.getExtras().getString("textfont")){
            case "sans":
                content.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                break;
            case "serif":
                typeface = Typeface.createFromAsset(getAssets(), "fonts/batang.ttc");
                content.setTypeface(typeface);
                break;
            case "casual":
                typeface = Typeface.createFromAsset(getAssets(), "fonts/nanumpen.ttf");
                content.setTypeface(typeface);
                break;
        }
        content.setTextSize(intent.getExtras().getFloat("textsize"));



        switch (intent.getExtras().getString("namecolor")){
            case "Black":
                name.setTextColor(Color.BLACK);
                break;
            case "Red":
                name.setTextColor(Color.RED);
                break;
            case "Blue":
                name.setTextColor(Color.BLUE);
                break;
            case "Green":
                name.setTextColor(Color.GREEN);
                break;
        }

        switch (intent.getExtras().getString("namefont")){
            case "sans":
                name.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                break;
            case "serif":
                typeface = Typeface.createFromAsset(getAssets(), "fonts/batang.ttc");
                name.setTypeface(typeface);
                break;
            case "casual":
                typeface = Typeface.createFromAsset(getAssets(), "fonts/nanumpen.ttf");
                name.setTypeface(typeface);
                break;
        }
        name.setTextSize(intent.getExtras().getFloat("namesize"));


        switch (intent.getExtras().getString("titlecolor")){
            case "Black":
                title.setTextColor(Color.BLACK);
                break;
            case "Red":
                title.setTextColor(Color.RED);
                break;
            case "Blue":
                title.setTextColor(Color.BLUE);
                break;
            case "Green":
                title.setTextColor(Color.GREEN);
                break;
        }

        switch (intent.getExtras().getString("titlefont")){
            case "sans":
                title.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                title.setTypeface(null,Typeface.BOLD);
                break;
            case "serif":
                typeface = Typeface.createFromAsset(getAssets(), "fonts/batang.ttc");
                title.setTypeface(typeface);
                break;
            case "casual":
                typeface = Typeface.createFromAsset(getAssets(), "fonts/nanumpen.ttf");
                title.setTypeface(typeface);
                break;
        }
        title.setTextSize(intent.getExtras().getFloat("titlesize"));


    }
}

