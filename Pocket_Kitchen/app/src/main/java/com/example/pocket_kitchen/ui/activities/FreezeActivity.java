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
import com.example.pocket_kitchen.datas.Freeze;
import com.example.pocket_kitchen.ui.adapters.ColdRecyclerAdapter;
import com.example.pocket_kitchen.ui.adapters.Cold_RecyclerViewAdapter;
import com.example.pocket_kitchen.ui.adapters.FreezeRecyclerAdapter;
import com.example.pocket_kitchen.ui.adapters.Freeze_RecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class FreezeActivity extends AppCompatActivity {

    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    private SharedPreferences sp;
    private SharedPreferences.Editor sped;

    private FreezeRecyclerAdapter freezeAdapter1, freezeAdapter2, freezeAdapter3, freezeAdapter4, freezeAdapter5;
    private Freeze_RecyclerViewAdapter freezeadapter1, freezeadapter2, freezeadapter3, freezeadapter4, freezeadapter5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeze);

        freezeAdapter1 = new FreezeRecyclerAdapter(this);
        freezeAdapter2 = new FreezeRecyclerAdapter(this);
        freezeAdapter3 = new FreezeRecyclerAdapter(this);
        freezeAdapter4 = new FreezeRecyclerAdapter(this);
        freezeAdapter5 = new FreezeRecyclerAdapter(this);

        sp = getSharedPreferences("pocket_kitchen", Context.MODE_PRIVATE);
        sped = sp.edit();

        Toolbar freeze_activity_toolbar = findViewById(R.id.freeze_activity_toolbar);

        setSupportActionBar(freeze_activity_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        FloatingActionButton freeze_fab = findViewById(R.id.freeze_fab);
        freeze_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Freeze_Add_Activity.class);
                intent.putExtra("edit", "false"); //도큐맨트의 아이디값을 넘겨주어 해당 액티비티에서 불러오게 함.
                intent.putExtra("layer", "1");
                startActivity(intent);
            }
        });

        RecyclerView freezeRecyclerView1 = findViewById(R.id.freeze_recyclerview_1);
        RecyclerView freezeRecyclerView2 = findViewById(R.id.freeze_recyclerview_2);
        RecyclerView freezeRecyclerView3 = findViewById(R.id.freeze_recyclerview_3);
        RecyclerView freezeRecyclerView4 = findViewById(R.id.freeze_recyclerview_4);
        RecyclerView freezeRecyclerView5 = findViewById(R.id.freeze_recyclerview_5);

        freezeRecyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        freezeRecyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        freezeRecyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        freezeRecyclerView4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        freezeRecyclerView5.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        freezeRecyclerView1.setAdapter(freezeAdapter1);
        freezeRecyclerView2.setAdapter(freezeAdapter2);
        freezeRecyclerView3.setAdapter(freezeAdapter3);
        freezeRecyclerView4.setAdapter(freezeAdapter4);
        freezeRecyclerView5.setAdapter(freezeAdapter5);
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
        fireStore.collection("users").document(sp.getString("uid", "0")).collection("freeze1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            freezeAdapter1.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Freeze freeze = document.toObject(Freeze.class);
                                freezeAdapter1.add(freeze);
                            }
                            freezeAdapter1.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void getList2() {
        fireStore.collection("users").document(sp.getString("uid", "0")).collection("freeze2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            freezeAdapter2.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Freeze freeze= document.toObject(Freeze.class);
                                freezeAdapter2.add(freeze);
                            }
                            freezeAdapter2.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void getList3() {
        fireStore.collection("users").document(sp.getString("uid", "0")).collection("freeze3")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            freezeAdapter3.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Freeze freeze= document.toObject(Freeze.class);
                                freezeAdapter3.add(freeze);
                            }
                            freezeAdapter3.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void getList4() {
        fireStore.collection("users").document(sp.getString("uid", "0")).collection("freeze4")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            freezeAdapter4.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Freeze freeze = document.toObject(Freeze.class);
                                freezeAdapter4.add(freeze);
                            }
                            freezeAdapter4.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void getList5() {
        fireStore.collection("users").document(sp.getString("uid", "0")).collection("freeze5")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            freezeAdapter5.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Freeze freeze = document.toObject(Freeze.class);
                                freezeAdapter5.add(freeze);
                            }
                            freezeAdapter5.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

