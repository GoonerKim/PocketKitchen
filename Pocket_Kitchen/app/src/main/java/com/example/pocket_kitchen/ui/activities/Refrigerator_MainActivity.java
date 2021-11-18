package com.example.pocket_kitchen.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.ui.activities.ColdActivity;
import com.example.pocket_kitchen.ui.activities.FreezeActivity;

public class Refrigerator_MainActivity extends AppCompatActivity {
    TextView freeze_btn;
    TextView cold_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrigerator__main);

        Toolbar refrigerator_main_toolbar = findViewById(R.id.refrigerator_main_toolbar);

        setSupportActionBar(refrigerator_main_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        freeze_btn = (TextView)findViewById(R.id.freeze_btn);
        freeze_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FreezeActivity.class);
                startActivity(intent);
            }
        });

        cold_btn = (TextView)findViewById(R.id.cold_btn);
        cold_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ColdActivity.class);
                startActivity(intent);
            }
        });
    }

    }
