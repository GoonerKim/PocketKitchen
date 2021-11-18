package com.example.pocket_kitchen.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.Comment;
import com.example.pocket_kitchen.datas.Post;
import com.example.pocket_kitchen.ui.activities.PostAddActivity;
import com.example.pocket_kitchen.ui.activities.PostViewActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {

    /**
     * lists 라는 큰 틀이 있고, 그 틀에는 list array만 넣을 수 있음.
     * 2차원 배열이라고 생각하면 쉬움.
     * <p>
     * |----------------|    insert, edit, remove   |------------|
     * |     lists      |  <----------------------  |  each list |
     * |----------------|                           |------------|
     **/

    private Context context;
    private String registeredDate;
    private String uid;
    private String page;
    private TextView textCommentCount;
    private ArrayList<Comment> comments = new ArrayList<>(); //arraylist 자체가 List값만 받는 리스트임
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public CommentRecyclerAdapter(Context context, String registeredDate, String uid, String page, TextView textCommentCount) {
        this.context = context;
        this.registeredDate = registeredDate;
        this.uid = uid;
        this.page = page;

        /** 어댑터에서 항목 삭제를 하기 때문에 댓글을 삭제한 경우 액티비티의 총 댓글 개수를 -1해주어야함
         * 따라서 액티비티의 텍스트뷰를 가져와서 어댑터에서 관리함**/
        this.textCommentCount = textCommentCount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CommentRecyclerAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment, null));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void add(Comment comment) { //lists array에 list 데이터 추가
        comments.add(comment);
    }

    public void clear() { //lists array를 모두 삭제시킴
        comments.clear();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.bind(comments.get(i));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        void bind(final Comment comment) {
            /** comment는 글쓴이, 날짜, 댓글 내용을 가지고 있음. **/
            TextView textWriter = view.findViewById(R.id.text_writer);
            TextView textDate = view.findViewById(R.id.text_date);
            TextView textComment = view.findViewById(R.id.text_comment);
            ImageButton iconMenu = view.findViewById(R.id.icon_menu);

            /** 본인 글인지 확인/ 본인 글일 시 수정 및 삭제 가능하게함 **/
            if (uid.equals(comment.getUid())) iconMenu.setVisibility(View.VISIBLE);
            else iconMenu.setVisibility(View.GONE);

            String date = new SimpleDateFormat("yyyy-MM-dd kk:mm", Locale.KOREA).format(new Date(comment.getRegisteredDate()));
            textWriter.setText(comment.getWriter());
            textDate.setText(date);
            textComment.setText(comment.getComment());

            iconMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence[] select = {"편집", "삭제"}; //본인이 작성 -> 편집 가능

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(select, (dialog, pos) -> {
                        if (select[pos].equals("편집")) {
                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View v = inflater.inflate(R.layout.dialog_edittext, null);
                            EditText edittext = v.findViewById(R.id.edittext);
                            edittext.setText(comment.getComment());
                            AlertDialog.Builder progressDialogBuilder = new AlertDialog.Builder(context)
                                    .setView(v)
                                    .setTitle("댓글 편집")
                                    .setCancelable(false)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            String text = edittext.getText().toString();
                                            Comment cmt = new Comment();
                                            cmt.setRegisteredDate(comment.getRegisteredDate());
                                            cmt.setUid(comment.getUid());
                                            cmt.setWriter(comment.getWriter());
                                            cmt.setComment(text);

                                            /** 어댑터에서 댓글 편집이 가능하게 했음.
                                             * firestore db에 직접 전달하기 때문에 어댑터의 변경 내용을 직접 변경해주었음.
                                             * textcomment는 activity에서 표시하는 댓글 내용임 **/
                                            firestore.collection("notice" + page)
                                                    .document(registeredDate).collection("comment")
                                                    .document(comment.getRegisteredDate().toString())
                                                    .set(cmt)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            textComment.setText(text);
                                                            comment.setComment(text);
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                        }
                                                    });
                                        }
                                    })
                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                            AlertDialog progressDialog = progressDialogBuilder.create();
                            progressDialog.show();
                        }
                        if (select[pos].equals("삭제")) {
                            AlertDialog.Builder delete = new AlertDialog.Builder(context);
                            delete.setTitle("삭제");
                            delete.setMessage("정말 삭제하시겠습니까?");
                            delete.setPositiveButton("예", (dialog1, which) -> {
                                firestore.collection("notice" + page)
                                        .document(registeredDate).collection("comment")
                                        .document(comment.getRegisteredDate().toString()).delete();
                                comments.remove(getAdapterPosition());
                                notifyDataSetChanged();

                                /** 댓글을 삭제한 경우 db에서 삭제, 어댑터에 적용하고 notify해줌
                                 * 아래는 현재 댓글 총 개수를 가져오고 -1해주어 저장하는 로직임**/

                                firestore.collection("notice" + page)
                                        .document(registeredDate).get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                Post post = documentSnapshot.toObject(Post.class);
                                                int commentCount = post.getCommentCount() - 1;
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
                            });
                            delete.setNegativeButton("아니오", (dialog12, which) -> {
                            });
                            delete.show();
                        }
                    });
                    builder.show();
                }
            });
        }
    }
}