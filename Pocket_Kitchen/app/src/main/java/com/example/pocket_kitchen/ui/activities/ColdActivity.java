package com.example.pocket_kitchen.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.Cold;
import com.example.pocket_kitchen.ui.adapters.ColdRecyclerAdapter;
import com.example.pocket_kitchen.ui.adapters.Cold_RecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ColdActivity extends AppCompatActivity {

    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    private SharedPreferences sp;
    private SharedPreferences.Editor sped;

    private ColdRecyclerAdapter coldAdapter1, coldAdapter2, coldAdapter3, coldAdapter4, coldAdapter5;
    private Cold_RecyclerViewAdapter adapter1, adapter2, adapter3, adapter4, adapter5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cold);

        coldAdapter1 = new ColdRecyclerAdapter(this);
        coldAdapter2 = new ColdRecyclerAdapter(this);
        coldAdapter3 = new ColdRecyclerAdapter(this);
        coldAdapter4 = new ColdRecyclerAdapter(this);
        coldAdapter5 = new ColdRecyclerAdapter(this);

        sp = getSharedPreferences("pocket_kitchen", Context.MODE_PRIVATE);
        sped = sp.edit();

        Toolbar cold_activity_toolbar = findViewById(R.id.cold_activity_toolbar);

        setSupportActionBar(cold_activity_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FloatingActionButton cold_fab = findViewById(R.id.cold_fab);
        cold_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Cold_Add_Activity.class);
                intent.putExtra("edit", "false"); //도큐맨트의 아이디값을 넘겨주어 해당 액티비티에서 불러오게 함.
                intent.putExtra("layer", "1");
                startActivity(intent);
            }
        });

        RecyclerView coldRecyclerView1 = findViewById(R.id.cold_recyclerview_1);
        RecyclerView coldRecyclerView2 = findViewById(R.id.cold_recyclerview_2);
        RecyclerView coldRecyclerView3 = findViewById(R.id.cold_recyclerview_3);
        RecyclerView coldRecyclerView4 = findViewById(R.id.cold_recyclerview_4);
        RecyclerView coldRecyclerView5 = findViewById(R.id.cold_recyclerview_5);

        coldRecyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        coldRecyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        coldRecyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        coldRecyclerView4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        coldRecyclerView5.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        coldRecyclerView1.setAdapter(coldAdapter1);
        coldRecyclerView2.setAdapter(coldAdapter2);
        coldRecyclerView3.setAdapter(coldAdapter3);
        coldRecyclerView4.setAdapter(coldAdapter4);
        coldRecyclerView5.setAdapter(coldAdapter5);
    }

    //홈버튼을 클릭하면 메인화면으로 돌아간다.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList1();
        getList2();
        getList3();
        getList4();
        getList5();
    }

    private void getList1() {
        fireStore.collection("users").document(sp.getString("uid", "0")).collection("cold1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            coldAdapter1.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Cold cold = document.toObject(Cold.class);
                                coldAdapter1.add(cold);
                            }
                            coldAdapter1.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void getList2() {
        fireStore.collection("users").document(sp.getString("uid", "0")).collection("cold2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            coldAdapter2.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Cold cold = document.toObject(Cold.class);
                                coldAdapter2.add(cold);
                            }
                            coldAdapter2.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void getList3() {
        fireStore.collection("users").document(sp.getString("uid", "0")).collection("cold3")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            coldAdapter3.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Cold cold = document.toObject(Cold.class);
                                coldAdapter3.add(cold);
                            }
                            coldAdapter3.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void getList4() {
        fireStore.collection("users").document(sp.getString("uid", "0")).collection("cold4")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            coldAdapter4.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Cold cold = document.toObject(Cold.class);
                                coldAdapter4.add(cold);
                            }
                            coldAdapter4.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void getList5() {
        fireStore.collection("users").document(sp.getString("uid", "0")).collection("cold5")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            coldAdapter5.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Cold cold = document.toObject(Cold.class);
                                coldAdapter5.add(cold);
                            }
                            coldAdapter5.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

