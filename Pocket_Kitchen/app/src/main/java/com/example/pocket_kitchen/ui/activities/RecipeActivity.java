package com.example.pocket_kitchen.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pocket_kitchen.R;

public class RecipeActivity extends AppCompatActivity {
    Button recipe_search_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Toolbar recipe_toolbar = findViewById(R.id.recipe_toolbar);

        setSupportActionBar(recipe_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        recipe_search_btn =(Button)findViewById(R.id.recipe_search_btn);

        recipe_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.ingredient_input);

                String ingredient = editText.getText().toString();
                String recipeUri = "http://www.10000recipe.com/recipe/list.html?q=" + ingredient + "&order=reco&page=1";

                if (ingredient.length() == 0) {
                    Toast.makeText(RecipeActivity.this, "재료를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(RecipeActivity.this, Recipe_Show_Activity.class);
                    intent.putExtra("recipeUri", recipeUri);
                    startActivity(intent);
                }
            }
        });
    }
}