package com.example.pocket_kitchen.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocket_kitchen.datas.Post;
import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.ui.activities.PostAddActivity;
import com.example.pocket_kitchen.ui.adapters.PostRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import butterknife.ButterKnife;

public class My_Recipe_MainFragment extends Fragment {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private AlertDialog progressDialog;

    private PostRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View progressView = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        AlertDialog.Builder progressDialogBuilder = new AlertDialog.Builder(getActivity())
                .setView(progressView)
                .setCancelable(false);
        progressDialog = progressDialogBuilder.create();
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        SharedPreferences sp = getActivity().getSharedPreferences("pocket_kitchen", Context.MODE_PRIVATE);
        adapter = new PostRecyclerAdapter(getActivity(), sp.getString("uid", ""), "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_recipe_fragment_main, container, false);
        ButterKnife.bind(this, view);
        // rootview가 플래그먼트 화면으로 보이게 된다. 부분화면을 보여주고자하는 틀로 생각하면 된다.
        // fragment_main 파일과 MainFragment와 연결 해준다.
        // 인플레이션 과정을 통해서 받을 수 있다.

        recyclerView = view.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab1 = view.findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostAddActivity.class);
                intent.putExtra("edit", "false");
                intent.putExtra("page", "");
                startActivity(intent);
            }
        });
        return view;
    }

    private void getList() { //리스트를 불러오는 함수
        progressDialog.show();
        firestore.collection("notice").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            adapter.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Post post = document.toObject(Post.class);
                                adapter.add(post);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.my_recipe_context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                return true;
            case R.id.delete:
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void update() {
        getList();
    }

    @Override
    public void onResume() {
        super.onResume();
        getList();
    }
}





