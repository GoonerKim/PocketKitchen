package com.example.pocket_kitchen.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocket_kitchen.R;
import com.example.pocket_kitchen.datas.Notice;
import com.example.pocket_kitchen.datas.Post;
import com.example.pocket_kitchen.ui.activities.My_Recipe_add_notice;
import com.example.pocket_kitchen.ui.activities.PostAddActivity;
import com.example.pocket_kitchen.ui.activities.PostViewActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {

    /**
     * lists 라는 큰 틀이 있고, 그 틀에는 list array만 넣을 수 있음.
     * 2차원 배열이라고 생각하면 쉬움.
     * <p>
     * |----------------|    insert, edit, remove   |------------|
     * |     lists      |  <----------------------  |  each list |
     * |----------------|                           |------------|
     **/

    private Context context;
    private String uid;
    private String page;
    private ArrayList<Post> posts = new ArrayList<>(); //arraylist 자체가 List값만 받는 리스트임
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    /**
     * 어댑터에서 argument로 받는 항목
     * context = 해당 액티비티의 context를 넘겨받음
     * uid = 글이 본인이 작성한 글인지 확인하기 위한 user id를 넘겨받음
     * page = db상의 collection 이름을 가져오기 위함.
     **/

    public PostRecyclerAdapter(Context context, String uid, String page) {
        this.context = context;
        this.uid = uid;
        this.page = page;

        /**
         * notice activity에서는 page를 "" 값으로 넘겨받음 (collection name : notice)
         * honey activity에서는 page를 "2" 값으로 넘겨받음 (collection name : notice2)
         * **/
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PostRecyclerAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, null));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void add(Post post) { //lists array에 list 데이터 추가
        posts.add(0, post);
    }

    public void clear() { //lists array를 모두 삭제시킴
        posts.clear();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.bind(posts.get(i));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        void bind(final Post post) {
            /** 리스트에 값을 집어넣음 **/
            TextView textItemCount = view.findViewById(R.id.text_item_count);
            TextView textTitle = view.findViewById(R.id.text_title);
            TextView textCommentCount = view.findViewById(R.id.text_comment_count);
            LinearLayout layoutBg = view.findViewById(R.id.layout_bg);

            /** count는 왼쪽에 1, 2, 3, 4와 같이 항목의 번호를 표시하는것임
             * 리스트의 사이즈는 개수를 반환하기 때문에 -1를 해주어야함.
             * (개수가 3개여도 index는 0,1,2 이기 때문에)**/
            String count = String.valueOf(((posts.size() - 1) - getAdapterPosition()) + 1);
            textItemCount.setText(count);
            textTitle.setText(post.getTitle());
            textCommentCount.setText(String.format("(%s)", post.getCommentCount()));

            /** 배경을 클릭하는 경우 registered date, page, uid를 넘겨줌
             * registereddate는 게시물 작성 날짜와 게시물 고유 번호라고 생각하면됨
             * page는 현재 아이템이 notice의 아이템인지, notice2의 아이템인지 구별하기 위함임
             * uid는 게시물이 자기 게시물인지 확인하기 위한 용도임. **/
            layoutBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PostViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("registeredDate", post.getRegisteredDate().toString());
                    intent.putExtra("page", page);
                    intent.putExtra("uid", post.getUid());
                    context.startActivity(intent);
                }
            });

            /** 게시물을 길게 눌렀을 때 이벤트, 편집과 삭제를 가능하게 함**/
            layoutBg.setOnLongClickListener(v -> { //Long Click 시 이벤트
                if (post.getUid().equals(uid)) { //본인 게시물인지 확인함
                    final CharSequence[] select = {"편집", "삭제"}; //본인이 작성 -> 편집 가능

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(post.getTitle());
                    builder.setItems(select, (dialog, pos) -> {
                        if (select[pos].equals("편집")) {
                            Intent intent = new Intent(context, PostAddActivity.class);
                            intent.putExtra("edit", post.getRegisteredDate().toString()); //도큐맨트의 아이디값을 넘겨주어 해당 액티비티에서 불러오게 함.
                            intent.putExtra("page", page);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        if (select[pos].equals("삭제")) {
                            AlertDialog.Builder delete = new AlertDialog.Builder(context);
                            delete.setTitle("삭제");
                            delete.setMessage("정말 삭제하시겠습니까?");
                            delete.setPositiveButton("예", (dialog1, which) -> {
                                firestore.collection("notice" + page)
                                        .document(post.getRegisteredDate().toString()).delete();
                                posts.remove(getAdapterPosition());
                                notifyDataSetChanged();
                                /** cold adapter와 마찬가지로 db상에서는 삭제되었지만 어댑터에는 반영이 되지 않음
                                 * 따라서 어댑터에서 항목을 삭제하고 notify해주어 뷰의 개수를 변경시켜줌. **/
                            });
                            delete.setNegativeButton("아니오", (dialog12, which) -> {
                            });
                            delete.show();
                        }
                    });
                    builder.show();
                }
                return true;
            });

            /* 타이틀의 폰트 색상, 폰트를 변경하는 부분입니다. */
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

            Typeface typeface;
            switch (post.getTitleFont()) {
                case "sans":
                    textTitle.setTypeface(null, Typeface.SANS_SERIF.getStyle());
                    textTitle.setTypeface(null, Typeface.BOLD);
                    break;
                case "serif":
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/batang.ttc");
                    textTitle.setTypeface(typeface);
                    break;
                case "casual":
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/nanumpen.ttf");
                    textTitle.setTypeface(typeface);
                    break;
            }
        }
    }
}