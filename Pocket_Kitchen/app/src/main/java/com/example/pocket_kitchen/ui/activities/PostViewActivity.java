package com.example.pocket_kitchen.ui.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.Comment;
import com.example.pocket_kitchen.datas.Post;
import com.example.pocket_kitchen.ui.adapters.CommentRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class PostViewActivity extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private String registeredDate, page, uid;
    private TextView textTitle, textDate, textWriter, textContent, textCommentCount;

    private CommentRecyclerAdapter commentRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        SharedPreferences sp = getSharedPreferences("pocket_kitchen", Context.MODE_PRIVATE);

        textTitle = findViewById(R.id.text_title);
        textDate = findViewById(R.id.text_date);
        textWriter = findViewById(R.id.text_writer);
        textContent = findViewById(R.id.text_content);
        textCommentCount = findViewById(R.id.text_comment_count);
        ImageButton btnMenu = findViewById(R.id.btn_menu);

        Intent intent = getIntent(); //intent에서 값을 넘겨받음
        try {
            registeredDate = intent.getExtras().getString("registeredDate");
            page = intent.getExtras().getString("page");
            uid = intent.getExtras().getString("uid");
        } catch (Exception e) {
            Toast.makeText(this, "오류. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
        commentRecyclerAdapter = new CommentRecyclerAdapter(this, registeredDate, sp.getString("uid", ""), page, textCommentCount);
        //댓글을 가져오는 어댑터임

        if (uid.equals(sp.getString("uid", ""))) btnMenu.setVisibility(View.VISIBLE);
        else btnMenu.setVisibility(View.GONE);
        //글 작성자와 uid를 비교해서 본인 글일 시 편집/삭제가 가능하게 했음

        btnMenu.setOnClickListener(new View.OnClickListener() { //어댑터의 편집,삭제와 기능 같음
            @Override
            public void onClick(View view) {

                final CharSequence[] select = {"편집", "삭제"}; //본인이 작성 -> 편집 가능

                AlertDialog.Builder builder = new AlertDialog.Builder(PostViewActivity.this);
                builder.setTitle(textTitle.getText());
                builder.setItems(select, (dialog, pos) -> {
                    if (select[pos].equals("편집")) {
                        Intent intent = new Intent(PostViewActivity.this, PostAddActivity.class);
                        intent.putExtra("edit", registeredDate); //도큐맨트의 아이디값을 넘겨주어 해당 액티비티에서 불러오게 함.
                        intent.putExtra("page", page);
                        startActivity(intent);
                    }
                    if (select[pos].equals("삭제")) {
                        AlertDialog.Builder delete = new AlertDialog.Builder(PostViewActivity.this);
                        delete.setTitle("삭제");
                        delete.setMessage("정말 삭제하시겠습니까?");
                        delete.setPositiveButton("예", (dialog1, which) -> {
                            firestore.collection("notice" + page)
                                    .document(registeredDate).delete();
                            finish();
                        });
                        delete.setNegativeButton("아니오", (dialog12, which) -> {
                        });
                        delete.show();
                    }
                });
                builder.show();
            }
        });

        RecyclerView commentRecyclerView = findViewById(R.id.comment_recycler_view);
        commentRecyclerView.setAdapter(commentRecyclerAdapter);
        commentRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //댓글 리사이클러뷰를 생성하주고 어댑터를 지정함. 아래는 리사이클러뷰의 divider를 만들어주는 코드

        Button btnAddComment = findViewById(R.id.btn_add_comment);
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.dialog_edittext, null);
                EditText edittext = v.findViewById(R.id.edittext);
                edittext.setHint("댓글을 입력해주세요");
                AlertDialog.Builder progressDialogBuilder = new AlertDialog.Builder(PostViewActivity.this)
                        .setView(v)
                        .setTitle("댓글 작성")
                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = ProgressDialog.show(PostViewActivity.this, "로딩중", "잠시만 기다려주세요..");

                                /** 댓글 작성하는 부분임. 현재 게시물에 comment라는 collection를 생성하여 저장함. **/
                                Long now = System.currentTimeMillis();
                                String text = edittext.getText().toString();
                                Comment cmt = new Comment();
                                cmt.setRegisteredDate(now);
                                cmt.setUid(sp.getString("uid", ""));
                                cmt.setWriter(sp.getString("name", ""));
                                cmt.setComment(text);

                                firestore.collection("notice" + page)
                                        .document(registeredDate).collection("comment")
                                        .document(now.toString())
                                        .set(cmt)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();
                                                Toast.makeText(getApplicationContext(), "작성되었습니다.", Toast.LENGTH_SHORT).show();
                                                getComment();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });

                                /** 글을 저장할 시 전체 댓글 수에 +1를 해주어야함.
                                 * 게시 글을 불러올 당시의 댓글 수와 추가할 시의 댓글 수가 다를 수 있기 때문에
                                 * 실시간으로 댓글 수를 불러와서 +1하고 저장함. **/
                                firestore.collection("notice" + page)
                                        .document(registeredDate).get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                Post post = documentSnapshot.toObject(Post.class);
                                                int commentCount = post.getCommentCount() + 1;
                                                post.setCommentCount(commentCount);
                                                firestore.collection("notice" + page)
                                                        .document(registeredDate).set(post)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                textCommentCount.setText(String.format("전체 댓글 %s >", commentCount));
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                            }
                                                        });
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog dialog = progressDialogBuilder.create();
                dialog.show();
            }
        });
    }

    /**
     * 게시글을 불러오는 코드임.
     **/
    private void getPost() {
        final ProgressDialog progressDialog = ProgressDialog.show(PostViewActivity.this, "로딩중", "잠시만 기다려주세요..");
        firestore.collection("notice" + page)
                .document(registeredDate)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        progressDialog.dismiss();

                        Post post = documentSnapshot.toObject(Post.class);
                        textTitle.setText(post.getTitle());
                        textContent.setText(post.getContent());

                        String date = new SimpleDateFormat("yyyy-MM-dd kk:mm", Locale.KOREA).format(new Date(post.getRegisteredDate()));
                        textDate.setText(date);
                        textWriter.setText(post.getName());
                        textCommentCount.setText(String.format("전체 댓글 %s >", post.getCommentCount()));

                        /* 제목과 내용의 색상, 폰트를 변경하는 부분 */
                        textTitle.setTextSize(post.getTitleSize());
                        textContent.setTextSize(post.getTextSize());
                        Typeface typeface;

                        switch (post.getTitleColor()) {
                            case "Black":
                                textTitle.setTextColor(Color.BLACK);
                                break;
                            case "Red":
                                textTitle.setTextColor(Color.RED);
                                break;
                            case "Blue":
                                textTitle.setTextColor(Color.BLUE);
                                break;
                            case "Green":
                                textTitle.setTextColor(Color.GREEN);
                                break;
                        }

                        switch (post.getTitleFont()) {
                            case "sans":
                                textTitle.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                                textTitle.setTypeface(null, Typeface.BOLD);
                                break;
                            case "serif":
                                typeface = Typeface.createFromAsset(getAssets(), "fonts/batang.ttc");
                                textTitle.setTypeface(typeface);
                                break;
                            case "casual":
                                typeface = Typeface.createFromAsset(getAssets(), "fonts/nanumpen.ttf");
                                textTitle.setTypeface(typeface);
                                break;
                        }

                        switch (post.getTextColor()) {
                            case "Black":
                                textContent.setTextColor(Color.BLACK);
                                break;
                            case "Red":
                                textContent.setTextColor(Color.RED);
                                break;
                            case "Blue":
                                textContent.setTextColor(Color.BLUE);
                                break;
                            case "Green":
                                textContent.setTextColor(Color.GREEN);
                                break;
                        }

                        switch (post.getTextFont()) {
                            case "sans":
                                textContent.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                                break;
                            case "serif":
                                typeface = Typeface.createFromAsset(getAssets(), "fonts/batang.ttc");
                                textContent.setTypeface(typeface);
                                break;
                            case "casual":
                                typeface = Typeface.createFromAsset(getAssets(), "fonts/nanumpen.ttf");
                                textContent.setTypeface(typeface);
                                break;
                        }
                    }
                });
    }

    /**
     * 댓글을 불러오는 코드임.
     * 댓글의 위치는 현재 게시물 안에 있는 comment collection임
     **/
    private void getComment() {
        firestore.collection("notice" + page).document(registeredDate).collection("comment")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            commentRecyclerAdapter.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Comment comment = document.toObject(Comment.class);
                                commentRecyclerAdapter.add(comment);
                            }
                            commentRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 액티비티의 상태 변화가 있을 시마다 업데이트함
     * activity lifecycle 참고
     **/
    @Override
    protected void onResume() {
        super.onResume();
        getPost();
        getComment();
    }
}
