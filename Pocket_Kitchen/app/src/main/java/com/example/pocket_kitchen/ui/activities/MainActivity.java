package com.example.pocket_kitchen.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocket_kitchen.datas.User;
import com.example.pocket_kitchen.ui.fragments.Honey_tip_MainFragment;
import com.example.pocket_kitchen.ui.fragments.Honey_tip_MainFragment;
import com.example.pocket_kitchen.ui.fragments.My_Recipe_MainFragment;
import com.example.pocket_kitchen.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    private FragmentManager fragmentManager;
    private My_Recipe_MainFragment fragment1;
    private Honey_tip_MainFragment fragment2;

    private SharedPreferences sp;
    private SharedPreferences.Editor sped;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("pocket_kitchen", Context.MODE_PRIVATE);
        sped = sp.edit();

        getUserInfo();

        sped.putBoolean("cold_add", false);
        sped.apply();

        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();

        Toolbar main_toolbar = findViewById(R.id.main_toolbar);

        setSupportActionBar(main_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageButton main_refrigerator_btn = findViewById(R.id.main_refrigerator_btn);
        main_refrigerator_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Refrigerator_MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton main_community_btn = findViewById(R.id.main_community_btn);
        main_community_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        ImageButton main_recipe_btn = findViewById(R.id.main_recipe_btn);
        main_recipe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
                startActivity(intent);
            }
        });


        ImageButton main_gas_range_btn = findViewById(R.id.main_gas_range_btn);
        main_gas_range_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Gas_Range_Activity.class);
                startActivity(intent);
            }
        });

    }

    private void getUserInfo() {
        fireStore.collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        if (document.exists()) {
                            User user = document.toObject(User.class);
                            if (user != null) {
                                sped.putString("uid", user.getUid());
                                sped.putString("email", user.getEmail());
                                sped.putString("name", user.getName());
                                sped.apply();
                                textView = (TextView) findViewById(R.id.toolbar_text);
                                textView.setText(user.getName() + " 님의 포켓키친");
                            }
                        } else {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

